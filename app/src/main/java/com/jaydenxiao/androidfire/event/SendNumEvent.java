package com.jaydenxiao.androidfire.event;

/**
 * Created by yyh on 2017-06-14.
 */

public class SendNumEvent {
    private int num;

    public SendNumEvent(int i) {
        this.num=i;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
