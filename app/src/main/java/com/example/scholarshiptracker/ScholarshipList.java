package com.example.scholarshiptracker;

public class ScholarshipList {
    private int sid;
    private String sname;
    private String sshortDesc;
    private String imageUrl;
    private String startDate;
    private String slongDesc;
    private String levelOfStudy;
    private String url;
    private boolean isExpanded;


    public ScholarshipList(int sid, String sname, String sshortDesc, String imageUrl, String startDate, String levelOfStudy, String slongDesc, String url) {
        this.sid = sid;
        this.sname = sname;
        this.sshortDesc = sshortDesc;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.slongDesc = slongDesc;
        this.levelOfStudy = levelOfStudy;
        this.url = url;
        isExpanded = false;

    }

    public String getBtnApply() {
        return url;
    }

    public void setBtnApply(String btnApply) {
        this.url = btnApply;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSshortDesc() {
        return sshortDesc;
    }

    public void setSshortDesc(String sshortDesc) {
        this.sshortDesc = sshortDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSlongDesc() {
        return slongDesc;
    }

    public void setSlongDesc(String slongDesc) {
        this.slongDesc = slongDesc;
    }

    public String getLevelOfStudy() {
        return levelOfStudy;
    }

    public void setLevelOfStudy(String levelOfStudy) {
        this.levelOfStudy = levelOfStudy;
    }

    @Override
    public String toString() {
        return "ScholarshipList{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", sshortDesc='" + sshortDesc + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", startDate='" + startDate + '\'' +
                ", slongDesc='" + slongDesc + '\'' +
                ", levelOfStudy='" + levelOfStudy + '\'' +
                ", btnApply=" + url +
                ", isExpanded=" + isExpanded +
                '}';
    }
}
