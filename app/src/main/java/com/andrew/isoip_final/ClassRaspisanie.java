package com.andrew.isoip_final;



public class ClassRaspisanie {


    public String raspDay;
    public String raspTime;
    //public String raspNedelya;
    public String raspDisciplina;
    public String raspPrerod;
    public String raspAud;

    public ClassRaspisanie(String raspDay, String raspTime, String raspDisciplina, String raspPrerod, String raspAud) {

        this.raspDay = raspDay;
        this.raspTime = raspTime;
        //this.raspNedelya = raspNedelya;
        this.raspDisciplina = raspDisciplina;
        this.raspPrerod = raspPrerod;
        this.raspAud = raspAud;
    }



    public String getRaspDay() {
        return raspDay;
    }

    public String getRaspTime() {
        return raspTime;
    }

    //public String getRaspNedelya() {
        //return raspNedelya;
    //}

    public String getRaspDisciplina() {
        return raspDisciplina;
    }

    public String getRaspPrerod() {
        return raspPrerod;
    }

    public String getRaspAud() {
        return raspAud;
    }
}
