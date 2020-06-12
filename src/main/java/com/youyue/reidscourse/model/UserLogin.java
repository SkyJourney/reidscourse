package com.youyue.reidscourse.model;

import java.io.Serializable;

public class UserLogin implements Serializable {

    private Integer uId;
    private String uName;
    private String uPwd;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }
}
