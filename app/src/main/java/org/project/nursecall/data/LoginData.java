package org.project.nursecall.data;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("status")
    private Boolean status;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }
}
