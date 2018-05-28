package com.jaydenxiao.androidfire.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/8/10.
 */

public class BKBroadDisplayData {
    /**
     * broadcastDisplay : ok
     */

    @SerializedName("broadcastDisplay")
    private String broadcastDisplay;

    public String getBroadcastDisplay() {
        return broadcastDisplay;
    }

    public void setBroadcastDisplay(String broadcastDisplay) {
        this.broadcastDisplay = broadcastDisplay;
    }

}
