package com.paolabora.projects.mobilecare.Modules;

public class DoctorUpdate {
    private String patientName, doctorName, patientSymptoms, symptomDescription, diagnosis,
            treatment, comment, senderId, receiverId;

    public DoctorUpdate() {
    }

    public DoctorUpdate(String patientName, String doctorName, String patientSymptoms,
                        String symptomDescription, String diagnosis, String treatment,
                        String comment, String senderId, String receiverId) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.patientSymptoms = patientSymptoms;
        this.symptomDescription = symptomDescription;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.comment = comment;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientSymptoms() {
        return patientSymptoms;
    }

    public void setPatientSymptoms(String patientSymptoms) {
        this.patientSymptoms = patientSymptoms;
    }

    public String getSymptomDescription() {
        return symptomDescription;
    }

    public void setSymptomDescription(String symptomDescription) {
        this.symptomDescription = symptomDescription;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
