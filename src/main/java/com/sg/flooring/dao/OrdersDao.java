/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Order;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface OrdersDao {

    void loadOrderData() throws DataPersistenceException;

    boolean writeOrderData() throws DataPersistenceException;

    List<Order> getAllOrders();

    void addOrder(Order order);

    void updateOrder(Order order);

    void loadEnvironmentVariable() throws DataPersistenceException;
}
