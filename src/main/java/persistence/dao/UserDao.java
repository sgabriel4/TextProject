/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.dao;

import model.User;

/**
 * Patron DAO de la entidad User. Se abstraen los metodos necesarios para trabajar con la clase User
 * @author Sergio
 */
public interface UserDao {
    
    public User search(String nombre, String password);

    public boolean add(User user);

    
}
