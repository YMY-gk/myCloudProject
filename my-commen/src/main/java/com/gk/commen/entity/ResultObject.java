package com.gk.commen.entity;

import com.gk.commen.entity.codes.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author bin.zhang
 * <p/>
 * Revision History:
 * 2021/05/10, 初始化版本
 * @version 1.0
 **/
@Data
public class ResultObject<R> implements Serializable {

	private static final long serialVersionUID = 5096803066603657101L;


	public ResultObject(ResultCode resultCode){
		this.code = resultCode.getCode();
		this.msg = resultCode.getMsg();
	}

	/**
	 * 错误码
	 */
	private Integer code;

	/**
	 * 提示信息
	 */
	private String msg;

	/**
	 * 返回的数据
	 */
	private R data;

	/**
	 * 扩展数据
	 */
	private Object extra;

}
