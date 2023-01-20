package com.shchelokov.diploma.models;

public class LP {
    public String lp00;
    public String lp01;
    public String lp02;
    public String lp03;
    public String lp04;
    public String lp05;
    public String lp10;
    public String lp11;
    public String lp12;
    public String lp13;
    public String lp14;
    public String lp15;
    public String lp20;
    public String lp21;
    public String lp22;
    public String lp23;
    public String lp24;
    public String lp30;
    public String lp31;
    public String lp32;
    public String lp33;
    public String lp34;
    public String lp40;
    public String lp41;
    public String lp42;
    public String lp43;
    public String lp44;
    public String lp45;
    public String lp50;
    public String lp51;
    public String lp52;
    public String lp53;
    public String lp54;

    public int[][] getLP() {
        int[][] lp = new int[6][];
        lp[0] = new int[]{parseInt(lp00), parseInt(lp01), parseInt(lp02), parseInt(lp03), parseInt(lp04), parseInt(lp05)};
        lp[1] = new int[]{parseInt(lp10), parseInt(lp11), parseInt(lp12), parseInt(lp13), parseInt(lp14), parseInt(lp15)};
        lp[2] = new int[]{parseInt(lp20), parseInt(lp21), parseInt(lp22), parseInt(lp23), parseInt(lp24)};
        lp[3] = new int[]{parseInt(lp30), parseInt(lp31), parseInt(lp32), parseInt(lp33), parseInt(lp34)};
        lp[4] = new int[]{parseInt(lp40), parseInt(lp41), parseInt(lp42), parseInt(lp43), parseInt(lp44), parseInt(lp45)};
        lp[5] = new int[]{parseInt(lp50), parseInt(lp51), parseInt(lp52), parseInt(lp53), parseInt(lp54)};
        return lp;
    }

    private int parseInt(String s) {
        if (s == null || s.equals("")) return 0;
        else return Integer.parseInt(s);
    }

    public String getLp00() {
        return lp00;
    }

    public void setLp00(String lp00) {
        this.lp00 = lp00;
    }

    public String getLp01() {
        return lp01;
    }

    public void setLp01(String lp01) {
        this.lp01 = lp01;
    }

    public String getLp02() {
        return lp02;
    }

    public void setLp02(String lp02) {
        this.lp02 = lp02;
    }

    public String getLp03() {
        return lp03;
    }

    public void setLp03(String lp03) {
        this.lp03 = lp03;
    }

    public String getLp04() {
        return lp04;
    }

    public void setLp04(String lp04) {
        this.lp04 = lp04;
    }

    public String getLp05() {
        return lp05;
    }

    public void setLp05(String lp05) {
        this.lp05 = lp05;
    }

    public String getLp10() {
        return lp10;
    }

    public void setLp10(String lp10) {
        this.lp10 = lp10;
    }

    public String getLp11() {
        return lp11;
    }

    public void setLp11(String lp11) {
        this.lp11 = lp11;
    }

    public String getLp12() {
        return lp12;
    }

    public void setLp12(String lp12) {
        this.lp12 = lp12;
    }

    public String getLp13() {
        return lp13;
    }

    public void setLp13(String lp13) {
        this.lp13 = lp13;
    }

    public String getLp14() {
        return lp14;
    }

    public void setLp14(String lp14) {
        this.lp14 = lp14;
    }

    public String getLp15() {
        return lp15;
    }

    public void setLp15(String lp15) {
        this.lp15 = lp15;
    }

    public String getLp20() {
        return lp20;
    }

    public void setLp20(String lp20) {
        this.lp20 = lp20;
    }

    public String getLp21() {
        return lp21;
    }

    public void setLp21(String lp21) {
        this.lp21 = lp21;
    }

    public String getLp22() {
        return lp22;
    }

    public void setLp22(String lp22) {
        this.lp22 = lp22;
    }

    public String getLp23() {
        return lp23;
    }

    public void setLp23(String lp23) {
        this.lp23 = lp23;
    }

    public String getLp24() {
        return lp24;
    }

    public void setLp24(String lp24) {
        this.lp24 = lp24;
    }

    public String getLp30() {
        return lp30;
    }

    public void setLp30(String lp30) {
        this.lp30 = lp30;
    }

    public String getLp31() {
        return lp31;
    }

    public void setLp31(String lp31) {
        this.lp31 = lp31;
    }

    public String getLp32() {
        return lp32;
    }

    public void setLp32(String lp32) {
        this.lp32 = lp32;
    }

    public String getLp33() {
        return lp33;
    }

    public void setLp33(String lp33) {
        this.lp33 = lp33;
    }

    public String getLp34() {
        return lp34;
    }

    public void setLp34(String lp34) {
        this.lp34 = lp34;
    }

    public String getLp40() {
        return lp40;
    }

    public void setLp40(String lp40) {
        this.lp40 = lp40;
    }

    public String getLp41() {
        return lp41;
    }

    public void setLp41(String lp41) {
        this.lp41 = lp41;
    }

    public String getLp42() {
        return lp42;
    }

    public void setLp42(String lp42) {
        this.lp42 = lp42;
    }

    public String getLp43() {
        return lp43;
    }

    public void setLp43(String lp43) {
        this.lp43 = lp43;
    }

    public String getLp44() {
        return lp44;
    }

    public void setLp44(String lp44) {
        this.lp44 = lp44;
    }

    public String getLp45() {
        return lp45;
    }

    public void setLp45(String lp45) {
        this.lp45 = lp45;
    }

    public String getLp50() {
        return lp50;
    }

    public void setLp50(String lp50) {
        this.lp50 = lp50;
    }

    public String getLp51() {
        return lp51;
    }

    public void setLp51(String lp51) {
        this.lp51 = lp51;
    }

    public String getLp52() {
        return lp52;
    }

    public void setLp52(String lp52) {
        this.lp52 = lp52;
    }

    public String getLp53() {
        return lp53;
    }

    public void setLp53(String lp53) {
        this.lp53 = lp53;
    }

    public String getLp54() {
        return lp54;
    }

    public void setLp54(String lp54) {
        this.lp54 = lp54;
    }
}
