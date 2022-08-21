package com.paolabora.projects.mobilecare.Modules;

public class PaymentModule {
    private String subscriber, cardNumber, expirationYear, cvv, postalCde,
            countryCode, mobileNumber;

    public PaymentModule() {
    }

    public PaymentModule(String subscriber, String cardNumber, String expirationYear, String cvv,
                         String postalCde, String countryCode,
                         String mobileNumber) {
        this.subscriber = subscriber;
        this.cardNumber = cardNumber;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
        this.postalCde = postalCde;
        this.countryCode = countryCode;
        this.mobileNumber = mobileNumber;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPostalCde() {
        return postalCde;
    }

    public void setPostalCde(String postalCde) {
        this.postalCde = postalCde;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
