/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.repository;
import com.maven.model.NguoiHoc;
import com.maven.appservice.Dao_Interface;
import com.maven.until.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author long0
 */
public class NguoiHocRepo implements Dao_Interface<NguoiHoc>{

    @Override
    public List<NguoiHoc> getAll() {
        Transaction tr = null;
        List<NguoiHoc> listNH = null;
        try (Session session = HibernateUtil.getFactory().openSession()){
            tr = session.beginTransaction();
            listNH = session.createQuery("SELECT u from NguoiHoc u",NguoiHoc.class).getResultList();
            session.close();
            return listNH;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(NguoiHoc t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(NguoiHoc t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(NguoiHoc t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
