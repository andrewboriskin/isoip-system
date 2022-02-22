package com.andrew.isoip_final;


public class ClassGroupInfo {


    public String stGroup;
    public String stFak;
    public String stSpec;
    public String stYear;
    public String stKolvo;
    public String stKurs;

    public ClassGroupInfo(String stGroup, String stFak, String stSpec, String stKurs, String stYear, String stKolvo) {
        this.stGroup = stGroup;
        this.stFak = stFak;
        this.stSpec = stSpec;
        this.stYear = stYear;
        this.stKolvo = stKolvo;
        this.stKurs = stKurs;
    }


    public String getStGroup() {
        return stGroup;
    }

    public String getStFak() {
        return stFak;
    }

    public String getStSpec() {
        return stSpec;
    }

    public String getStYear() {
        return stYear;
    }

    public String getStKolvo() {
        return stKolvo;
    }

    public String getStKurs() {
        return stKurs;
    }
}
