package com.gk.commen.entity;

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
public class Extra implements Serializable {
	private static final long serialVersionUID = -2610329246170855752L;

	/**
	 * 当前页码
	 */
	private Long pageIndex;

	/**
	 * 一页条数
	 */
	private Long pageSize;

	/**
	 * 还有多少剩余的数据
	 */
	private Long more;

	/**
	 * 总条数
	 */
	private Long total;
}
