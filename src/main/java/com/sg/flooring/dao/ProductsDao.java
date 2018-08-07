/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Product;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface ProductsDao {

    void loadProductData() throws DataPersistenceException;

    List<Product> getProducts();

    Product getProductByIndex(int index);
}
