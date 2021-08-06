package com.aoslec.honey_all.Bean;

public class FoodListBean {

    String mCode;
    String mCategory;
    String mName;
    String mImagePath;
    String mAddDay;
    String mImage2;

    public FoodListBean(String mCode, String mCategory, String mName, String mImagePath, String mAddDay, String mImage2) {
        this.mCode = mCode;
        this.mCategory = mCategory;
        this.mName = mName;
        this.mImagePath = mImagePath;
        this.mAddDay = mAddDay;
        this.mImage2 = mImage2;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
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

    public String getmAddDay() {
        return mAddDay;
    }

    public void setmAddDay(String mAddDay) {
        this.mAddDay = mAddDay;
    }

    public String getmImage2() {
        return mImage2;
    }

    public void setmImage2(String mImage2) {
        this.mImage2 = mImage2;
    }
}
