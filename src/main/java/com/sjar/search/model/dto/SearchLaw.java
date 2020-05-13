package com.sjar.search.model.dto;

public class SearchLaw {

    /**
     * {
     * "page": 1,
     * "total": 0,
     * "page_number": 1,
     * "page_size": 10,
     * "size": 10,
     * "conditions": {"must_extend_words": "知识产权"},
     * "search_type": "all",
     * "sort_type": 1
     * }
     */

    private int page;

    private int total;

    private int page_number;

    private int page_size;

    private int size;

    private String search_type;

    private int sort_type;

    private Conditions conditions;

    private Fields fields;

    private String words;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }

    public int getSort_type() {
        return sort_type;
    }

    public void setSort_type(int sort_type) {
        this.sort_type = sort_type;
    }

    public Conditions getConditions() {
        return conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
