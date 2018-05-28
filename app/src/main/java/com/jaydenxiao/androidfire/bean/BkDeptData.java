package com.jaydenxiao.androidfire.bean;

import java.util.List;

/**
 * 报刊部门信息（虚拟信箱对应部门）
 * Created by yyh on 2017-06-03.
 * [
 {
 "offset": 0,
 "limit": 15,
 "exportType": null,
 "exportParamJson": null,
 "deptId": 1,
 "deptName": "收发办",
 "deptCode": "123456",
 "deptBox": 10,
 "deptToplimit": 1000,
 "deptTelephone": "1234567890",
 "remark": "这是一个测试部门",
 "sort": 1,
 "status": 0,
 "statusDate": null
 }
 ]
 */

public class BkDeptData {

    private int deptId;//部门id
    private String deptName;//部门名称
    private String deptCode;//部门code
    private int deptBox;//部门信箱号
    private int status;
    private List<Deptbkinfo> bkInfoList;//已发报刊集合

    public List<Deptbkinfo> getBkInfoList() {
        return bkInfoList;
    }

    public void setBkInfoList(List<Deptbkinfo> bkInfoList) {
        this.bkInfoList = bkInfoList;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public int getDeptBox() {
        return deptBox;
    }

    public void setDeptBox(int deptBox) {
        this.deptBox = deptBox;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public class Deptbkinfo {
        private String bkName;

        public String getBkName() {
            return bkName;
        }

        public void setBkName(String bkName) {
            this.bkName = bkName;
        }
    }
}
