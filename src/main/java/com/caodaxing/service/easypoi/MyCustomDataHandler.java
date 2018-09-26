package com.caodaxing.service.easypoi;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

@Component
public class MyCustomDataHandler extends ExcelDataHandlerDefaultImpl<Object> {

	private static final String FIELD_KEY = "fieldKey_";
	
	@Override
	public Object importHandler(Object obj, String name, Object value) {
		if(name.startsWith(FIELD_KEY)) {
			String indexStr = name.substring(FIELD_KEY.length());
			int index = Integer.parseInt(indexStr);
			for(Field field : obj.getClass().getDeclaredFields()) {
				Excel annotation = field.getAnnotation(Excel.class);
				if(annotation != null) {
					if(annotation.fixedIndex() > -1 || name.equals(annotation.name())) {
						if(index == annotation.fixedIndex()) {
							String numFormat = annotation.numFormat();
							if(!StringUtils.isEmpty(numFormat)) {
								DecimalFormat df = new DecimalFormat(numFormat);
								return df.format(new BigDecimal(value.toString()));
							}
						}
					}
				}
			}
		}
		return value;
	}
	
	public void setFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		String[] array = Arrays.stream(fields).filter(field -> {
			Excel annotation = field.getAnnotation(Excel.class);
			return annotation != null && !StringUtils.isEmpty(annotation.numFormat());
		}).map(field -> {
			Excel annotation = field.getAnnotation(Excel.class);
			int fixedIndex = annotation.fixedIndex();
			return fixedIndex > -1 ? FIELD_KEY + fixedIndex : annotation.name();
		}).toArray(String[]::new);
		setNeedHandlerFields(array);
	}

}
