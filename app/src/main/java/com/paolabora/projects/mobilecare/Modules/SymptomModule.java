package com.paolabora.projects.mobilecare.Modules;

public class SymptomModule {
    private String symptoms, symptomDesc, medicAns, medications, travelAns, latestTravels, changesAns,
            lifeChanges, sender, receiver, uploadTime, symptomsStatus, symptomId;

    public SymptomModule() {
    }

    public SymptomModule(String symptoms, String symptomDesc, String medicAns, String medications,
                         String travelAns, String latestTravels, String changesAns,
                         String lifeChanges, String sender, String receiver, String uploadTime,
                         String symptomsStatus, String symptomId) {
        this.symptoms = symptoms;
        this.symptomDesc = symptomDesc;
        this.medicAns = medicAns;
        this.medications = medications;
        this.travelAns = travelAns;
        this.latestTravels = latestTravels;
        this.changesAns = changesAns;
        this.lifeChanges = lifeChanges;
        this.sender = sender;
        this.receiver = receiver;
        this.uploadTime = uploadTime;
        this.symptomsStatus = symptomsStatus;
        this.symptomId = symptomId;
    }

    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomsStatus() {
        return symptomsStatus;
    }

    public void setSymptomsStatus(String symptomsStatus) {
        this.symptomsStatus = symptomsStatus;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public String getMedicAns() {
        return medicAns;
    }

    public void setMedicAns(String medicAns) {
        this.medicAns = medicAns;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getTravelAns() {
        return travelAns;
    }

    public void setTravelAns(String travelAns) {
        this.travelAns = travelAns;
    }

    public String getLatestTravels() {
        return latestTravels;
    }

    public void setLatestTravels(String latestTravels) {
        this.latestTravels = latestTravels;
    }

    public String getChangesAns() {
        return changesAns;
    }

    public void setChangesAns(String changesAns) {
        this.changesAns = changesAns;
    }

    public String getLifeChanges() {
        return lifeChanges;
    }

    public void setLifeChanges(String lifeChanges) {
        this.lifeChanges = lifeChanges;
    }
}
