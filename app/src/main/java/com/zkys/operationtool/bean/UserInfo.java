package com.zkys.operationtool.bean;

import java.util.List;

/**
 * Created by DGDL-08 on ${DATA}
 */
public class UserInfo {

    /**
     * id : 3
     * tag : app
     * name : 测试账号
     * username : test
     * avatarUrl :
     * token : eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOjMsInN1YiI6InRlc3QiLCJhdWQiOiLmtYvor5XotKblj7ciLCJ0YWciOiJhcHAiLCJ1cmciOlsib3BlcmF0ZSIsImNhcHRhaW4iXSwiZXhwIjoxNTQ4MjA1ODkxfQ.t6PBaKvZhk4EtzmcN4Y9kzMt0XmzoXheM6JLAY8_NIDLeycyCCmi1mWIPCc_n15WovSVlG4crpsM5SVpi6IjtQ
     * roleInfo : ["operate","captain"]
     */

    private int id;
    private String tag;
    private String name;
    private String username;
    private String avatarUrl;
    private String token;
    private List<String> roleInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(List<String> roleInfo) {
        this.roleInfo = roleInfo;
    }
}
