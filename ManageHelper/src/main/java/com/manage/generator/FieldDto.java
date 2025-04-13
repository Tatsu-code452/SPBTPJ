package com.manage.generator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDto {
	private String fieldType;
	private String fieldName;
	private Integer elementLength;
	private Integer listSize;
}
