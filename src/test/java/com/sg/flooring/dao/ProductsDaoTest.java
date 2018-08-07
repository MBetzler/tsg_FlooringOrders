/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Product;
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
public class ProductsDaoTest {

    ProductsDao dao = new ProductsDaoImpl();

    public ProductsDaoTest() {
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
     * Test of loadProductData method, of class ProductsDao. Also tested
     * indirectly by following ProductsDao method tests.
     */
    @Test
    public void testLoadProductData() throws Exception {
        try {
            dao.loadProductData();
        } catch (DataPersistenceException e) {
            fail(e.getMessage());
        }

    }

    /**
     * Test of getProducts method, of class ProductsDao.
     */
    @Test
    public void testGetProducts() throws Exception {
        dao.loadProductData();

        assertEquals(6, dao.getProducts().size());
        //Asserting specific value to ensure all rows of current file are read corretly.
        //assertTrue > 0 would allow for updates to data file, but not ensure
        //all rows are read correctly. With a db, assuming could plug return of a query on the
        //source so test assertion value would update with changes to source.
    }

    /**
     * Test of getProductByIndex method, of class ProductsDao.
     */
    @Test
    public void testGetProductByIndex() throws Exception {
        dao.loadProductData();

        int index = 0;
        int[] indices = new int[dao.getProducts().size()];
        Product[] products = new Product[dao.getProducts().size()];

        for (Product prods : dao.getProducts()) {
            indices[index] = index;
            products[index] = prods;
            index++;
        }

        for (int i = 0; i < dao.getProducts().size(); i++) {
            assertEquals(dao.getProductByIndex(indices[i]), products[i]);
        }
    }

}
