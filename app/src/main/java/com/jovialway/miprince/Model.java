package com.jovialway.miprince;

public class Model {
    String surahName;
    String surah;



    public Model(String surahName, String surah) {
        this.surahName = surahName;
        this.surah = surah;

    }


    public String getSurahName() {
        return surahName;
    }

    public String getSurah() {
        return surah;
    }
}