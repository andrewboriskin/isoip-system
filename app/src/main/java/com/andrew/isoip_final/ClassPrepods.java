package com.andrew.isoip_final;



public class ClassPrepods {

    String img;
    String namePrepod;
    String Dolzhnost;

    public ClassPrepods(String namePrepod, String img, String Dolzhnost) {
        this.namePrepod = namePrepod;
        this.img = img;
        this.Dolzhnost = Dolzhnost;
    }

    public String getImg() {
        return img;
    }

    public String getNamePrepod() {
        return namePrepod;
    }

    public String getDolzhnost() {
        return Dolzhnost;
    }
}
