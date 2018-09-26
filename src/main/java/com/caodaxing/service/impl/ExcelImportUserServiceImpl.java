package com.caodaxing.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caodaxing.entity.vo.ExcelDTO;
import com.caodaxing.entity.vo.UserInfoVO;
import com.caodaxing.service.ExcelImportUserService;
import com.caodaxing.service.easypoi.MyCustomDataHandler;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

@Service
public class ExcelImportUserServiceImpl implements ExcelImportUserService {

	@Autowired
	private MyCustomDataHandler myCustomDataHandler;
	
	@Override
	@Transactional(readOnly=true,rollbackFor=Exception.class)
	public ExcelDTO<UserInfoVO> importUserInfo(InputStream is) {
		if(is == null) {
			throw new NullPointerException("file stream is null!");
		}
		ExcelImportResult<UserInfoVO> excelParse = excelParse(is);
		if(excelParse == null) {
			throw new RuntimeException("parseing excel is error");
		}
		return new ExcelDTO<UserInfoVO>(excelParse.getList(), excelParse.getFailList(), excelParse.isVerfiyFail());
	}
	
	private ExcelImportResult<UserInfoVO> excelParse(InputStream is) {
		ExcelImportResult<UserInfoVO> importResult = null;
		ImportParams params = new ImportParams();
		//是否校验数据,默认为false
		params.setNeedVerfiy(Boolean.TRUE);
		//excel标题占有的行数,没有默认为0
		params.setTitleRows(1);
		//Excel表头占有的行数,默认为1
		params.setHeadRows(1);
		myCustomDataHandler.setFields(UserInfoVO.class);
		params.setDataHandler(myCustomDataHandler);
		try {
			importResult = ExcelImportUtil.importExcelMore(is, UserInfoVO.class, params);
			if(importResult.isVerfiyFail()) {
				File file = new File("D:\\logs\\failexcel\\aaa.xlsx");
				importResult.getFailWorkbook().write(new FileOutputStream(file));
			}
			return importResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
