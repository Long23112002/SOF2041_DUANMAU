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
    public List<NguoiHoc> getAll(){
        return nguoiHocRepo.getAll();
    }
    public int add(NguoiHoc t) {
       return  nguoiHocRepo.add(t);
    }

    public int update(NguoiHoc t) {
        return nguoiHocRepo.update(t);
    }

    public int delete(NguoiHoc t) {
        return nguoiHocRepo.delete(t);
    }
    
    public int rowCount(){
        return nguoiHocRepo.countNguoiHoc();
    }
    
    public List<NguoiHoc> listPhanTrang(int limit, int offset){
        return nguoiHocRepo.listNguoiHoc(limit, offset);
    }
    
}
