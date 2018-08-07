/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Tax;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface TaxesDao {

    void loadTaxData() throws DataPersistenceException;

    List<Tax> getTaxes();

    Tax getTaxByIndex(int index);
}
