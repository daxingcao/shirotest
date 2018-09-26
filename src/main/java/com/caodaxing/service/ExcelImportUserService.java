package com.caodaxing.service;

import java.io.InputStream;

import com.caodaxing.entity.vo.ExcelDTO;
import com.caodaxing.entity.vo.UserInfoVO;

public interface ExcelImportUserService {
	
	public ExcelDTO<UserInfoVO> importUserInfo(InputStream is);

}
