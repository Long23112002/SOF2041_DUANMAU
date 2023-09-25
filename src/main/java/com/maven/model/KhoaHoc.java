/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.model;

import java.util.Date;
import java.util.List;
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
@Table(name = "KHOAHOC")
public class KhoaHoc {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAKH")
    private int maKH;

    @Column(name = "MACD")
    private String maCD;

    @Column(name = "HOCPHI")
    private float hocPhi;

    @Column(name = "THOILUONG")
    private int thoiLuong;

    @Column(name = "NGAYKG")
    private Date ngayKG;

    @Column(name = "GHICHU")
    private String ghiChu;

    @Column(name = "MANV")
    private String maNV;

    @Column(name = "NGAYTAO")
    private Date ngayTao;

    @ManyToOne
    @JoinColumn(name = "MANV", insertable = false, updatable = false)
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MACD", insertable = false, updatable = false)
    private ChuyenDe chuyenDe;

    public KhoaHoc() {
    }

    public KhoaHoc(int maKH, String maCD, float hocPhi, int thoiLuong, Date ngayKG, String ghiChu, String maNV, Date ngayTao, NhanVien nhanVien, ChuyenDe chuyenDe) {
        this.maKH = maKH;
        this.maCD = maCD;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.ngayKG = ngayKG;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.ngayTao = ngayTao;
        this.nhanVien = nhanVien;
        this.chuyenDe = chuyenDe;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public float getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(float hocPhi) {
        this.hocPhi = hocPhi;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public Date getNgayKG() {
        return ngayKG;
    }

    public void setNgayKG(Date ngayKG) {
        this.ngayKG = ngayKG;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public ChuyenDe getChuyenDe() {
        return chuyenDe;
    }

    public void setChuyenDe(ChuyenDe chuyenDe) {
        this.chuyenDe = chuyenDe;
    }
    
    
}