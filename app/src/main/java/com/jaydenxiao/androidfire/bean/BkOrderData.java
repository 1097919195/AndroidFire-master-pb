package com.jaydenxiao.androidfire.bean;

/**
 * 更新订单返回的实体
 * Created by yyh on 2017-06-10.
 */

public class BkOrderData {

    private Boolean success;//false OR true
    private String message;//失败 OR 成功

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
