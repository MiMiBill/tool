package com.zkys.operationtool.bean;

public class AuthorityBean {



//    "sortno" -> {Double@22531} 3.0
//            "list" -> {ArrayList@22533}  size = 0
//            "id" -> {Double@22535} 212.0
//            "updateTime" -> {Double@22537} 1.556015023E12
//            "fatherId" -> {Double@22539} 211.0
//            "createTime" -> {Double@22541} 1.556015023E12
//            "name" -> "平板密码"
//            "disabled" -> {Double@22545} 1.0
//            "type" -> "2"
//            "pathUrl" -> "*"
//            "code" -> "app:menu:yw:tools:padkey"


    private String name;
    private int type;
    private String code;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AuthorityBean{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", id=" + id +
                '}';
    }
}
