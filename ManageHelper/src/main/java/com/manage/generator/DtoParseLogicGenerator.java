package com.manage.generator;

import java.util.List;

public class DtoParseLogicGenerator {

	public StringBuilder generateLogic(List<FieldDto> fields, boolean insideInnerClass) {
		return generateParseLogic(fields, insideInnerClass);
	}

	private StringBuilder generateParseLogic(List<FieldDto> fields, boolean insideInnerClass) {
		StringBuilder result = new StringBuilder();
		String indentation = getIndentation(insideInnerClass);

		result.append(indentation).append("public void parse(String input) {" + System.lineSeparator());
		result.append(indentation).append("\tint index = 0;" + System.lineSeparator());

		for (FieldDto fieldDto : fields) {
			result.append(parseField(fieldDto, indentation));
		}
		result.append(indentation).append("}").append(System.lineSeparator());

		return result;
	}

	private String parseField(FieldDto field, String indentation) {
		String substr = "input.substring(index, index + " + field.getElementLength() + ").trim()";
		String nextIdx = "input += " + field.getElementLength() + ";" + System.lineSeparator();
		String setData = indentation + "\t" + field.getFieldName() + " = ";
		StringBuilder builder = new StringBuilder();
		boolean isList = false;
		switch (field.getFieldType()) {
		case "int":
			builder.append(setData).append("Integer.parseInt(" + substr + ")");
			break;
		case "Integer":
			builder.append(setData).append("Integer.valueOf(" + substr + ")");
			break;
		case "Long":
			builder.append(setData).append("Long.valueOf(" + substr + ")");
			break;
		case "BigDecimal":
			builder.append(setData).append("new BigDecimal(" + substr + ")");
			break;
		case "String":
			builder.append(setData).append(substr + ".trim()");
			break;
		default:
			if (field.getFieldType().startsWith("List")) {
				isList = true;
				builder.append(setData).append("new ArrayList<>();").append(System.lineSeparator());
				builder.append(indentation + "\t").append("for (int i = 0; i < ").append(field.getListSize())
						.append("; i++) {").append(System.lineSeparator());
				builder.append(parseListField(field, indentation + "\t"));
			} else {
				builder.append(setData).append("new ").append(field.getFieldType()).append("();")
						.append(System.lineSeparator());
				builder.append(indentation + "\t").append(field.getFieldName()).append(".parse(").append(substr)
						.append(")");
			}
			break;
		}
		if (!isList) {
			builder.append(";").append(System.lineSeparator());
			builder.append(indentation + "\t").append(nextIdx);
		}
		return builder.toString();
	}

	private String parseListField(FieldDto field, String indentation) {
		String substr = "input.substring(index, index + " + field.getElementLength() + ").trim()";
		String nextIdx = "input += " + field.getElementLength() + ";" + System.lineSeparator();
		String setData = indentation + "\t" + field.getFieldType().split("<")[1].split(">")[0] + " work = ";
		StringBuilder builder = new StringBuilder();
		switch (field.getFieldType().split("<")[1].split(">")[0]) {
		case "int":
			builder.append(setData).append("Integer.parseInt(" + substr + ")");
			break;
		case "Integer":
			builder.append(setData).append("Integer.valueOf(" + substr + ")");
			break;
		case "Long":
			builder.append(setData).append("Long.valueOf(" + substr + ")");
			break;
		case "BigDecimal":
			builder.append(setData).append("new BigDecimal(" + substr + ")");
			break;
		case "String":
			builder.append(setData).append(substr + ".trim()");
			break;
		default:
			builder.append(setData).append("new ").append(field.getFieldType().split("<")[1].split(">")[0])
					.append("();").append(System.lineSeparator());
			builder.append(indentation + "\t").append("work.parse(").append(substr).append(")");
			break;
		}
		builder.append(";").append(System.lineSeparator());
		builder.append(indentation + "\t").append(field.getFieldName()).append(".add(work);")
				.append(System.lineSeparator());
		builder.append(indentation + "\t").append(nextIdx);
		builder.append(indentation).append("}").append(System.lineSeparator());
		return builder.toString();
	}

	private String getIndentation(boolean insideInnerClass) {
		return insideInnerClass ? "\t\t" : "\t";
	}

}