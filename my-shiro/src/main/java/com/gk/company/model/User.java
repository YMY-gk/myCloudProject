package com.gk.company.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/9 0:04
 */
@Accessors(chain =true )
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -4035754716493701436L;
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type;
    private Date createTime;
    private Date updateTime;
    private String salt;

}
