package com.example.weargetmotion3;

public class ResultDataModel {
    String timestamp, action, F_avg, delta_theta;

    public ResultDataModel(String timestamp, String action, String F_avg, String delta_theta) {
        this.timestamp = timestamp;
        this.action = action;
        this.F_avg = F_avg;
        this.delta_theta = delta_theta;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getF_avg() {
        return F_avg;
    }

    public void setF_avg(String f_avg) {
        F_avg = f_avg;
    }

    public String getDelta_theta() {
        return delta_theta;
    }

    public void setDelta_theta(String delta_theta) {
        this.delta_theta = delta_theta;
    }
}
