package com.aoslec.honey_all.Bean;

public class SearchListBean {

    String search_index;
    String search_text;
    String search_date;

    public SearchListBean(String search_index, String search_text, String search_date) {
        this.search_index = search_index;
        this.search_text = search_text;
        this.search_date = search_date;
    }

    public SearchListBean() {
    }

    public String getSearch_index() {
        return search_index;
    }

    public void setSearch_index(String search_index) {
        this.search_index = search_index;
    }

    public String getSearch_text() {
        return search_text;
    }

    public void setSearch_text(String search_text) {
        this.search_text = search_text;
    }

    public String getSearch_date() {
        return search_date;
    }

    public void setSearch_date(String search_date) {
        this.search_date = search_date;
    }
}
