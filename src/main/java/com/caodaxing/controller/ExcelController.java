package com.caodaxing.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ClusterCommandExecutor.MulitNodeResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.caodaxing.entity.vo.ExcelDTO;
import com.caodaxing.entity.vo.UserInfoVO;
import com.caodaxing.service.ExcelImportUserService;
import com.caodaxing.utils.MessageUtil;
import com.caodaxing.utils.file.ExcelReadUtil;

@Controller
@RequestMapping("/excel")
public class ExcelController {
	
	private static final Logger log = LoggerFactory.getLogger(ExcelController.class);
	
	@Autowired
	private ExcelImportUserService excelImportUserService;
	
	@GetMapping("/download/excelmodel")
	public void downloadExcelModel(HttpServletRequest request,HttpServletResponse response) {
		InputStream is = request.getServletContext().getResourceAsStream("/excel/emp_model.xlsx");
		try (OutputStream os = response.getOutputStream()) {
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=emp_import_model.xlsx");
			byte[] byteArray = IOUtils.toByteArray(is);
			os.write(byteArray);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/upload/file")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request,MultipartFile myFile) {
		String contextPath = request.getServletContext().getRealPath("/excel");
		File file = new File(contextPath,myFile.getOriginalFilename());
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			myFile.transferTo(file);
			return MessageUtil.successMessage("上传成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return MessageUtil.errorMessage("上传失败!");
	}
	
	@PostMapping("/upload/userexcel")
	@ResponseBody
	public Map<String, Object> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		if(ExcelReadUtil.excel07FileType(fileName, file.getInputStream()) ||
				ExcelReadUtil.excel97FileType(fileName, file.getInputStream())) {
			InputStream is = file.getInputStream();
			ExcelDTO<UserInfoVO> excelDTO = excelImportUserService.importUserInfo(is);
			return MessageUtil.successMessage(excelDTO);
		}
		if(log.isErrorEnabled()) {
			log.error("上传文件类型错误...");
		}
		return MessageUtil.errorMessage("上传文件不是Excel类型!");
	}

}
