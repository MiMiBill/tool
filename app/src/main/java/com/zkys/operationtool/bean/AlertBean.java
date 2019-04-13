package com.zkys.operationtool.bean;

public class AlertBean {

    /**
     * createTime : 1.55479764E12
     * randomNumber : 1553501220718
     * pageSize : 20.0
     * id : 991.0
     * title : 设备离线警报
     * pageNum : 1.0
     * content : 东莞大朗医院-内科-联通公司测试机号床的设备已离线,请注意查看!
     */

    private double createTime;
    private String randomNumber;
    private double pageSize;
    private double id;
    private String title;
    private double pageNum;
    private String content;

    public double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(double createTime) {
        this.createTime = createTime;
    }

    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public double getPageSize() {
        return pageSize;
    }

    public void setPageSize(double pageSize) {
        this.pageSize = pageSize;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPageNum() {
        return pageNum;
    }

    public void setPageNum(double pageNum) {
        this.pageNum = pageNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
