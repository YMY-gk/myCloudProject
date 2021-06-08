package com.gk.company.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业表
 * </p>
 *
 * @author guokui
 * @since 2021-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_company")
public class SysCompany extends Model<SysCompany> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 企业登录账号ID
     */
    @TableField("manager_id")
    private String managerId;

    /**
     * 当前版本
     */
    @TableField("version")
    private String version;

    /**
     * 续期时间
     */
    @TableField("renewal_date")
    private Date renewalDate;

    /**
     * 到期时间
     */
    @TableField("expiration_date")
    private Date expirationDate;

    /**
     * 公司地区
     */
    @TableField("company_area")
    private String companyArea;

    /**
     * 公司地址
     */
    @TableField("company_address")
    private String companyAddress;

    /**
     * 营业执照-图片
     */
    @TableField("business_license")
    private String businessLicense;

    /**
     * 法人代表
     */
    @TableField("legal_representative")
    private String legalRepresentative;

    /**
     * 公司电话
     */
    @TableField("company_phone")
    private String companyPhone;

    /**
     * 邮箱
     */
    @TableField("mailbox")
    private String mailbox;

    /**
     * 公司规模
     */
    @TableField("company_size")
    private Double companySize;

    /**
     * 所属行业
     */
    @TableField("industry")
    private String industry;

    /**
     * 审核状态
     */
    @TableField("audit_state")
    private Integer auditState;

    /**
     * 状态
     */
    @TableField("state")
    private Integer state;

    /**
     * 当前余额
     */
    @TableField("balance")
    private String balance;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
