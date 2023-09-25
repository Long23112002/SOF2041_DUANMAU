/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.service;

import com.maven.model.ChuyenDe;
import com.maven.repository.ChuyenDeRepo;
import java.util.List;

/**
 *
 * @author long0
 */
public class ChuyenDeService {

    private ChuyenDeRepo cdrp = new ChuyenDeRepo();

    public int countChuyenDe() {
     return cdrp.countChuyenDe();
    }

    public List<ChuyenDe> listChuyenDe(int limit, int offset) {
     return cdrp.listChuyenDe(limit, offset);
    }
}
