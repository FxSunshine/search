package com.sjar.search.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuxiangjian
 * @since 2020-05-13
 */
@TableName("tb_law")
public class Law extends Model<Law> {

    private static final long serialVersionUID = 1L;

    private String codeId;

    private String subjectCategory;

    private String location;

    private String issuingAgency;

    private String releaseDate;

    private String codeName;

    private String isStar;

    private String laws;

    private String implementDate;

    private String timeliness;

    private String lawType;

    private String issuingNumber;

    private String url;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getSubjectCategory() {
        return subjectCategory;
    }

    public void setSubjectCategory(String subjectCategory) {
        this.subjectCategory = subjectCategory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIssuingAgency() {
        return issuingAgency;
    }

    public void setIssuingAgency(String issuingAgency) {
        this.issuingAgency = issuingAgency;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getIsStar() {
        return isStar;
    }

    public void setIsStar(String isStar) {
        this.isStar = isStar;
    }

    public String getLaws() {
        return laws;
    }

    public void setLaws(String laws) {
        this.laws = laws;
    }

    public String getImplementDate() {
        return implementDate;
    }

    public void setImplementDate(String implementDate) {
        this.implementDate = implementDate;
    }

    public String getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(String timeliness) {
        this.timeliness = timeliness;
    }

    public String getLawType() {
        return lawType;
    }

    public void setLawType(String lawType) {
        this.lawType = lawType;
    }

    public String getIssuingNumber() {
        return issuingNumber;
    }

    public void setIssuingNumber(String issuingNumber) {
        this.issuingNumber = issuingNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected Serializable pkVal() {
        return this.codeId;
    }

    @Override
    public String toString() {
        return "Law{" +
        "codeId=" + codeId +
        ", subjectCategory=" + subjectCategory +
        ", location=" + location +
        ", issuingAgency=" + issuingAgency +
        ", releaseDate=" + releaseDate +
        ", codeName=" + codeName +
        ", isStar=" + isStar +
        ", laws=" + laws +
        ", implementDate=" + implementDate +
        ", timeliness=" + timeliness +
        ", lawType=" + lawType +
        ", issuingNumber=" + issuingNumber +
        "}";
    }
}
