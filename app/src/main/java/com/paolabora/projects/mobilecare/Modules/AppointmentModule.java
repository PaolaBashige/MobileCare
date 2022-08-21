package com.paolabora.projects.mobilecare.Modules;

import androidx.annotation.NonNull;

public class AppointmentModule {
    private String doctorName, doctorId, dateSet, timeSet, patientName, patientId,
            appointmentReason, symptoms, symptomDesc, medications;

    public AppointmentModule() {
    }

    public AppointmentModule(String doctorName, String doctorId, String dateSet, String timeSet,
                             String patientName, String patientId, String appointmentReason,
                             String symptoms, String symptomDesc, String medications) {
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.dateSet = dateSet;
        this.timeSet = timeSet;
        this.patientName = patientName;
        this.patientId = patientId;
        this.appointmentReason = appointmentReason;
        this.symptoms = symptoms;
        this.symptomDesc = symptomDesc;
        this.medications = medications;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDateSet() {
        return dateSet;
    }

    public void setDateSet(String dateSet) {
        this.dateSet = dateSet;
    }

    public String getTimeSet() {
        return timeSet;
    }

    public void setTimeSet(String timeSet) {
        this.timeSet = timeSet;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentReason() {
        return appointmentReason;
    }

    public void setAppointmentReason(String appointmentReason) {
        this.appointmentReason = appointmentReason;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSymptomDesc() {
        return symptomDesc;
    }

    public void setSymptomDesc(String symptomDesc) {
        this.symptomDesc = symptomDesc;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    @NonNull
    @Override
    public String toString() {
        return dateSet;
    }
}
