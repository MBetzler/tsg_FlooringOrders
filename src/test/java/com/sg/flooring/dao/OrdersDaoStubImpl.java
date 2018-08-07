/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author betzler
 */
public class OrdersDaoStubImpl implements OrdersDao {

    private Map<Integer, Order> orders = new TreeMap<>();

    @Override
    public void loadOrderData() throws DataPersistenceException {
        Order addedOrder1 = new Order();
        Order addedOrder2 = new Order();
        Order addedOrder3 = new Order();
        Order addedOrder4 = new Order();

        addedOrder1.setOrderNumber(4);
        addedOrder1.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder1.setCustomer("Order Four");
        addedOrder1.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder1.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder1.setArea(new BigDecimal("100.00"));
        addedOrder1.setMaterialCost(new BigDecimal("515.00"));
        addedOrder1.setLaborCost(new BigDecimal("475.00"));
        addedOrder1.setTaxCost(new BigDecimal("61.88"));
        addedOrder1.setTotal(new BigDecimal("1051.88"));

        orders.put(addedOrder1.getOrderNumber(), addedOrder1);

        addedOrder2.setOrderNumber(2);
        addedOrder2.setOrderDate(LocalDate.parse("1901-01-01"));
        addedOrder2.setCustomer("Order Two");
        addedOrder2.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder2.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder2.setArea(new BigDecimal("100.00"));
        addedOrder2.setMaterialCost(new BigDecimal("515.00"));
        addedOrder2.setLaborCost(new BigDecimal("475.00"));
        addedOrder2.setTaxCost(new BigDecimal("61.88"));
        addedOrder2.setTotal(new BigDecimal("1051.88"));

        orders.put(addedOrder2.getOrderNumber(), addedOrder2);

        addedOrder3.setOrderNumber(3);
        addedOrder3.setOrderDate(LocalDate.parse("1902-01-01"));
        addedOrder3.setCustomer("Order Three (Cancelled)");
        addedOrder3.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder3.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder3.setArea(new BigDecimal("100.00"));
        addedOrder3.setMaterialCost(new BigDecimal("515.00"));
        addedOrder3.setLaborCost(new BigDecimal("475.00"));
        addedOrder3.setTaxCost(new BigDecimal("61.88"));
        addedOrder3.setTotal(new BigDecimal("1051.88"));

        orders.put(addedOrder3.getOrderNumber(), addedOrder3);

        addedOrder4.setOrderNumber(1);
        addedOrder4.setOrderDate(LocalDate.parse("1901-01-01"));
        addedOrder4.setCustomer("Order One");
        addedOrder4.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder4.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder4.setArea(new BigDecimal("100.00"));
        addedOrder4.setMaterialCost(new BigDecimal("515.00"));
        addedOrder4.setLaborCost(new BigDecimal("475.00"));
        addedOrder4.setTaxCost(new BigDecimal("61.88"));
        addedOrder4.setTotal(new BigDecimal("1051.88"));

        orders.put(addedOrder4.getOrderNumber(), addedOrder4);
    }

    @Override
    public boolean writeOrderData() throws DataPersistenceException {
        return true;
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public void addOrder(Order order) {
        orders.put(order.getOrderNumber(), order);
    }

    @Override
    public void updateOrder(Order order) {
        orders.put(order.getOrderNumber(), order);
    }

    @Override
    public void loadEnvironmentVariable() throws DataPersistenceException {
        //Do nothing - not necessary
    }
}
