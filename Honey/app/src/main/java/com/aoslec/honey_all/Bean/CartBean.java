package com.aoslec.honey_all.Bean;

public class CartBean {
    String cId;
    String iCode;
    String mCode;

    public CartBean(String cId, String iCode, String mCode) {
        this.cId = cId;
        this.iCode = iCode;
        this.mCode = mCode;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getiCode() {
        return iCode;
    }

    public void setiCode(String iCode) {
        this.iCode = iCode;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }
}
