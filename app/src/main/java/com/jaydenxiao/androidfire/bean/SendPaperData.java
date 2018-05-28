package com.jaydenxiao.androidfire.bean;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.common.commonutils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 2017-05-26.
 */

public class SendPaperData {
    private String PaperName;
    private String domesticPostCode;
    private String repalceString = "待替换";


    public String getPaperName() {
        return PaperName;
    }

    public void setPaperName(String paperName) {
        PaperName = paperName;
    }

    public String getDomesticPostCode() {
        return domesticPostCode;
    }

    public void setDomesticPostCode(String domesticPostCode) {
        this.domesticPostCode = domesticPostCode;
    }

    public List<SendPaperData> sendPaperData(Context context, int sendtype) {
        String s = "";
        if (sendtype == 1) {
            s = SPUtils.getSharedStringData(context, "sendPaperDatas");
        } else if (sendtype == 2) {
            s = SPUtils.getSharedStringData(context, "sendPaperDatas1");
        }
        if (!"".equals(s)) {
            List<SendPaperData> sendPaperDatas = JSON.parseArray(s, SendPaperData.class);
            return sendPaperDatas;
        } else {
            List<SendPaperData> sendPaperDatas = new ArrayList<>();
            for(int i=0;i<32;i++)
            {
                SendPaperData sendPaperData = new SendPaperData();
                sendPaperData.setPaperName(repalceString);
                sendPaperData.setDomesticPostCode("");
                sendPaperDatas.add(sendPaperData);
            }
            return sendPaperDatas;
        }

    }

}
