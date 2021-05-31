package com.gk.company.param.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/8 14:55
 */
@Accessors(chain =true )
@Data
public class LoginResult implements Serializable {
    private static final long serialVersionUID = -4035754716493701436L;

    String code;
    String msg;
    String token;
    Object result;

}
