package com.byngetutor.booking_hotel_191074;

public class Data {
    private String Nama, No_wa, Tipe_kamar, Total, Id_hotel, Jumlah_orang, Jumlah_hari;

    public Data() {
    }

    public Data(String id_hotel, String nama, String no_wa, String tipe_kamar, String jumlah_orang, String jumlah_hari, String total) {
        this.Id_hotel = id_hotel;
        this.Nama = nama;
        this.No_wa = no_wa;
        this.Tipe_kamar = tipe_kamar;
        this.Jumlah_orang = jumlah_orang;
        this.Jumlah_hari = jumlah_hari;
        this.Total = total;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNo_wa() {
        return No_wa;
    }

    public void setNo_wa(String no_wa) {
        No_wa = no_wa;
    }

    public String getTipe_kamar() {
        return Tipe_kamar;
    }

    public void setTipe_kamar(String tipe_kamar) {
        Tipe_kamar = tipe_kamar;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getId_hotel() {
        return Id_hotel;
    }

    public void setId_hotel(String id_hotel) {
        Id_hotel = id_hotel;
    }

    public String getJumlah_orang() {
        return Jumlah_orang;
    }

    public void setJumlah_orang(String jumlah_orang) {
        Jumlah_orang = jumlah_orang;
    }

    public String getJumlah_hari() {
        return Jumlah_hari;
    }

    public void setJumlah_hari(String jumlah_hari) {
        Jumlah_hari = jumlah_hari;
    }
}
