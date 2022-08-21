package com.paolabora.projects.mobilecare.Modules;

public class AvailabilityModule {
    private String appDoctorName, appDoctorId, time, date, scheduleStatus, appId;

    public AvailabilityModule() {
    }

    public AvailabilityModule(String appDoctorName, String appDoctorId, String time, String date,
                              String scheduleStatus, String appId) {
        this.appDoctorName = appDoctorName;
        this.appDoctorId = appDoctorId;
        this.time = time;
        this.date = date;
        this.scheduleStatus = scheduleStatus;
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public String getAppDoctorName() {
        return appDoctorName;
    }

    public void setAppDoctorName(String appDoctorName) {
        this.appDoctorName = appDoctorName;
    }

    public String getAppDoctorId() {
        return appDoctorId;
    }

    public void setAppDoctorId(String appDoctorId) {
        this.appDoctorId = appDoctorId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
