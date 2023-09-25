/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.repository;

import com.maven.model.NguoiHoc;
import com.maven.appservice.Dao_Interface;
import com.maven.until.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author long0
 */
public class NguoiHocRepo implements Dao_Interface<NguoiHoc> {

    @Override
    public List<NguoiHoc> getAll() {
        Transaction tr = null;
        List<NguoiHoc> listNH = null;
        try ( Session session = HibernateUtil.getFactory().openSession()) {
            tr = session.beginTransaction();
            listNH = session.createQuery("SELECT u from NguoiHoc u", NguoiHoc.class).getResultList();
            session.close();
            return listNH;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(NguoiHoc t) {
        Transaction tr = null;
        try ( Session session = HibernateUtil.getFactory().openSession()) {
            tr = session.beginTransaction();
            session.save(t);
            tr.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public int update(NguoiHoc t) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getFactory().openSession()){
            tr = session.beginTransaction();
            session.merge(t);
            tr.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            if(tr != null){
                tr.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(NguoiHoc t) {
       Transaction tr = null;
        try(Session session = HibernateUtil.getFactory().openSession()) {
            tr = session.beginTransaction();
            session.delete(t);
            tr.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

 public int countNguoiHoc() {
        String countSql = "SELECT COUNT(*) FROM NGUOIHOC";
        try (Session session = HibernateUtil.getFactory().openSession()) {
            return ((Number) session.createNativeQuery(countSql).uniqueResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<NguoiHoc> listNguoiHoc(int limit, int offset) {
        String dataSql = "SELECT * FROM NGUOIHOC ORDER BY MANH OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";
        try (Session session = HibernateUtil.getFactory().openSession()) {
            SQLQuery query = session.createSQLQuery(dataSql).addEntity(NguoiHoc.class)
                    .setParameter("limit", limit)
                    .setParameter("offset", offset);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
