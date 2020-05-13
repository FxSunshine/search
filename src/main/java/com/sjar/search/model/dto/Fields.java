package com.sjar.search.model.dto;

public class Fields {
    private boolean doc_type;

    private boolean cause_name;

    private boolean court_name;

    private boolean province_name;

    private boolean trial_round;


    public boolean isDoc_type() {
        return doc_type;
    }

    public void setDoc_type(boolean doc_type) {
        this.doc_type = doc_type;
    }

    public boolean isCause_name() {
        return cause_name;
    }

    public void setCause_name(boolean cause_name) {
        this.cause_name = cause_name;
    }

    public boolean isCourt_name() {
        return court_name;
    }

    public void setCourt_name(boolean court_name) {
        this.court_name = court_name;
    }

    public boolean isProvince_name() {
        return province_name;
    }

    public void setProvince_name(boolean province_name) {
        this.province_name = province_name;
    }

    public boolean isTrial_round() {
        return trial_round;
    }

    public void setTrial_round(boolean trial_round) {
        this.trial_round = trial_round;
    }
}
