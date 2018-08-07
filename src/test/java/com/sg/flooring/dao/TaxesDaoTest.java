/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Tax;
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
public class TaxesDaoTest {

    TaxesDao dao = new TaxesDaoImpl();

    public TaxesDaoTest() {
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
     * Test of loadTaxData method, of class TaxesDao. Also tested indirectly by
     * following TaxesDao method tests.
     */
    @Test
    public void testLoadTaxData() throws Exception {
        try {
            dao.loadTaxData();
        } catch (DataPersistenceException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test of getTaxes method, of class TaxesDao.
     */
    @Test
    public void testGetTaxes() throws Exception {
        dao.loadTaxData();

        assertEquals(9, dao.getTaxes().size());
        //Asserting specific value to ensure all rows of current file are read corretly.
        //assertTrue > 0 would allow for updates to data file, but not ensure
        //all rows are read correctly. With a db, assuming could plug return of a query on the
        //source so test assertion value would update with changes to source.
    }

    /**
     * Test of getTaxByIndex method, of class TaxesDao.
     */
    @Test
    public void testGetTaxByIndex() throws Exception {
        dao.loadTaxData();
        int index = 0;
        int[] indices = new int[dao.getTaxes().size()];
        Tax[] taxes = new Tax[dao.getTaxes().size()];

        for (Tax t : dao.getTaxes()) {
            indices[index] = index;
            taxes[index] = t;
            index++;
        }

        for (int i = 0; i < dao.getTaxes().size(); i++) {
            assertEquals(dao.getTaxByIndex(indices[i]), taxes[i]);
        }
    }

}
