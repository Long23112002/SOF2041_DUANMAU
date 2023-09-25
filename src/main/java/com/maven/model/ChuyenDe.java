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
}
