package com.aoslec.honey_all.Bean;

public class PaymentDetail_s {
    private String iName;
    private String iCapacity;
    private String iUnit;
    private String iPrice;
    private String mName;
    private String mImagePath;
    private String buyPostNum;
    private String buyAddress1;
    private String buyAddress2;
    private String buyRequests;
    private String buyDeliveryPrice;
    private int buyEA;
    private String buyDay;
    private String buyCencelDay;

    public PaymentDetail_s(String iName, String iCapacity, String iUnit, String iPrice, String mName, String mImagePath, String buyPostNum, String buyAddress1, String buyAddress2, String buyRequests, String buyDeliveryPrice, int buyEA, String buyDay, String buyCencelDay) {
        this.iName = iName;
        this.iCapacity = iCapacity;
        this.iUnit = iUnit;
        this.iPrice = iPrice;
        this.mName = mName;
        this.mImagePath = mImagePath;
        this.buyPostNum = buyPostNum;
        this.buyAddress1 = buyAddress1;
        this.buyAddress2 = buyAddress2;
        this.buyRequests = buyRequests;
        this.buyDeliveryPrice = buyDeliveryPrice;
        this.buyEA = buyEA;
        this.buyDay = buyDay;
        this.buyCencelDay = buyCencelDay;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getiCapacity() {
        return iCapacity;
    }

    public void setiCapacity(String iCapacity) {
        this.iCapacity = iCapacity;
    }

    public String getiUnit() {
        return iUnit;
    }

    public void setiUnit(String iUnit) {
        this.iUnit = iUnit;
    }

    public String getiPrice() {
        return iPrice;
    }

    public void setiPrice(String iPrice) {
        this.iPrice = iPrice;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public String getBuyPostNum() {
        return buyPostNum;
    }

    public void setBuyPostNum(String buyPostNum) {
        this.buyPostNum = buyPostNum;
    }

    public String getBuyAddress1() {
        return buyAddress1;
    }

    public void setBuyAddress1(String buyAddress1) {
        this.buyAddress1 = buyAddress1;
    }

    public String getBuyAddress2() {
        return buyAddress2;
    }

    public void setBuyAddress2(String buyAddress2) {
        this.buyAddress2 = buyAddress2;
    }

    public String getBuyRequests() {
        return buyRequests;
    }

    public void setBuyRequests(String buyRequests) {
        this.buyRequests = buyRequests;
    }

    public String getBuyDeliveryPrice() {
        return buyDeliveryPrice;
    }

    public void setBuyDeliveryPrice(String buyDeliveryPrice) {
        this.buyDeliveryPrice = buyDeliveryPrice;
    }

    public int getBuyEA() {
        return buyEA;
    }

    public void setBuyEA(int buyEA) {
        this.buyEA = buyEA;
    }

    public String getBuyDay() {
        return buyDay;
    }

    public void setBuyDay(String buyDay) {
        this.buyDay = buyDay;
    }

    public String getBuyCencelDay() {
        return buyCencelDay;
    }

    public void setBuyCencelDay(String buyCencelDay) {
        this.buyCencelDay = buyCencelDay;
    }
}
