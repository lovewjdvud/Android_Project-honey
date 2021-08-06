package com.aoslec.honey_all.Bean;

public class BottomSheetBean {
    String name;
    String code;
    String mName;

    public BottomSheetBean() {
    }

    public BottomSheetBean(String name, String code, String mName) {
        this.name = name;
        this.code = code;
        this.mName = mName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
