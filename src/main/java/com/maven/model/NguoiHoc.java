/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author long0
 */
@Entity
@Table(name = "NGUOIHOC")
public class NguoiHoc {

    @Id
    @Column(name = "MANH")
    private String maNH;

    @Column(name = "MATKHAU")
    private String matKhau;

    @Column(name = "GIOITINH")
    private Boolean gioiTinh;

    @Column(name = "NGAYSINH")
    private Date ngaySinh;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DIENTHOAI")
    private String dienThoai;

    @Column(name = "GHICHU")
    private String ghiChu;

    @Column(name = "MANV")
    private String maNV;

    @Column(name = "NGAYDK")
    private Date ngayDK;

    @ManyToOne
    @JoinColumn(name = "MANV", insertable = false, updatable = false)
    private NhanVien nhanVien;

    public NguoiHoc() {
    }

    public NguoiHoc(String maNH, String matKhau, Boolean gioiTinh, Date ngaySinh, String email, String dienThoai, String ghiChu, String maNV, Date ngayDK) {
        this.maNH = maNH;
        this.matKhau = matKhau;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.dienThoai = dienThoai;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.ngayDK = ngayDK;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
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

    public Date getNgayDK() {
        return ngayDK;
    }

    public void setNgayDK(Date ngayDK) {
        this.ngayDK = ngayDK;
    }

    public Object[] toData() {
        return new Object[]{
            this.maNH, this.maNV,
             this.gioiTinh, this.ngaySinh,
             this.email, this.matKhau,
             this.dienThoai, this.ghiChu,
             this.ngayDK};
    }

}
