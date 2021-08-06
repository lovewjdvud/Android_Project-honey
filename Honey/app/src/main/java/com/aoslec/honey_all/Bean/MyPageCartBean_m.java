package com.aoslec.honey_all.Bean;

public class MyPageCartBean_m {
    private String client_cId, mName, mCode, tipContent, TipAddDay;

    public MyPageCartBean_m(String client_cId, String mCode, String mName, String tipContent, String TipAddDay) {//0626 tip day 추가
        this.client_cId = client_cId;
        this.mName = mName;
        this.mCode = mCode;
        this.tipContent = tipContent;
        this.TipAddDay = TipAddDay;//0626 tip day 추가
    }

    public String getClient_cId() {
        return client_cId;
    }

    public void setClient_cId(String client_cId) {
        this.client_cId = client_cId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }

    public String getTipAddDay() {
        return TipAddDay;
    }//0626 tip day 추가

    public void setTipAddDay(String tipAddDay) {
        TipAddDay = tipAddDay;
    }//0626 tip day 추가
}
