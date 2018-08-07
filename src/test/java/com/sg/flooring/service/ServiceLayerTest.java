/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.service;

//import com.sg.flooring.dao.DataPersistenceException;
import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author betzler
 */
public class ServiceLayerTest {

    private ServiceLayer service;

    public ServiceLayerTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        service
                = ctx.getBean("serviceLayer", ServiceLayer.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadAllData method, of class ServiceLayer.
     */
    //@Test
    public void testLoadAllData() throws Exception {
        //Pass through; each load method call is tested by individual Dao test.
    }

    /**
     * Test of getActiveOrdersPerDate method, of class ServiceLayer.
     */
    @Test
    public void testGetActiveOrdersPerDate() throws Exception {
        service.loadAllData();
        assertEquals(2, service.getActiveOrdersPerDate(LocalDate.parse("1901-01-01")).size());

        //Test sorting by Order Number
        assertTrue(service.getActiveOrdersPerDate(LocalDate.parse("1901-01-01")).get(0).getOrderNumber() < service.getActiveOrdersPerDate(LocalDate.parse("1901-01-01")).get(1).getOrderNumber());
    }

    @Test
    public void testGetActiveOrdersPerDateOrderCancelled() throws Exception {
        service.loadAllData();
        try {
            service.getActiveOrdersPerDate(LocalDate.parse("1902-01-01"));
            fail("There is no active order for 1902-01-01.");
        } catch (NoExistingOrderException e) {
            return;
        }
    }

    @Test
    public void testGetActiveOrdersPerDateNoOrders() throws Exception {
        service.loadAllData();
        try {
            service.getActiveOrdersPerDate(LocalDate.parse("1905-01-01"));
            fail("There are no orders for 1905-01-01.");
        } catch (NoExistingOrderException e) {
            return;
        }
    }

    /**
     * Test of getNextOrderNumber method, of class ServiceLayer.
     */
    @Test
    public void testGetNextOrderNumber() throws Exception {
        service.loadAllData();
        assertEquals(5, service.getNextOrderNumber());
    }

    /**
     * Test of writeOrderData method, of class ServiceLayer.
     */
    @Test
    public void testWriteOrderData() throws Exception {
        assertEquals("\nThere is no Order data to save.", service.writeOrderData());

        service.loadAllData();
        assertEquals("\nOrder data successfully saved to file.", service.writeOrderData());
    }

    /**
     * Test of getActiveOrderByNumber method, of class ServiceLayer.
     */
    @Test
    public void testGetActiveOrderByNumberOrderCancelled() throws Exception {
        service.loadAllData();
        try {
            service.getActiveOrderByNumber(3);
            fail("Order with Order Number = 3 is a cancelled Order.");
        } catch (NoExistingOrderException e) {
            return;
        }
    }

    @Test
    public void testGetActiveOrderByNumber() throws Exception {
        service.loadAllData();
        Order addedOrder = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        service.addOrder(addedOrder, true);

        assertEquals(addedOrder, service.getActiveOrderByNumber(5));
    }

    /**
     * Test of addOrder method, of class ServiceLayer.
     */
    @Test
    public void testAddOrderWithoutConfirmation() throws Exception {
        service.loadAllData();
        Order addedOrder = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        assertEquals("\nSave cancelled.", service.addOrder(addedOrder, false));

        try {
            service.getActiveOrderByNumber(5);
            fail("Order with Order Number = 5 was not confirmed to save.");
        } catch (NoExistingOrderException e) {
            return;
        }
    }

    @Test
    public void testAddOrderWithConfirmation() throws Exception {
        service.loadAllData();

        Order addedOrder = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        assertEquals("\nOrder successfully saved.", service.addOrder(addedOrder, true));

        assertEquals(addedOrder, service.getActiveOrderByNumber(5));
    }

    /**
     * Test of updateOrder method, of class ServiceLayer.
     */
    @Test
    public void testUpdateOrderWithConfirmation() throws Exception {
        service.loadAllData();

        Order addedOrder = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        service.addOrder(addedOrder, true);

        Order changedOrder = new Order(addedOrder);
        changedOrder.setCustomer("Changed the order");

        assertEquals("\nOrder successfully updated.", service.updateOrder(addedOrder, changedOrder, true));

        assertNotEquals(addedOrder, service.getActiveOrderByNumber(5));
        assertEquals(changedOrder, service.getActiveOrderByNumber(5));
    }

    @Test
    public void testUpdateOrderWithoutConfirmation() throws Exception {
        service.loadAllData();

        Order addedOrder = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        service.addOrder(addedOrder, true);

        Order changedOrder = new Order(addedOrder);
        changedOrder.setCustomer("Changed the order");

        assertEquals("\nUpdate cancelled.", service.updateOrder(addedOrder, changedOrder, false));

        assertEquals(addedOrder, service.getActiveOrderByNumber(5));
        assertNotEquals(changedOrder, service.getActiveOrderByNumber(5));
    }

    /**
     * Test of cancelOrder method, of class ServiceLayer.
     */
    @Test
    public void testCancelOrder() throws Exception {
        service.loadAllData();
        Order addedOrder = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        service.addOrder(addedOrder, true);

        assertEquals("\nOrder is still active.", service.cancelOrder(addedOrder, false));

        assertFalse(service.getActiveOrderByNumber(5).getCustomer().endsWith("(Cancelled)"));

        assertEquals("\nOrder successfully cancelled.", service.cancelOrder(addedOrder, true));
        
        assertTrue(addedOrder.getCustomer().endsWith("(Cancelled)"));

        try {
            service.getActiveOrderByNumber(5);
            fail("Order with Order Number = 5 is cancelled.");
        } catch (NoExistingOrderException e) {
            return;
        }
    }

    /**
     * Test of getProducts method, of class ServiceLayer.
     */
    @Test
    public void testGetProducts() throws Exception {
        service.loadAllData();
        List<Product> productList = service.getProducts();

        assertEquals(6, productList.size());
        //Asserting specific value to ensure all rows of current file are read corretly.
        //assertTrue > 0 would allow for updates to data file, but not ensure
        //all rows are read correctly. With a db, assuming could plug return of a query on the
        //source so test assertion value would update with changes to source.

        //Test sort
        assertTrue(productList.get(0).getType().compareTo(productList.get(1).getType()) < 0);
    }

    /**
     * Test of getProductByIndex method, of class ServiceLayer.
     */
    //@Test
    public void testGetProductByIndex() {
        //Pass through; tested in ProductsDaoTest.
    }

    /**
     * Test of setEditedProduct method, of class ServiceLayer.
     */
    @Test
    public void testSetEditedProduct() throws Exception {
        service.loadAllData();
        Order addedOrder = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(service.getProductByIndex(0));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        service.addOrder(addedOrder, true);

        Order editedOrder = new Order(addedOrder);

        service.setEditedProduct(-1, editedOrder);

        assertEquals(addedOrder, editedOrder);

        service.setEditedProduct(1, editedOrder);

        assertNotEquals(addedOrder, editedOrder);
    }

    /**
     * Test of getTaxes method, of class ServiceLayer.
     */
    @Test
    public void testGetTaxes() throws Exception {
        service.loadAllData();
        List<Tax> taxList = service.getTaxes();

        assertEquals(9, taxList.size());
        //Asserting specific value to ensure all rows of current file are read corretly.
        //assertTrue > 0 would allow for updates to data file, but not ensure
        //all rows are read correctly. With a db, assuming could plug return of a query on the
        //source so test assertion value would update with changes to source.

        //Test sort
        assertTrue(taxList.get(0).getState().compareTo(taxList.get(1).getState()) < 0);
    }

    /**
     * Test of getTaxByIndex method, of class ServiceLayer.
     */
    //@Test
    public void testGetTaxByIndex() {
        //Pass through; tested in TaxesDaoTest.
    }

    /**
     * Test of setEditedTax method, of class ServiceLayer.
     */
    @Test
    public void testSetEditedTax() throws Exception {
        service.loadAllData();
        Order addedOrder = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(service.getTaxByIndex(0));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("515.00"));
        addedOrder.setLaborCost(new BigDecimal("475.00"));
        addedOrder.setTaxCost(new BigDecimal("61.88"));
        addedOrder.setTotal(new BigDecimal("1051.88"));

        service.addOrder(addedOrder, true);

        Order editedOrder = new Order(addedOrder);

        service.setEditedTax(-1, editedOrder);

        assertEquals(addedOrder, editedOrder);

        service.setEditedTax(1, editedOrder);

        assertNotEquals(addedOrder, editedOrder);
    }

