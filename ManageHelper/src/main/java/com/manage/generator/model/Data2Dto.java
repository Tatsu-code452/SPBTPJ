package com.manage.generator.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import java.util.ArrayList;

@Data
public class Data2Dto {
	private int data1;
	private Integer data2;
	private Long data3;
	private BigDecimal data4;
	private String data5;
	private List<String> data6;
	private List<Data2InnerDto> data7;

	public void parse(String input) {
		int index = 0;
		data1 = Integer.parseInt(input.substring(index, index + 5).trim());
		input += 5;
		data2 = Integer.valueOf(input.substring(index, index + 2).trim());
		input += 2;
		data3 = Long.valueOf(input.substring(index, index + 4).trim());
		input += 4;
		data4 = new BigDecimal(input.substring(index, index + 3).trim());
		input += 3;
		data5 = input.substring(index, index + 67).trim().trim();
		input += 67;
		data6 = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			String work = input.substring(index, index + 2).trim().trim();
			data6.add(work);
			input += 2;
		}
		data7 = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Data2InnerDto work = new Data2InnerDto();
			work.parse(input.substring(index, index + 134).trim());
			data7.add(work);
			input += 134;
		}
	}
	@Data
	public static class Data2InnerDto {
		private int data1;
		private Integer data2;
		private Long data3;
		private BigDecimal data4;
		private String data5;
		private List<String> data6;
		public void parse(String input) {
			int index = 0;
			data1 = Integer.parseInt(input.substring(index, index + 5).trim());
			input += 5;
			data2 = Integer.valueOf(input.substring(index, index + 3).trim());
			input += 3;
			data3 = Long.valueOf(input.substring(index, index + 2).trim());
			input += 2;
			data4 = new BigDecimal(input.substring(index, index + 7).trim());
			input += 7;
			data5 = input.substring(index, index + 2).trim().trim();
			input += 2;
			data6 = new ArrayList<>();
			for (int i = 0; i < 23; i++) {
				String work = input.substring(index, index + 5).trim().trim();
				data6.add(work);
				input += 5;
			}
		}
	}
}
