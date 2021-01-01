package com.example.seniorproject;

import java.io.Serializable;

public class AracIlani implements Serializable
{
    public String tarih;
    public String kullaniciAdi;
    public String kullaniciFBID;
    public String aciklama;
    public String id;


    public AracIlani()
    {

    }

    public AracIlani(String tarih, String kullaniciAdi, String aciklama) {
        this.tarih = tarih;
        this.kullaniciAdi = kullaniciAdi;
        this.aciklama = aciklama;
    }

    public String getKullaniciFBID() {
        return kullaniciFBID;
    }

    public void setKullaniciFBID(String kullaniciFBID) {
        this.kullaniciFBID = kullaniciFBID;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
