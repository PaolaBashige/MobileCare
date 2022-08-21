package com.paolabora.projects.mobilecare.Modules;

public class PatientModule {
    private String patientFirstName, patientMiddleName, patientLastName, patientEmailAddress,
            patientDateOfBirth, patientRace, patientGender, patientId;

    public PatientModule() {
    }

    public PatientModule(String patientFirstName, String patientMiddleName, String patientLastName,
                         String patientEmailAddress, String patientDateOfBirth, String patientRace,
                         String patientGender, String patientId) {
        this.patientFirstName = patientFirstName;
        this.patientMiddleName = patientMiddleName;
        this.patientLastName = patientLastName;
        this.patientEmailAddress = patientEmailAddress;
        this.patientDateOfBirth = patientDateOfBirth;
        this.patientRace = patientRace;
        this.patientGender = patientGender;
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientMiddleName() {
        return patientMiddleName;
    }

    public void setPatientMiddleName(String patientMiddleName) {
        this.patientMiddleName = patientMiddleName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientEmailAddress() {
        return patientEmailAddress;
    }

    public void setPatientEmailAddress(String patientEmailAddress) {
        this.patientEmailAddress = patientEmailAddress;
    }

    public String getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    public void setPatientDateOfBirth(String patientDateOfBirth) {
        this.patientDateOfBirth = patientDateOfBirth;
    }

    public String getPatientRace() {
        return patientRace;
    }

    public void setPatientRace(String patientRace) {
        this.patientRace = patientRace;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }
}
