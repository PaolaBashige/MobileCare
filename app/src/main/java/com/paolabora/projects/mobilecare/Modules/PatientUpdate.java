package com.paolabora.projects.mobilecare.Modules;

public class PatientUpdate {
    private String patientName, doctorName, docDiagnosis, docTreatmentSuggestion, docComments,
            patientComment, senderId, receiverId;

    public PatientUpdate() {
    }

    public PatientUpdate(String patientName, String doctorName, String docDiagnosis,
                         String docTreatmentSuggestion, String docComments, String patientComment,
                         String senderId, String receiverId) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.docDiagnosis = docDiagnosis;
        this.docTreatmentSuggestion = docTreatmentSuggestion;
        this.docComments = docComments;
        this.patientComment = patientComment;
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

    public String getDocDiagnosis() {
        return docDiagnosis;
    }

    public void setDocDiagnosis(String docDiagnosis) {
        this.docDiagnosis = docDiagnosis;
    }

    public String getDocTreatmentSuggestion() {
        return docTreatmentSuggestion;
    }

    public void setDocTreatmentSuggestion(String docTreatmentSuggestion) {
        this.docTreatmentSuggestion = docTreatmentSuggestion;
    }

    public String getDocComments() {
        return docComments;
    }

    public void setDocComments(String docComments) {
        this.docComments = docComments;
    }

    public String getPatientComment() {
        return patientComment;
    }

    public void setPatientComment(String patientComment) {
        this.patientComment = patientComment;
    }
}
