package com.paolabora.projects.mobilecare.Modules;

public class ScheduleSlotList {
    private String doctorName, dateSet, timeSet, scheduleStatus, doctorId;

    public ScheduleSlotList() {
    }

    public ScheduleSlotList(String doctorName, String dateSet, String timeSet, String scheduleStatus,
                            String doctorId) {
        this.doctorName = doctorName;
        this.dateSet = dateSet;
        this.timeSet = timeSet;
        this.scheduleStatus = scheduleStatus;
        this.doctorId = doctorId;
    }

    public String getTimeSet() {
        return timeSet;
    }

    public void setTimeSet(String timeSet) {
        this.timeSet = timeSet;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDateSet() {
        return dateSet;
    }

    public void setDateSet(String dateSet) {
        this.dateSet = dateSet;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }
}
