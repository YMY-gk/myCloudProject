package com.gk.commen.entity.param.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/9 0:04
 */
@Accessors(chain =true )
@Data
public class RequestUser implements Serializable {
    private static final long serialVersionUID = -4035754716493701436L;
    private String nickname;
    private String password;
    private String salt;
}
