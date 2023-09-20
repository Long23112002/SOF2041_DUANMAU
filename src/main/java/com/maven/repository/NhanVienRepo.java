/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.repository;

import com.maven.appservice.Dao_Interface;
import com.maven.model.NhanVien;
import com.maven.until.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;



/**
 *
 * @author long0
 */
public class NhanVienRepo implements Dao_Interface<NhanVien> {

    @Override
    public List<NhanVien> getAll() {
        List<NhanVien> list = null;
        Transaction tr = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            if (session != null) {
                tr = session.beginTransaction();
                list = session.createQuery("Select u from NhanVien u", NhanVien.class).getResultList();
                tr.commit();
                session.close();
            }
            return list;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(NhanVien t) {
      Transaction tr = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tr = session.beginTransaction();
            session.saveOrUpdate(t);
            tr.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(NhanVien t) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tr = session.beginTransaction();
            session.merge(t);
            tr.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            if(tr !=  null){
                tr.rollback();
            } 
            e.printStackTrace();
            return 0;
        }
    }

    @Override
public int delete(NhanVien t) {
    Transaction tr = null;
    try (Session session = HibernateUtil.getFactory().openSession()) {
        tr = session.beginTransaction();
        session.delete(t);
        tr.commit();
        return 1;
    } catch (Exception e) {
        if (tr != null) {
            tr.rollback();
        }
        e.printStackTrace();
        return 0;
    }
}

}
