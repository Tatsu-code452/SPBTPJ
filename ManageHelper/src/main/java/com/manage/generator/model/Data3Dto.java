package com.manage.generator.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import java.util.ArrayList;

@Data
public class Data3Dto {
	private int data1;
	private Integer data2;
	private Long data3;
	private BigDecimal data4;
	private String data5;
	private List<String> data6;

	public void parse(String input) {
		int index = 0;
		data1 = Integer.parseInt(input.substring(index, index + 2).trim());
		input += 2;
		data2 = Integer.valueOf(input.substring(index, index + 4).trim());
		input += 4;
		data3 = Long.valueOf(input.substring(index, index + 6).trim());
		input += 6;
		data4 = new BigDecimal(input.substring(index, index + 3).trim());
		input += 3;
		data5 = input.substring(index, index + 5).trim().trim();
		input += 5;
		data6 = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			String work = input.substring(index, index + 3).trim().trim();
			data6.add(work);
			input += 3;
		}
	}
}
