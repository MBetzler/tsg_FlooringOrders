/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Tax;
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
public class TaxesDaoImpl implements TaxesDao {

    private static final String DELIMITER = ",";
    private static final String TAXES_FILE = "Taxes.txt";
    private Map<String, Tax> taxes = new TreeMap<>();

    @Override
    public void loadTaxData() throws DataPersistenceException {
        loadTaxes();
    }

    @Override
    public List<Tax> getTaxes() {
        return new ArrayList<>(taxes.values());
    }

    @Override
    public Tax getTaxByIndex(int index) {
        return getTaxes().get(index);
    }

    private void loadTaxes() throws DataPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e) {
            throw new DataPersistenceException(
                    "--> Could not load tax rate data into memory.", e);
        }

        String currentLine;
        String[] currentTokens;

        scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Tax currentTax = new Tax(currentTokens[0], new BigDecimal(currentTokens[1]));

            taxes.put(currentTax.getState(), currentTax);
        }

        scanner.close();
    }
}
