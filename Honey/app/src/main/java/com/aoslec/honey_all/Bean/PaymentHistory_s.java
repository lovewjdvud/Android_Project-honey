package com.aoslec.honey_all.Bean;

public class PaymentHistory_s {
    String buyNum;
    String buyDeliveryPrice;
    String buyDay;
    String buyCencelDay;
    String iName;
    String iCapacity;
    String iUnit;
    String count;

    public PaymentHistory_s(String buyNum, String buyDeliveryPrice, String buyDay, String buyCencelDay, String iName, String iCapacity, String iUnit, String count) {
        this.buyNum = buyNum;
        this.buyDeliveryPrice = buyDeliveryPrice;
        this.buyDay = buyDay;
        this.buyCencelDay = buyCencelDay;
        this.iName = iName;
        this.iCapacity = iCapacity;
        this.iUnit = iUnit;
        this.count = count;
    }

    public String getBuyCencelDay() {
        return buyCencelDay;
    }

    public void setBuyCencelDay(String buyCencelDay) {
        this.buyCencelDay = buyCencelDay;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getBuyDeliveryPrice() {
        return buyDeliveryPrice;
    }

    public void setBuyDeliveryPrice(String buyDeliveryPrice) {
        this.buyDeliveryPrice = buyDeliveryPrice;
    }

    public String getBuyDay() {
        return buyDay;
    }

    public void setBuyDay(String buyDay) {
        this.buyDay = buyDay;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
