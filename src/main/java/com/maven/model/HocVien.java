/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author H.Long
 */
@Entity
@Table(name = "HOCVIEN")
public class HocVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAHV")
    private int maHV;

    @Column(name = "MAKH")
    private int maKH;

    @Column(name = "DIEM")
    private float diem;

    @Column(name = "MANH")
    private String maNH;
    
     @ManyToOne
    @JoinColumn(name = "MAKH", insertable = false, updatable = false)
    private KhoaHoc khoaHoc;

    @ManyToOne
    @JoinColumn(name = "MANH", insertable = false, updatable = false)
    private NguoiHoc nguoiHoc;
    
    public HocVien() {
    }

    public HocVien(int maHV, int maKH, float diem, String maNH) {
        this.maHV = maHV;
        this.maKH = maKH;
        this.diem = diem;
        this.maNH = maNH;
    }

    public int getMaHV() {
        return maHV;
    }

    public void setMaHV(int maHV) {
        this.maHV = maHV;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }
    
    
}
