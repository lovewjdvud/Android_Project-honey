package com.aoslec.honey_all.Bean;

public class UserAddress_s {

    String cPostNum;
    String cAddress1;
    String cAddress2;

    public UserAddress_s(String cPostNum, String cAddress1, String cAddress2) {
        this.cPostNum = cPostNum;
        this.cAddress1 = cAddress1;
        this.cAddress2 = cAddress2;
    }

    public String getcPostNum() {
        return cPostNum;
    }

    public void setcPostNum(String cPostNum) {
        this.cPostNum = cPostNum;
    }

    public String getcAddress1() {
        return cAddress1;
    }

    public void setcAddress1(String cAddress1) {
        this.cAddress1 = cAddress1;
    }

    public String getcAddress2() {
        return cAddress2;
    }

    public void setcAddress2(String cAddress2) {
        this.cAddress2 = cAddress2;
    }
}
