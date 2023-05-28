package com.if4a.projectpab.Model;

import java.util.List;

public class ModelResponses {
    private String kode,pesan;
    private List<ModelJajanan> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelJajanan> getData() {
        return data;
    }
}
