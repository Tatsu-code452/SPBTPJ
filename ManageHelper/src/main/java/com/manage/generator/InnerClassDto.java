package com.manage.generator;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InnerClassDto {
	private List<String> annotation;
	private String className;
	private List<FieldDto> fields;
	private List<StringBuilder> logic;

	public InnerClassDto() {
		annotation = new ArrayList<>();
		fields = new ArrayList<>();
		logic = new ArrayList<>();
	}
}
