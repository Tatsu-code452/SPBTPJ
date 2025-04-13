package com.manage.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DtoGenerator {

	private static final String LIST_PREFIX = "List";
	private static final String BIG_DECIMAL = "BigDecimal";
	// 定義ファイルのディレクトリ
	private static final String inputDirectory = "C:/workspace/web/SPBTPJ/ManageHelper/src/main/java/com/manage/generator/def";
	// 出力ディレクトリ
	private static final String outputDirectory = "C:/workspace/web/SPBTPJ/ManageHelper/src/main/java/com/manage/generator/model";

	public static void main(String[] args) {
		try {
			// パッケージ名
			String packageName = "com.manage.generator.model";
			generateDtoClassesForAllFiles(packageName);
		} catch (IOException e) {
			System.err.println("Error generating DTO classes: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void generateDtoClassesForAllFiles(String packageName) throws IOException {
		File dir = new File(inputDirectory);
		if (!dir.exists() || !dir.isDirectory()) {
			throw new IllegalArgumentException(
					"Input directory does not exist or is not a directory: " + inputDirectory);
		}

		File[] defFiles = dir.listFiles((d, name) -> name.endsWith(".def"));
		if (defFiles == null || defFiles.length == 0) {
			System.out.println("No .def files found in directory: " + inputDirectory);
			return;
		}

		// Create a thread pool
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		// LambdaとStreamを利用した並列処理
		Stream.of(defFiles).forEach(defFile -> executor.submit(() -> {
			try {
				System.out.println("Processing file: " + defFile.getName());
				processDefFile(defFile.getAbsolutePath(), packageName);
			} catch (IOException e) {
				System.err.println("Error processing file " + defFile.getName() + ": " + e.getMessage());
				e.printStackTrace();
			}
		}));

		// Shut down the executor service
		executor.shutdown();
		try {
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				System.err.println("Executor did not terminate in the specified time.");
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

//	public static void generateDtoClassesForAllFiles(String packageName) throws IOException {
//		File dir = new File(inputDirectory);
//		if (!dir.exists() || !dir.isDirectory()) {
//			throw new IllegalArgumentException(
//					"Input directory does not exist or is not a directory: " + inputDirectory);
//		}
//
//		File[] defFiles = dir.listFiles((d, name) -> name.endsWith(".def"));
//		if (defFiles == null || defFiles.length == 0) {
//			System.out.println("No .def files found in directory: " + inputDirectory);
//			return;
//		}
//
//		for (File defFile : defFiles) {
//			System.out.println("Processing file: " + defFile.getName());
//			processDefFile(defFile.getAbsolutePath(), packageName);
//		}
//	}

	private static void processDefFile(String inputFileName, String packageName) throws IOException {
		ClassDto target = new ClassDto();
		String readClassName = "";
		boolean insideInnerClass = false;
		try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
			String line;

			while ((line = br.readLine()) != null) {
				line = line.trim();
				String[] parts = line.split(":");
				if (line.endsWith(":class")) {
					readClassName = getClassName(parts);
					startClass(target, packageName, parts);
				} else if (line.endsWith(":innerClass")) {
					readClassName = getClassName(parts);
					insideInnerClass = true;
					startInnerClass(target, parts);
				} else if (line.contains(":")) {
					if (insideInnerClass) {
						setInnerField(target, target.getInnerClass().get(readClassName), parts);
					} else {
						setField(target, parts);
					}
				}
			}

			writeClass(outputDirectory, target);
		}
	}

	private static String getClassName(String[] parts) {
		return parts[0].trim();
	}

	private static void startClass(ClassDto target, String packageName, String[] parts) {
		target.setPackageName(packageName);
		target.getImports().add("lombok.Data");
		target.getAnnotation().add("@Data");
		target.setClassName(parts[0].trim());
	}

	private static void startInnerClass(ClassDto parent, String[] parts) {
		InnerClassDto target = new InnerClassDto();
		target.getAnnotation().add("@Data");
		target.setClassName(parts[0].trim());
		parent.getInnerClass().put(target.getClassName(), target);
	}

	private static void setField(ClassDto target, String[] parts) {
		FieldDto field = new FieldDto();
		field.setFieldName(parts[0].trim());
		field.setFieldType(parts[1].trim());
		field.setElementLength(Integer.parseInt(parts[2].trim()));
		if (parts.length >= 4) {
			field.setListSize(Integer.parseInt(parts[3].trim()));
		}
		resolveFieldType(field.getFieldType(), target.getImports());
		target.getFields().add(field);
	}

	private static void setInnerField(ClassDto parent, InnerClassDto target, String[] parts) {
		FieldDto field = new FieldDto();
		field.setFieldName(parts[0].trim());
		field.setFieldType(parts[1].trim());
		field.setElementLength(Integer.parseInt(parts[2].trim()));
		if (parts.length >= 4) {
			field.setListSize(Integer.parseInt(parts[3].trim()));
		}
		resolveFieldType(field.getFieldType(), parent.getImports());
		target.getFields().add(field);
	}

	private static void resolveFieldType(String fieldType, Set<String> imports) {
		if (fieldType.startsWith(LIST_PREFIX)) {
			imports.add("java.util.List");
			imports.add("java.util.ArrayList");
		} else if (fieldType.equals(BIG_DECIMAL)) {
			imports.add("java.math.BigDecimal");
		}
	}

	private static void writeClass(String outputDirectory, ClassDto target) throws IOException {

		File dir = new File(outputDirectory);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try (PrintWriter pw = new PrintWriter(new File(dir, target.getClassName() + ".java"))) {
			// パッケージ
			pw.println("package " + target.getPackageName() + ";");
			pw.println();
			// インポート
			pw.println(target.getImports().stream().map(v -> "import " + v + ";" + System.lineSeparator())
					.collect(Collectors.joining()));
			// アノテーション
			pw.print(
					target.getAnnotation().stream().map(v -> v + System.lineSeparator()).collect(Collectors.joining()));
			// クラス
			pw.println("public class " + target.getClassName() + " {");
			// フィールド
			pw.print(target.getFields().stream()
					.map(v -> "\tprivate " + v.getFieldType() + " " + v.getFieldName() + ";" + System.lineSeparator())
					.collect(Collectors.joining()));
			pw.println();

			// ロジック
			DtoParseLogicGenerator generator = new DtoParseLogicGenerator();
			pw.print(generator.generateLogic(target.getFields(), false).toString());

			// インナークラス
			for (InnerClassDto inner : target.getInnerClass().values()) {
				// アノテーション
				pw.print(inner.getAnnotation().stream().map(v -> "\t" + v + System.lineSeparator())
						.collect(Collectors.joining()));
				// クラス
				pw.println("\tpublic static class " + inner.getClassName() + " {");
				// フィールド
				pw.print(inner.getFields().stream().map(
						v -> "\t\tprivate " + v.getFieldType() + " " + v.getFieldName() + ";" + System.lineSeparator())
						.collect(Collectors.joining()));
				// ロジック
				pw.print(generator.generateLogic(inner.getFields(), true).toString());

				// クラスクローズ
				pw.println("\t}");
			}
			// クラスクローズ
			pw.println("}");
		}
	}

}