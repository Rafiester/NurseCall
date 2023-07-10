package org.project.nursecall.data;

import com.google.gson.annotations.SerializedName;

public class PasienData {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("time")
    private Integer time;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTime() {
        return time;
    }
}
