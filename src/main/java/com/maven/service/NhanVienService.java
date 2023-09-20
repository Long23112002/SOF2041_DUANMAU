/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.service;

import com.maven.appservice.Dao_Interface;
import com.maven.model.NhanVien;
import com.maven.repository.NhanVienRepo;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class NhanVienService {
    NhanVienRepo nvrp = new NhanVienRepo();

    public List<NhanVien> getAll() {
        return nvrp.getAll();
    }


    public int add(NhanVien t) {
      return nvrp.add(t);
    }


    public int update(NhanVien t) {
       return nvrp.update(t);
    }


    public int delete(NhanVien t) {
       return nvrp.delete(t);
    }
    
}
