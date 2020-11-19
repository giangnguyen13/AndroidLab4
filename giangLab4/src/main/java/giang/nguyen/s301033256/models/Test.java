package giang.nguyen.s301033256.models;

import androidx.annotation.NonNull;
/**
 * Giang Nguyen
 * Student# 301033256
 * COMP304 002
 * Professor: Haki Sharifi
 * */
public class Test {
    private long id;
    private long patient_id;
    private String bloodPressure;
    private String cholesterol; //respiratory
    private String temperature; //bloodOxygen
    private String heartBeatRate;
    private String testDate;
    private String covid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getHeartBeatRate() {
        return heartBeatRate;
    }

    public void setHeartBeatRate(String heartBeatRate) {
        this.heartBeatRate = heartBeatRate;
    }

    public String getCovid() {
        return covid;
    }

    public void setCovid(String covid) {
        this.covid = covid;
    }

    @Override
    public String toString() {
        return String.format("Patient ID#%s take test on %s", getId(), getTestDate());
    }
}
