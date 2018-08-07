/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.service;

import com.sg.flooring.dao.DataPersistenceException;
import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface ServiceLayer {

    void loadAllData() throws DataPersistenceException;

    List<Order> getActiveOrdersPerDate(LocalDate date) throws NoExistingOrderException;

    int getNextOrderNumber();

    String writeOrderData() throws DataPersistenceException;

    Order getActiveOrderByNumber(int orderNumber) throws NoExistingOrderException;

    String addOrder(Order order, boolean confirmation);

    String updateOrder(Order order, Order editedOrder, boolean confirmation);

    String cancelOrder(Order cancelledOrder, boolean confirmation);

    List<Product> getProducts();

    Product getProductByIndex(int index);

    void setEditedProduct(int product, Order editedOrder);

    List<Tax> getTaxes();

    Tax getTaxByIndex(int index);

    void setEditedTax(int tax, Order editedOrder);

    void calculateTotalCost(Order order);
}
