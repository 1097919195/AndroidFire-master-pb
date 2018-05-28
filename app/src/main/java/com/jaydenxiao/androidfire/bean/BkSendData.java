package com.jaydenxiao.androidfire.bean;

import java.util.List;

/**
 * 报刊硬件发送参数（虚拟信箱号，数量）的列表
 * Created by yyh on 2017-06-03.
 * [
 {
 "bkDomesticPostCode": "2-31",
 "bkName": "中小学外语教学（中学篇）",
 "bkId": 301,
 "order": [
 {
 "deptName": "人事司",
 "deptId": 43,
 "orderNum": 2,
 "deptBox": null
 },
 {
 "deptName": "人事司",
 "deptId": 43,
 "orderNum": 1,
 "deptBox": null
 }
 ]
 }
 ]
 */

public class BkSendData {
    private String bkDomesticPostCode;
    private String bkName;
    private int bkId;
    private List<Order> order;

    public String getBkDomesticPostCode() {
        return bkDomesticPostCode;
    }

    public void setBkDomesticPostCode(String bkDomesticPostCode) {
        this.bkDomesticPostCode = bkDomesticPostCode;
    }

    public String getBkName() {
        return bkName;
    }

    public void setBkName(String bkName) {
        this.bkName = bkName;
    }

    public int getBkId() {
        return bkId;
    }

    public void setBkId(int bkId) {
        this.bkId = bkId;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public class Order {
        private String deptName;
        private int deptId;
        private int orderNum;
        private int deptBox;
        private int orderId;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public int getDeptBox() {
            return deptBox;
        }

        public void setDeptBox(int deptBox) {
            this.deptBox = deptBox;
        }
    }
}
