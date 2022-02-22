package com.andrew.isoip_final;

/**
 * Created by Андрей on 07.06.2018.
 */

public class ClassMarks {

    String MarkDiscipl;
    String RESultKT1;
    String RESultKT2;
    String RESultKT3;
    String RESultMark;
    String MaRkPrepod;
    String TipKontrol;
    String DaTeControl;

    public ClassMarks(String markDiscipl, String resultKT1, String resultKT2, String resultKT3, String resultMark, String markPrepod, String tipKontrol, String dateControl) {
        this.MarkDiscipl = markDiscipl;
        this.RESultKT1 = resultKT1;
        this.RESultKT2 = resultKT2;
        this.RESultKT3 = resultKT3;
        this.RESultMark = resultMark;
        this.MaRkPrepod = markPrepod;
        this.TipKontrol = tipKontrol;
        this.DaTeControl = dateControl;
    }

    public String getMarkDiscipl() {
        return MarkDiscipl;
    }

    public String getResultKT1() {
        return RESultKT1;
    }

    public String getResultKT2() {
        return RESultKT2;
    }

    public String getResultKT3() {
        return RESultKT3;
    }

    public String getResultMark() {
        return RESultMark;
    }

    public String getMarkPrepod() {
        return MaRkPrepod;
    }

    public String getTipKontrol() {
        return TipKontrol;
    }

    public String getDateControl() {
        return DaTeControl;
    }
}
