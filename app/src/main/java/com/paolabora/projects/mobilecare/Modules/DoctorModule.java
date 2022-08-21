package com.paolabora.projects.mobilecare.Modules;

public class DoctorModule {
    private String doctorsFistName, doctorsLastName, doctorsEmailAddress, doctorsGender,
            doctorsSpeciality, doctorProfilePic, doctorId, doctorProfile;

    public DoctorModule() {
    }

    public DoctorModule(String doctorsFistName, String doctorsLastName, String doctorsEmailAddress,
                        String doctorsGender, String doctorsSpeciality, String doctorProfilePic,
                        String doctorId, String doctorProfile) {
        this.doctorsFistName = doctorsFistName;
        this.doctorsLastName = doctorsLastName;
        this.doctorsEmailAddress = doctorsEmailAddress;
        this.doctorsGender = doctorsGender;
        this.doctorsSpeciality = doctorsSpeciality;
        this.doctorProfilePic = doctorProfilePic;
        this.doctorId = doctorId;
        this.doctorProfile = doctorProfile;
    }

    public String getDoctorProfile() {
        return doctorProfile;
    }

    public void setDoctorProfile(String doctorProfile) {
        this.doctorProfile = doctorProfile;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorProfilePic() {
        return doctorProfilePic;
    }

    public void setDoctorProfilePic(String doctorProfilePic) {
        this.doctorProfilePic = doctorProfilePic;
    }

    public String getDoctorsFistName() {
        return doctorsFistName;
    }

    public void setDoctorsFistName(String doctorsFistName) {
        this.doctorsFistName = doctorsFistName;
    }

    public String getDoctorsLastName() {
        return doctorsLastName;
    }

    public void setDoctorsLastName(String doctorsLastName) {
        this.doctorsLastName = doctorsLastName;
    }

    public String getDoctorsEmailAddress() {
        return doctorsEmailAddress;
    }

    public void setDoctorsEmailAddress(String doctorsEmailAddress) {
        this.doctorsEmailAddress = doctorsEmailAddress;
    }

    public String getDoctorsGender() {
        return doctorsGender;
    }

    public void setDoctorsGender(String doctorsGender) {
        this.doctorsGender = doctorsGender;
    }

    public String getDoctorsSpeciality() {
        return doctorsSpeciality;
    }

    public void setDoctorsSpeciality(String doctorsSpeciality) {
        this.doctorsSpeciality = doctorsSpeciality;
    }
}
