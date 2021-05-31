package com.gk.commen.entity.codes;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
/**
 * @author guokui
 * @class lintcode01
 * @date 2021/5/29 14:05
 */

public interface ResultCode {
    Integer getCode();

    String getMsg();


    default <R> ResultObject<R> covert(){
        return ResultObjectUtil.ERROR(this);
    }
    default <R> PageResultObject<R> covertP(){
        return PageObjectUtil.ERROR(this, new Page<R>());
    }
}
