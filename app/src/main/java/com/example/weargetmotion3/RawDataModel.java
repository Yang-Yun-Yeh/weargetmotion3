package com.example.weargetmotion3;

import java.sql.Timestamp;

public class RawDataModel {
    float linear_acceleration_x, linear_acceleration_y, linear_acceleration_z, acceleration ,ang_vel_x, ang_vel_y, ang_vel_z, omega;
    String timestamp;

    public RawDataModel(float linear_acceleration_x, float linear_acceleration_y, float linear_acceleration_z, float acceleration, float ang_vel_x, float ang_vel_y, float ang_vel_z, float omega, String timestamp) {
        this.linear_acceleration_x = linear_acceleration_x;
        this.linear_acceleration_y = linear_acceleration_y;
        this.linear_acceleration_z = linear_acceleration_z;
        this.acceleration = acceleration;
        this.ang_vel_x = ang_vel_x;
        this.ang_vel_y = ang_vel_y;
        this.ang_vel_z = ang_vel_z;
        this.omega = omega;
        this.timestamp = timestamp;
    }

    public float getLinear_acceleration_x() {
        return linear_acceleration_x;
    }

    public void setLinear_acceleration_x(float linear_acceleration_x) {
        this.linear_acceleration_x = linear_acceleration_x;
    }

    public float getLinear_acceleration_y() {
        return linear_acceleration_y;
    }

    public void setLinear_acceleration_y(float linear_acceleration_y) {
        this.linear_acceleration_y = linear_acceleration_y;
    }

    public float getLinear_acceleration_z() {
        return linear_acceleration_z;
    }

    public void setLinear_acceleration_z(float linear_acceleration_z) {
        this.linear_acceleration_z = linear_acceleration_z;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getAng_vel_x() {
        return ang_vel_x;
    }

    public void setAng_vel_x(float ang_vel_x) {
        this.ang_vel_x = ang_vel_x;
    }

    public float getAng_vel_y() {
        return ang_vel_y;
    }

    public void setAng_vel_y(float ang_vel_y) {
        this.ang_vel_y = ang_vel_y;
    }

    public float getAng_vel_z() {
        return ang_vel_z;
    }

    public void setAng_vel_z(float ang_vel_z) {
        this.ang_vel_z = ang_vel_z;
    }

    public float getOmega() {
        return omega;
    }

    public void setOmega(float omega) {
        this.omega = omega;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
