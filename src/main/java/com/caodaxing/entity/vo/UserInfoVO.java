package com.caodaxing.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {

	private static final long serialVersionUID = -4420597082496960755L;

	@NotNull
	@Excel(name="姓名",fixedIndex=0)
	private String userName;
	
	@Excel(name="年龄",fixedIndex=1)
	@DecimalMin("0")
	@DecimalMax("150")
	private Integer userAge;
	
	@NotNull
	@DecimalMax("1")
	@DecimalMin("0")
	@Excel(name="性别",fixedIndex=2,replace= {"男_1","女_2"})
	private Integer userGander;
	
	@Excel(name="居住地址",fixedIndex=3)
	private String userAddress;
	
	@Excel(name="手机号码",fixedIndex=4)
	@NotNull
	private String userMobile;
	
	@Excel(name="微信号码",fixedIndex=5)
	@NotNull
	private String userWechat;
	
	@Excel(name="QQ号码",fixedIndex=6)
	private String userQq;
	
	@Excel(name="工资",fixedIndex=7,numFormat="#.######")
	private BigDecimal salary;

}
