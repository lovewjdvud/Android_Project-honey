package com.aoslec.honey_all.Bean;

public class TipBean_y {
    String tipCode;
    String cId;
    String cName;
    String tipContent;

    public TipBean_y(String tipCode, String cId, String cName, String tipContent) {
        this.tipCode = tipCode;
        this.cId = cId;
        this.cName = cName;
        this.tipContent = tipContent;
    }

    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getTipCode() {
        return tipCode;
    }

    public void setTipCode(String tipCode) {
        this.tipCode = tipCode;
    }
}
