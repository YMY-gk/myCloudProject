package com.gk.commen.entity;

import com.gk.commen.entity.codes.CommonCode;
import com.gk.commen.entity.codes.ResultCode;

/**
 * <p></p>
 *
 * @author bin.zhang
 * <p/>
 * Revision History:
 * 2021/05/10, 初始化版本
 * @version 1.0
 **/
public class ResultObjectUtil {

	/**
	 * 成功返回
	 * @return
	 */
	public static <R> ResultObject<R> OK(){
		return OK(null);
	}


	/**
	 * 成功返回(包含数据)
	 * @param data 返回的数据
	 * @return
	 */
	public static <R> ResultObject<R> OK(R data){
		return OK(data, null);
	}


	/**
	 * 成功返回(包含数据, 额外数据)
	 * @param data 返回的数据
	 * @param extra 额外数据
	 * @return
	 */
	public static <R> ResultObject<R> OK(R data, Object extra) {
		return result(CommonCode.SUCCESS, data, extra);
	}


	/**
	 * 包含错误码的返回
	 * @param resultCode 错误码
	 * @return
	 */
	public static <R> ResultObject<R> ERROR(ResultCode resultCode){
		return ERROR(resultCode, null, null);
	}


	/**
	 * 包含错误码的返回
	 * @param resultCode 错误码
	 * @param data 返回数据
	 * @return
	 */
	public static <R> ResultObject<R> ERROR(ResultCode resultCode, R data){
		return ERROR(resultCode, data, null);
	}


	/**
	 * 包含错误码数据和额外数据的返回
	 * @param resultCode
	 * @param data
	 * @param extra
	 * @param <R>
	 * @return
	 */
	public static <R> ResultObject<R> ERROR(ResultCode resultCode, R data, Object extra){
		return result(resultCode, data, extra);
	}


	/**
	 * 完整的构造方法
	 * @param resultCode
	 * @param data
	 * @param extra
	 * @param <R>
	 * @return
	 */
	private static <R> ResultObject<R> result(ResultCode resultCode, R data, Object extra){
		ResultObject<R> resultObject = new ResultObject<>(resultCode);
		resultObject.setData(data);
		resultObject.setExtra(extra);
		return resultObject;
	}


	private ResultObjectUtil(){}
}
