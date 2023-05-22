package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @author cornelius
 * @date 2023/5/11 18:05
 * @description
 */
public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
