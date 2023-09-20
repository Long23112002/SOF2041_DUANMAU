/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maven.appservice;

import java.util.List;

/**
 *
 * @author long0
 */
public interface Dao_Interface <T>{
    
    public List<T> getAll();
    
    public int add(T t);
    
    public int update(T t);
    
    public int delete(T t);
    
    
}
