package com.andrew.isoip_final;

/**
 * Created by Андрей on 08.06.2018.
 */

public class ClassFackultInfo {

    String nameFack;
    String sokrFack;
    String dekanFack;
    String telFack;
    String audFack;

    public ClassFackultInfo(String nameFack, String sokrFack, String dekanFack, String telFack, String audFack) {
        this.nameFack = nameFack;
        this.sokrFack = sokrFack;
        this.dekanFack = dekanFack;
        this.telFack = telFack;
        this.audFack = audFack;
    }

    public String getNameFack() {
        return nameFack;
    }

    public String getSokrFack() {
        return sokrFack;
    }

    public String getDekanFack() {
        return dekanFack;
    }

    public String getTelFack() {
        return telFack;
    }

    public String getAudFack() {
        return audFack;
    }
}
