/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.Tax;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author betzler
 */
public class OrdersDaoTest {

    OrdersDao dao = new OrdersDaoImpl();

    public OrdersDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadOrderData method, of class OrdersDao. This will never throw
     * an exception. If there are no order files in the directory no attempt is
     * made to read order data files. Otherwise on initial startup/delivery the
     * user would encounter an (somewhat confusing) error. And, the application
     * would not be usable until an order file were manually created.
     */
    @Test
    public void testLoadOrderData() throws Exception {
        dao.loadEnvironmentVariable();
        Order writeOrder = new Order();

        writeOrder.setOrderNumber(1);
        writeOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        writeOrder.setCustomer("Load Test");
        writeOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        writeOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        writeOrder.setArea(new BigDecimal("100.00"));
        writeOrder.setMaterialCost(new BigDecimal("515.00"));
        writeOrder.setLaborCost(new BigDecimal("475.00"));
        writeOrder.setTaxCost(new BigDecimal("61.88"));
        writeOrder.setTotal(new BigDecimal("1051.88"));

        assertTrue(dao.getAllOrders().isEmpty());

        dao.addOrder(writeOrder);
        dao.writeOrderData();

        dao = null;

        OrdersDao dao2 = new OrdersDaoImpl();

        assertTrue(dao2.getAllOrders().isEmpty());

        dao2.loadOrderData();

        assertTrue(dao2.getAllOrders().size() >= 1);

        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/Orders_01011900.txt"));
    }

    /**
     * Test of writeOrderData method, of class OrdersDao.
     */
    @Test
    public void testWriteOrderData() throws Exception {
        dao.loadEnvironmentVariable();
        Order writeOrder = new Order();

        writeOrder.setOrderNumber(1);
        writeOrder.setOrderDate(LocalDate.parse("1901-01-01"));
        writeOrder.setCustomer("Write Test");
        writeOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        writeOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        writeOrder.setArea(new BigDecimal("100.00"));
        writeOrder.setMaterialCost(new BigDecimal("515.00"));
        writeOrder.setLaborCost(new BigDecimal("475.00"));
        writeOrder.setTaxCost(new BigDecimal("61.88"));
        writeOrder.setTotal(new BigDecimal("1051.88"));

        dao.addOrder(writeOrder);
        dao.writeOrderData();

        File orderFile = new File(System.getProperty("user.dir") + "/Orders_01011901.txt");

        assertTrue(orderFile.exists());

        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/Orders_01011901.txt"));

    }

    /**
     * Test of getAllOrders method, of class OrdersDao.
     */
    @Test
    public void testGetAllOrders() {
        Order addedOrder = new Order();
        Order addedOrder2 = new Order();

        addedOrder.setOrderNumber(1);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order One");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        dao.addOrder(addedOrder);

        addedOrder2.setOrderNumber(2);
        addedOrder2.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder2.setCustomer("Order Two");
        addedOrder2.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder2.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder2.setArea(new BigDecimal("100.00"));
        addedOrder2.setMaterialCost(new BigDecimal("515.00"));
        addedOrder2.setLaborCost(new BigDecimal("475.00"));
        addedOrder2.setTaxCost(new BigDecimal("61.88"));
        addedOrder2.setTotal(new BigDecimal("1051.88"));

        dao.addOrder(addedOrder2);

        assertEquals(2, dao.getAllOrders().size());
    }

    /**
     * Test of addOrder method, of class OrdersDao.
     */
    @Test
    public void testAddOrder() {
        Order addedOrder = new Order();

        addedOrder.setOrderNumber(1);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Add Test");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        dao.addOrder(addedOrder);

        Order daoOrder = dao.getAllOrders().get(0);

        assertEquals(addedOrder, daoOrder);
    }

    /**
     * Test of updateOrder method, of class OrdersDao.
     */
    @Test
    public void testUpdateOrder() {
        Order addedOrder = new Order();

        addedOrder.setOrderNumber(1);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Update Test");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        dao.addOrder(addedOrder);

        addedOrder.setCustomer("Updated Order");

        dao.updateOrder(addedOrder);

        Order daoOrder = dao.getAllOrders().get(0);

        assertEquals(addedOrder, daoOrder);
    }

    /**
     * Test of loadEnvironmentVariable method, of class OrdersDao. Also
     * indirectly tested (more thoroughly) by test of writeOrderData and
     * loadOrderData above. Tests of loadOrderData and writeOrderData require a
     * Production environment to write Order data.
     */
    @Test
    public void testLoadEnvironmentVariable() throws Exception {
        try {
            dao.loadEnvironmentVariable();
        } catch (DataPersistenceException e) {
            fail(e.getMessage());
        }
    }
}
