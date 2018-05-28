package com.jaydenxiao.androidfire.bean;

import java.util.List;

/**
 * 报刊搜索结果数据
 * Created by yyh on 2017-06-03.
 *
 {
 "total": 1,
 "rows": [
 {
 "offset": 0,
 "limit": 15,
 "exportType": null,
 "exportParamJson": null,
 "bkOrderList": null,
 "orderedNum": null,
 "bkId": 10,
 "bkName": "中国教育报",
 "type": "教育、教学类",
 "issue": "日报",
 "category": 1,
 "mode": null,
 "language": null,
 "subscriptionMode": null,
 "barcode": null,
 "domesticPostCode": "1-10",
 "domesticSerialNumber": null,
 "internationalStandardSerialNumber": null,
 "foreignIssuingCode": null,
 "unitPrice": 24,
 "annualPrice": 288,
 "organizer": null,
 "postcode": null,
 "telephone": null,
 "editor": null,
 "createDate": null,
 "site": null,
 "classificationCode": null,
 "printUnit": null,
 "section": null,
 "characteristic": null,
 "province": null,
 "year": 2017,
 "isHost": 1,
 "publicationDate": null,
 "pressBureau": "北京市发报刊局",
 "level": "中央",
 "briefIntroduction": "以教育新闻为主的全国性日报。",
 "postSign": "邮发",
 "note": "周1-4出12版，周5出8版，周67、元旦清明五一端午十一出4版。22/4出24版，10/9出16版。4-5、11-12/3、3-6、10-13/7；28-31/8出8版。16-26/1；6-24/2；17-31/7；1-25/8出4版",
 "isPopular": 1,
 "issueLimit": "公开",
 "status": 0,
 "statusDate": null
 }
 ]
 }
 */

public class BkSearchData {
    private String total;//统计数量
    private List<BkRow> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<BkRow> getRows() {
        return rows;
    }

    public void setRows(List<BkRow> rows) {
        this.rows = rows;
    }

    public  class BkRow extends SelectedBean {
        private int bkId;
        private String bkName;//报刊名称
        private String type;//报刊类型
        private String issue;//日报，周报...
        private int category;//种类 1
        private String domesticPostCode;
        private int year;
        private int status;
        private int isPopular;
        private int isHost;

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

        public String getDomesticPostCode() {
            return domesticPostCode;
        }

        public void setDomesticPostCode(String domesticPostCode) {
            this.domesticPostCode = domesticPostCode;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsPopular() {
            return isPopular;
        }

        public void setIsPopular(int isPopular) {
            this.isPopular = isPopular;
        }

        public int getIsHost() {
            return isHost;
        }

        public void setIsHost(int isHost) {
            this.isHost = isHost;
        }
    }
}
