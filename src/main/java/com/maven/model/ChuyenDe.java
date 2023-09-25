/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author H.Long
 */
@Entity
@Table(name = "CHUYENDE")
public class ChuyenDe {
    
   @Id
    @Column(name = "MACD")
    private String maCD;

    @Column(name = "TENCD")
    private String tenCD;

    @Column(name = "HOCPHI")
    private float hocPhi;

    @Column(name = "THOILUONG")
    private int thoiLuong;

    @Column(name = "HINH")
    private String hinh;

    @Column(name = "MOTA")
    private String moTa;

    @OneToMany(mappedBy = "chuyenDe", cascade = CascadeType.ALL)
    private List<KhoaHoc> khoaHocList;

    public ChuyenDe() {
    }

    public ChuyenDe(String maCD, String tenCD, float hocPhi, int thoiLuong, String hinh, String moTa) {
        this.maCD = maCD;
        this.tenCD = tenCD;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.hinh = hinh;
        this.moTa = moTa;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public String getTenCD() {
        return tenCD;
    }

    public void setTenCD(String tenCD) {
        this.tenCD = tenCD;
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

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public List<KhoaHoc> getKhoaHocList() {
        return khoaHocList;
    }

    public void setKhoaHocList(List<KhoaHoc> khoaHocList) {
        this.khoaHocList = khoaHocList;
    }
    
    
    
    public Object[] toDataChuyenDe(){
        return new Object[]{this.maCD,this.tenCD,this.hocPhi,this.hinh,this.moTa};
    }
}
