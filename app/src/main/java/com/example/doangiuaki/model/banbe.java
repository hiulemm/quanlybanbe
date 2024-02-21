package com.example.doangiuaki.model;

import java.io.Serializable;
import java.util.Date;

public class banbe implements Serializable {


    private int id;
    private String hoTen;
    private String bietDanh;
    private Date ngaySinh;
    private String faceboook;
    private String instagram;
    private String zalo;
    private String gmail;
    private byte[] avt;

    public banbe(){

    }
    public banbe(int id, String hoTen, String bietDanh, Date ngaySinh, String faceboook, String instagram, String zalo, String gmail, byte[] avt) {
        this.id = id;
        this.hoTen = hoTen;
        this.bietDanh = bietDanh;
        this.ngaySinh = ngaySinh;
        this.faceboook = faceboook;
        this.instagram = instagram;
        this.zalo = zalo;
        this.gmail = gmail;
        this.avt = avt;
    }
    public banbe( String hoTen, String bietDanh, Date ngaySinh, String faceboook, String instagram, String zalo, String gmail, byte[] avt) {
        this.hoTen = hoTen;
        this.bietDanh = bietDanh;
        this.ngaySinh = ngaySinh;
        this.faceboook = faceboook;
        this.instagram = instagram;
        this.zalo = zalo;
        this.gmail = gmail;
        this.avt = avt;
    }

    public int getId() { return id;}
    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getBietDanh() {
        return bietDanh;
    }

    public void setBietDanh(String bietDanh) {
        this.bietDanh = bietDanh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getFaceboook() {
        return faceboook;
    }

    public void setFaceboook(String faceboook) {
        this.faceboook = faceboook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public byte[] getAvt() {
        return avt;
    }

    public void setAvt(byte[] avt) {
        this.avt = avt;
    }



}
