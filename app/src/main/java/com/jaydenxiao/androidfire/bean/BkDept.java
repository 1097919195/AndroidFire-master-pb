package com.jaydenxiao.androidfire.bean;

import java.util.Date;

/**
 * 部门信息
 * Created by yyh on 2017-06-04.
 */
public class BkDept {
    private int deptId;
    private String deptName;
    private String deptCode;
    private int deptBox;
    private Float deptToplimit;
    private String deptTelephone;
    private String remark;
    private int sort;
    private int status;
    private Date statusDate;

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

    public Float getDeptToplimit() {
        return deptToplimit;
    }

    public void setDeptToplimit(Float deptToplimit) {
        this.deptToplimit = deptToplimit;
    }

    public String getDeptTelephone() {
        return deptTelephone;
    }

    public void setDeptTelephone(String deptTelephone) {
        this.deptTelephone = deptTelephone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
}
