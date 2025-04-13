package com.manage.generator.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import java.util.ArrayList;

@Data
public class Data1Dto {
	private int data1;
	private Integer data2;
	private Long data3;
	private BigDecimal data4;
	private String data5;
	private List<String> data6;
	private List<Data1InnerDto> data7;
	private Data1InnerDto data8;

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
		data7 = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Data1InnerDto work = new Data1InnerDto();
			work.parse(input.substring(index, index + 32).trim());
			data7.add(work);
			input += 32;
		}
		data8 = new Data1InnerDto();
		data8.parse(input.substring(index, index + 32).trim());
		input += 32;
	}
	@Data
	public static class Data1InnerDto {
		private int data1;
		private Integer data2;
		private Long data3;
		private BigDecimal data4;
		private String data5;
		private List<String> data6;
		public void parse(String input) {
			int index = 0;
			data1 = Integer.parseInt(input.substring(index, index + 4).trim());
			input += 4;
			data2 = Integer.valueOf(input.substring(index, index + 3).trim());
			input += 3;
			data3 = Long.valueOf(input.substring(index, index + 5).trim());
			input += 5;
			data4 = new BigDecimal(input.substring(index, index + 1).trim());
			input += 1;
			data5 = input.substring(index, index + 16).trim().trim();
			input += 16;
			data6 = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				String work = input.substring(index, index + 1).trim().trim();
				data6.add(work);
				input += 1;
			}
		}
	}
}
