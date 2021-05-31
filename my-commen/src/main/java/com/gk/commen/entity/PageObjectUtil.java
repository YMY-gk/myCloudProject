package com.gk.commen.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gk.commen.entity.codes.CommonCode;
import com.gk.commen.entity.codes.ResultCode;

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
public class PageObjectUtil {


	/**
	 * 正常返回数据
	 * @param page
	 * @param <R>
	 * @return
	 */
	public static <R> PageResultObject<R> OK(IPage<R> page){
		return pageResult(CommonCode.SUCCESS, page.getRecords(), buildExtra(page));
	}


	/**
	 * 错误返回
	 * @param <R>
	 * @return
	 */
	public static <R> PageResultObject<R> ERROR(ResultCode resultCode, IPage<R> page){
		return pageResult(resultCode, page.getRecords(), buildExtra(page));
	}


	/**
	 *
	 * @param resultCode
	 * @param data
	 * @param extra
	 * @param <R>
	 * @return
	 */
	private static <R> PageResultObject<R> pageResult(ResultCode resultCode, Collection<R> data, Extra extra){
		PageResultObject<R> pageResultObject = new PageResultObject<>(resultCode);
		pageResultObject.setData(data);
		pageResultObject.setExtra(extra);
		return pageResultObject;
	}


	/**
	 *
	 * @param page
	 * @return
	 */
	private static Extra buildExtra(IPage<?> page){
		long more = page.getTotal() - page.getSize() * page.getCurrent();
		Extra extra = new Extra();
		extra.setTotal(page.getTotal());
		extra.setPageIndex(page.getCurrent());
		extra.setPageSize(page.getSize());
		extra.setMore(more < 0 ? 0 : more);
		return extra;
	}


	private PageObjectUtil(){}

}
