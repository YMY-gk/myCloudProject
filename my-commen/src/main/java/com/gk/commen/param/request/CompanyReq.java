package com.gk.commen.param.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 企业表
 * </p>
 *
 * @author guokui
 * @since 2021-06-09
 */
@Data
@Accessors(chain = true)
public class CompanyReq  {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    /**
     * 描述
     */
    private String remark;

    /**
     * 企业登录账号ID
     */
    private String managerId;

    /**
     * 当前版本
     */
    private String version;

    /**
     * 续期时间
     */
    private Date renewalDate;

    /**
     * 到期时间
     */
    private Date expirationDate;

    /**
     * 公司地区
     */
    private String companyArea;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 营业执照-图片
     */
    private String businessLicense;

    /**
     * 法人代表
     */
    private String legalRepresentative;

    /**
     * 公司电话
     */
    private String companyPhone;

    /**
     * 邮箱
     */
    private String mailbox;

    /**
     * 公司规模
     */
    private Double companySize;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 审核状态
     */
    private Integer auditState;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 当前余额
     */
    private String balance;

    /**
     * 创建时间
     */
    private Date createTime;

}
