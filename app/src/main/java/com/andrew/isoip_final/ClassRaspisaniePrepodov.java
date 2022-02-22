package com.andrew.isoip_final;



public class ClassRaspisaniePrepodov {

    String RaspDayPrep;
    String RaspTimePrep;
    String RaspDiscpPrep;
    String RaspGroupPrep;
    String RaspAudPrep;

    public ClassRaspisaniePrepodov(String raspDayPrep, String raspTimePrep, String raspDiscpPrep, String raspGroupPrep, String raspAudPrep) {
        this.RaspDayPrep = raspDayPrep;
        this.RaspTimePrep = raspTimePrep;
        this.RaspDiscpPrep = raspDiscpPrep;
        this.RaspGroupPrep = raspGroupPrep;
        this.RaspAudPrep = raspAudPrep;

    }

    public String getRaspDayPrep() {
        return RaspDayPrep;
    }

    public String getRaspTimePrep() {
        return RaspTimePrep;
    }

    public String getRaspDiscpPrep() {
        return RaspDiscpPrep;
    }

    public String getRaspGroupPrep() {
        return RaspGroupPrep;
    }

    public String getRaspAudPrep() {
        return RaspAudPrep;
    }
}
