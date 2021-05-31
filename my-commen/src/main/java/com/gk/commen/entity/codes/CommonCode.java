package com.gk.commen.entity.codes;

import lombok.Getter;

/**
 * <p></p>
 *
 * @author bin.zhang
 * <p/>
 * Revision History:
 * 2021/05/10, 初始化版本
 * @version 1.0
 **/
@Getter
public enum CommonCode implements ResultCode {


	CREATE_TEMPLATE_FAILED(10003, "创建模板失败"),
	DEFAULT_ERROR(500, "服务端错误，请稍后重试"),
	EXPORT_EXCEL_FAILED(10005, "导出Excel表格失败"),
	EXPORT_OVERFLOW(10006, "导出Excel超出数量限制"),
	PARAM(202, "参数错误"),
	RECORD_NO_EXIST(203,"操作记录不存在"),
	PHONE_FORMAT_ERROR(10009, "手机号格式有误，发送短信失败"),
	READ_EXCEL_FAILED(10004, "读取Excel表格失败"),
	RIGHT_ERROR(10002, "权限不足"),
	SEND_MESSAGE_FAILED(10008, "发送通知失败"),
	SUCCESS(0, "成功"),
	TIME_GAP_OVERFLOW(10007, "时间跨度不能超过%s"),
	TOKEN_ERROR(10001, "Token错误"),
	RECORD_EXIST(300, "操作数据已存在"),
	PARAM_MISS_ERROR(400, "参数缺失或ContentType错误"),
	REQUEST_METHOD_ERROR(405, "Method Not Allowed"),

	;
	private final Integer code;
	private final String msg;

	CommonCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
