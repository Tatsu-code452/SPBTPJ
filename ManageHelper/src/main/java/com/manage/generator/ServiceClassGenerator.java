package com.manage.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.reflections.Reflections;

public class ServiceClassGenerator {

	public static void main(String[] args) {
		Reflections reflections = new Reflections("com.manage.helper");
		Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(LogicClass.class);

		String outputDir = "src/main/java/com/manage/helperUtil/Service/";
		File dir = new File(outputDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		for (Class<?> clazz : annotatedClasses) {
			if (!Modifier.isAbstract(clazz.getModifiers())) {
				String serviceClassContent = generateServiceClassContent(clazz);
				String className = clazz.getSimpleName().replace("Logic", "Service");

				try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputDir + className + ".java"))) {
					writer.write(serviceClassContent);
					System.out.println("Service class " + className + " generated successfully.");
				} catch (IOException e) {
					System.err.println("Error writing file for class: " + className);
					e.printStackTrace();
				}
			}
		}
	}

	private static String generateServiceClassContent(Class<?> logicClass) {
		StringBuilder serviceClassContent = new StringBuilder();
		String className = logicClass.getSimpleName().replace("Logic", "Service");
		String bdtoName = logicClass.getSimpleName().replace("Logic", "BDto");
		String logicClassName = logicClass.getName();
		String basePackage = logicClass.getPackage().getName().replace(".BusinessLogic.Logic", "");

		// パッケージ
		serviceClassContent.append("package ").append(basePackage).append(".BusinessLogic.Service;\n\\n");

		// インポート
		serviceClassContent.append("import org.springframework.stereotype.Service;\n");
		serviceClassContent.append("import lombok.RequiredArgsConstructor;\n");
		serviceClassContent.append("import ").append(logicClassName).append(";\n");
		serviceClassContent.append("import ").append(basePackage).append(".Model.").append(bdtoName).append(";\n");
		serviceClassContent.append("\n");

		// アノテーション
		serviceClassContent.append("@Service\n");
		serviceClassContent.append("@RequiredArgsConstructor\n");

		// クラス宣言開始
		serviceClassContent.append("public class ").append(className).append(" {\n\n");

		// フィールド宣言
		serviceClassContent.append("\tprivate final ").append(logicClass.getSimpleName()).append(" logic;\n");
		serviceClassContent.append("\n");

		// メソッド宣言
		serviceClassContent.append("\tpublic boolean execute(").append(bdtoName).append(" dto) {\n");
		serviceClassContent.append("\t\treturn logic.execute(dto);");
		serviceClassContent.append("\t}\n");
		serviceClassContent.append("\n");

		// クラス宣言終了
		serviceClassContent.append("}");

		return serviceClassContent.toString();
	}
}