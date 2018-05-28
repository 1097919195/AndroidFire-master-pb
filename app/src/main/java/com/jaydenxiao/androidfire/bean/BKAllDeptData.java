package com.jaydenxiao.androidfire.bean;

import java.util.List;

/**
 * Created by asus-pc on 2017/8/22.
 */

public class BKAllDeptData {

    private List<BKDept> mlist;

    public List<BKDept> getMlist() {
        return mlist;
    }

    public void setMlist(List<BKDept> mlist) {
        this.mlist = mlist;
    }


    public class BKDept {

        private String deptName;
        private int  deptBox;

        public String getDeptName() {
        return deptName;
        }

        public void setDeptName(String deptName) {
        this.deptName = deptName;
        }

        public int getDeptBox() {
        return deptBox;
        }

        public void setDeptBox(int deptBox) {
        this.deptBox = deptBox;
        }
    }



}
