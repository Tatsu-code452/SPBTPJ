package com.manage.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassDto {
	private String packageName;
	private Set<String> imports;
	private List<String> annotation;
	private String className;
	private List<FieldDto> fields;
	private List<StringBuilder> logic;
	private Map<String, InnerClassDto> innerClass;

	public ClassDto() {
		imports = new HashSet<>();
		annotation = new ArrayList<>();
		fields = new ArrayList<>();
		logic = new ArrayList<>();
		innerClass = new LinkedHashMap<>();
	}
}
