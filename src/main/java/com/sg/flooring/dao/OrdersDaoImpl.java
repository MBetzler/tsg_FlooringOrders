/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dao;

import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author betzler
 */
public class OrdersDaoImpl implements OrdersDao {

    private static final String DELIMITER = ",";
    private static final String ENVIRONMENT_FILE = "Production.txt";
    private boolean production;
    private Map<Integer, Order> orders = new TreeMap<>();

    @Override
    public void loadOrderData() throws DataPersistenceException {
        loadOrders();
    }

    @Override
    public boolean writeOrderData() throws DataPersistenceException {
        if (production) {
            writeOrders();
        }

        return production;
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
        loadProduction();
    }

    private List<File> getOrderDataFiles() {
        File folder = new File(System.getProperty("user.dir"));

        List<File> orderFiles = Arrays.asList(folder.listFiles()).stream()
                .filter((f) -> f.isFile())
                .filter((f) -> f.getName().length() == 19)
                .filter((f) -> f.getName().toLowerCase().endsWith(".txt"))
                .filter((f) -> f.getName().toLowerCase().startsWith("orders_"))
                .collect(Collectors.toList());

        return orderFiles;
    }

    private void loadOrders() throws DataPersistenceException {
        Scanner scanner;
        String orderFile;
        LocalDate fileDate;

        List<File> orderFiles = getOrderDataFiles();

        for (File file : orderFiles) {
            orderFile = file.getName();
            fileDate = LocalDate.parse(file.getName().substring(7, 15), DateTimeFormatter.ofPattern("MMddyyyy"));

            try {
                scanner = new Scanner(
                        new BufferedReader(
                                new FileReader(orderFile)));
            } catch (FileNotFoundException e) {
                throw new DataPersistenceException(
                        "--> Could not load order data into memory.", e);
            }

            String currentLine;
            String[] currentTokens;
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentTokens = currentLine.split(DELIMITER);
                Order currentOrder = new Order(Integer.parseInt(currentTokens[0]));
                currentOrder.setCustomer(currentTokens[1]);
                currentOrder.setTax(new Tax(currentTokens[2], new BigDecimal(currentTokens[3])));
                currentOrder.setProduct(new Product(currentTokens[4], new BigDecimal(currentTokens[6]), new BigDecimal(currentTokens[7])));
                currentOrder.setArea(new BigDecimal(currentTokens[5]));
                currentOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
                currentOrder.setLaborCost(new BigDecimal(currentTokens[9]));
                currentOrder.setTaxCost(new BigDecimal(currentTokens[10]));
                currentOrder.setTotal(new BigDecimal(currentTokens[11]));
                currentOrder.setOrderDate(fileDate);

                orders.put(currentOrder.getOrderNumber(), currentOrder);
            }
            scanner.close();
        }
    }

    private void writeOrders() throws DataPersistenceException {
        PrintWriter out;

        List<Order> orderList = new ArrayList<>(orders.values());

        Map<LocalDate, List<Order>> groupedByDate = orderList.stream()
                .collect(Collectors.groupingBy((r) -> r.getOrderDate()));

        Set<LocalDate> keys = groupedByDate.keySet();

        for (LocalDate d : keys) {
            List<Order> ords = new ArrayList<>();
            ords = groupedByDate.get(d);

            String thisOrder = "Orders_" + d.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";

            try {
                out = new PrintWriter(new FileWriter(thisOrder));
            } catch (IOException e) {
                throw new DataPersistenceException(
                        "--> Could not save order data to file.", e);
            }

            out.println(Order.getFieldList());
            out.flush();

            for (Order currentOrder : ords) {
                out.println(currentOrder.getDelimited(DELIMITER));
                out.flush();
            }
            out.close();
        }
    }

    private void loadProduction() throws DataPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ENVIRONMENT_FILE)));
        } catch (FileNotFoundException e) {
            throw new DataPersistenceException(
                    "--> Could not load environment setting into memory.", e);
        }

        production = Boolean.parseBoolean(scanner.nextLine());
        scanner.close();
    }
}
