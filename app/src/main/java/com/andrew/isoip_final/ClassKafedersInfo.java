package com.andrew.isoip_final;



public class ClassKafedersInfo {

    public String nameKaf;
    public String sokrKaf;
    public String zavkaf;
    public String telKaf;
    public String audKaf;

    public ClassKafedersInfo(String nameKaf, String sokrKaf, String zavkaf, String telKaf, String audKaf) {
        this.nameKaf = nameKaf;
        this.sokrKaf = sokrKaf;
        this.zavkaf = zavkaf;
        this.telKaf = telKaf;
        this.audKaf = audKaf;
    }

    public String getNameKaf() {
        return nameKaf;
    }

    public String getSokrKaf() {
        return sokrKaf;
    }

    public String getZavkaf() {
        return zavkaf;
    }

    public String getTelKaf() {
        return telKaf;
    }

    public String getAudKaf() {
        return audKaf;
    }
}
