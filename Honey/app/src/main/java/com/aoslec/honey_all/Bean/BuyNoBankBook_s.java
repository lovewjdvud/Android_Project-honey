package com.aoslec.honey_all.Bean;

public class BuyNoBankBook_s {
    private String iName;
    private String iCapacity;
    private String iUnit;
    private String buyPostNum;
    private String buyAddress1;
    private String buyAddress2;
    private String buyRequests;
    private String buyDeliveryPrice;
    private String count;

    public BuyNoBankBook_s(String iName, String iCapacity, String iUnit, String buyPostNum, String buyAddress1, String buyAddress2, String buyRequests, String buyDeliveryPrice, String count) {
        this.iName = iName;
        this.iCapacity = iCapacity;
        this.iUnit = iUnit;
        this.buyPostNum = buyPostNum;
        this.buyAddress1 = buyAddress1;
        this.buyAddress2 = buyAddress2;
        this.buyRequests = buyRequests;
        this.buyDeliveryPrice = buyDeliveryPrice;
        this.count = count;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
