package com.jaydenxiao.androidfire.bean;

/**
 * 报刊详情实体类
 * Created by yyh on 2017-06-04.
 */
public class BkInfo {
    private int bkId;//主键
    private String bkName;//报刊名称
    private String type;//报刊分类
    private String issue;//刊期
    private int category;//报刊类型(1为报纸，2为杂志)
    private String barcode;//条形码
    private String domesticPostCode;//国内邮发代号
    private String domesticSerialNumber;//国内统一刊号
    private String internationalStandardSerialNumber;//国际标准刊号
    private String foreignIssuingCode;// 国外发行代号
    private int year;//年度
    private int isPopular;//是否畅销报(0为不是，1为是)
    private int status;//状态 0为正常，1为被删除

    public int getBkId() {
        return bkId;
    }

    public void setBkId(int bkId) {
        this.bkId = bkId;
    }

    public String getBkName() {
        return bkName;
    }

    public void setBkName(String bkName) {
        this.bkName = bkName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDomesticPostCode() {
        return domesticPostCode;
    }

    public void setDomesticPostCode(String domesticPostCode) {
        this.domesticPostCode = domesticPostCode;
    }

    public String getDomesticSerialNumber() {
        return domesticSerialNumber;
    }

    public void setDomesticSerialNumber(String domesticSerialNumber) {
        this.domesticSerialNumber = domesticSerialNumber;
    }

    public String getInternationalStandardSerialNumber() {
        return internationalStandardSerialNumber;
    }

    public void setInternationalStandardSerialNumber(String internationalStandardSerialNumber) {
        this.internationalStandardSerialNumber = internationalStandardSerialNumber;
    }

    public String getForeignIssuingCode() {
        return foreignIssuingCode;
    }

    public void setForeignIssuingCode(String foreignIssuingCode) {
        this.foreignIssuingCode = foreignIssuingCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(int isPopular) {
        this.isPopular = isPopular;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
