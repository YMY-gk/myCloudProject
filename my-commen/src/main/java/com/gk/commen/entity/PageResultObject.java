package com.gk.commen.entity;

import com.gk.commen.entity.codes.ResultCode;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

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
public class PageResultObject<R> implements Serializable {

	private static final long serialVersionUID = 4871119395037177159L;


	public PageResultObject(ResultCode resultCode){
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
	private Collection<R> data;

	/**
	 * 分页额外数据
	 */
	private Extra extra;




}
