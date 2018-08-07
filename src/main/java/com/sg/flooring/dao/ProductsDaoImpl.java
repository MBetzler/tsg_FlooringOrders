/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author betzler
 */
public class ProductsDaoImpl implements ProductsDao {

    private static final String DELIMITER = ",";
    private static final String PRODUCTS_FILE = "Products.txt";
    private Map<String, Product> products = new TreeMap<>();

    @Override
    public void loadProductData() throws DataPersistenceException {
        loadProducts();
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getProductByIndex(int index) {
        return getProducts().get(index);
    }

    private void loadProducts() throws DataPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new DataPersistenceException(
                    "--> Could not load product data into memory.", e);
        }

        String currentLine;
        String[] currentTokens;

        scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Product currentProduct = new Product(currentTokens[0], new BigDecimal(currentTokens[1]), new BigDecimal(currentTokens[2]));

            products.put(currentProduct.getType(), currentProduct);
        }

        scanner.close();
    }
}
