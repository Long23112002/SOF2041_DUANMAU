/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.repository;

import com.maven.appservice.Dao_Interface;
import com.maven.model.ChuyenDe;
import com.maven.model.NguoiHoc;
import com.maven.until.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author long0
 */
public class ChuyenDeRepo implements Dao_Interface<ChuyenDe> {

    @Override
    public List<ChuyenDe> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int add(ChuyenDe t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(ChuyenDe t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(ChuyenDe t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int countChuyenDe() {
        String countSql = "SELECT COUNT(*) FROM CHUYENDE";
        try (Session session = HibernateUtil.getFactory().openSession()) {
            return ((Number) session.createNativeQuery(countSql).uniqueResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<ChuyenDe> listChuyenDe(int limit, int offset) {
        String dataSql = "SELECT * FROM CHUYENDE ORDER BY MACD OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";
        try (Session session = HibernateUtil.getFactory().openSession()) {
            SQLQuery query = session.createSQLQuery(dataSql).addEntity(ChuyenDe.class)
                    .setParameter("limit", limit)
                    .setParameter("offset", offset);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
