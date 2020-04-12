package com.example.crubfirebasemovilesii.models;

import java.io.Serializable;

public class TituloModel implements Serializable {
    String mid;
    String mtitulo;
    String mplataforma;

    public TituloModel() {
    }

    public TituloModel(String mid, String mtitulo, String mplataforma) {
        this.mid = mid;
        this.mtitulo = mtitulo;
        this.mplataforma = mplataforma;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMtitulo() {
        return mtitulo;
    }

    public void setMtitulo(String mtitulo) {
        this.mtitulo = mtitulo;
    }

    public String getMplataforma() {
        return mplataforma;
    }

    public void setMplataforma(String mplataforma) {
        this.mplataforma = mplataforma;
    }
}
