package org.project.nursecall.data;

import com.google.gson.annotations.SerializedName;

public class PasienData {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("time")
    private Integer time;

    @SerializedName("msg_status")
    private Integer msg_status;

    @SerializedName("status")
    private Boolean status;

    public PasienData(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getMsg_status() {return msg_status;}

    public Boolean getStatus() {return status;}
}
