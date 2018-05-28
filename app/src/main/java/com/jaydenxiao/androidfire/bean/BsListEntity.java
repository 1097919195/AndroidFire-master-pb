package com.jaydenxiao.androidfire.bean;

import java.util.List;

/**
 * Created by yyh on 2017-05-22.
 */

public class BsListEntity {

    private String Message;
    private List<BsListData> SList;



    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public List<BsListData> getSList() {
        return SList;
    }

    public void setSList(List<BsListData> SList) {
        this.SList = SList;
    }
}
