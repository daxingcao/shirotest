package com.caodaxing.entity.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDTO<T> {
	
	private List<T> successList;
	
	private List<T> failedList;
	
	private boolean isFailed;

}
