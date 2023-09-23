/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.service;

import com.maven.appservice.Dao_Interface;
import com.maven.model.NguoiHoc;
import com.maven.repository.NguoiHocRepo;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class NguoiHocService{
   
    NguoiHocRepo nguoiHocRepo = new NguoiHocRepo();
   
    public int add(NguoiHoc t) {
       return  nguoiHocRepo.add(t);
    }

    public int update(NguoiHoc t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int delete(NguoiHoc t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