    /**
     * Test of calculateTotalCost method, of class ServiceLayer.
     */
    @Test
    public void testCalculateTotalCost() throws Exception {
        service.loadAllData();
        Order addedOrder = new Order();
        Order addedOrder2 = new Order();

        addedOrder.setOrderNumber(5);
        addedOrder.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder.setCustomer("Order Five");
        addedOrder.setTax(new Tax("OH", new BigDecimal("6.25")));
        addedOrder.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        addedOrder.setArea(new BigDecimal("100.00"));
        addedOrder.setMaterialCost(new BigDecimal("0"));
        addedOrder.setLaborCost(new BigDecimal("0"));
        addedOrder.setTaxCost(new BigDecimal("0"));
        addedOrder.setTotal(new BigDecimal("0"));

        service.addOrder(addedOrder, true);

        service.calculateTotalCost(addedOrder);

        addedOrder2.setOrderNumber(6);
        addedOrder2.setOrderDate(LocalDate.parse("1900-01-01"));
        addedOrder2.setCustomer("Order Six");
        addedOrder2.setTax(new Tax("CA", new BigDecimal("9.8")));
        addedOrder2.setProduct(new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.1")));
        addedOrder2.setArea(new BigDecimal("78"));
        addedOrder2.setMaterialCost(new BigDecimal("0"));
        addedOrder2.setLaborCost(new BigDecimal("0"));
        addedOrder2.setTaxCost(new BigDecimal("0"));
        addedOrder2.setTotal(new BigDecimal("0"));

        service.addOrder(addedOrder2, true);

        service.calculateTotalCost(addedOrder2);

        assertEquals(new BigDecimal("1051.88"), addedOrder.getTotal());
        assertEquals(new BigDecimal("372.55"), addedOrder2.getTotal());
    }
}
