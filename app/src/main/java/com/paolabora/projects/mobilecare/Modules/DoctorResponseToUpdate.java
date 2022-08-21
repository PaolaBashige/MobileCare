package com.paolabora.projects.mobilecare.Modules;

public class DoctorResponseToUpdate {
    private String senderId, receiverId, diagnosis, treatment, patientComment, doctorResponse;

    public DoctorResponseToUpdate() {
    }

    public DoctorResponseToUpdate(String senderId, String receiverId, String diagnosis,
                                  String treatment, String patientComment, String doctorResponse) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.patientComment = patientComment;
        this.doctorResponse = doctorResponse;
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

    public String getPatientComment() {
        return patientComment;
    }

    public void setPatientComment(String patientComment) {
        this.patientComment = patientComment;
    }

    public String getDoctorResponse() {
        return doctorResponse;
    }

    public void setDoctorResponse(String doctorResponse) {
        this.doctorResponse = doctorResponse;
    }
}
