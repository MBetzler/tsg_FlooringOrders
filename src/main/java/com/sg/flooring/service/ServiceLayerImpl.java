/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.service;

import com.sg.flooring.dao.DataPersistenceException;
import com.sg.flooring.dao.OrdersDao;
import com.sg.flooring.dao.ProductsDao;
import com.sg.flooring.dao.TaxesDao;
import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.Tax;
import static java.lang.Integer.compare;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author betzler
 */
public class ServiceLayerImpl implements ServiceLayer {

    OrdersDao ordersDao;
    ProductsDao productsDao;
    TaxesDao taxesDao;

    public ServiceLayerImpl(OrdersDao ordersDao, ProductsDao productsDao, TaxesDao taxesDao) {
        this.ordersDao = ordersDao;
        this.productsDao = productsDao;
        this.taxesDao = taxesDao;
    }

    @Override
    public void loadAllData() throws DataPersistenceException {
        ordersDao.loadEnvironmentVariable();
        productsDao.loadProductData();
        taxesDao.loadTaxData();
        ordersDao.loadOrderData();
    }

    @Override
    public List<Order> getActiveOrdersPerDate(LocalDate date) throws NoExistingOrderException {
        List<Order> activeOrders = ordersDao.getAllOrders().stream()
                .filter((d) -> d.getOrderDate().equals(date))
                .filter((c) -> !c.getCustomer().endsWith("(Cancelled)"))
                .sorted((n1, n2) -> compare(n1.getOrderNumber(), n2.getOrderNumber()))
                .collect(Collectors.toList());

        if (activeOrders.isEmpty()) {
            throw new NoExistingOrderException("\nThere are no active Orders with Order Date "
                    + date.format(DateTimeFormatter.ofPattern("M/d/yyyy")) + ".");
        }

        return activeOrders;
    }

    @Override
    public int getNextOrderNumber() {
        int orderNumber = ordersDao.getAllOrders().stream()
                .mapToInt(Order::getOrderNumber)
                .max()
                .getAsInt();

        return orderNumber + 1;
    }

    @Override
    public String writeOrderData() throws DataPersistenceException {
        String writeResult;
        boolean production;
        int allOrders = ordersDao.getAllOrders().size();

        production = ordersDao.writeOrderData();

        if (!production) {
            writeResult = "\nOrder data may not be saved to file in Training environment.";
        } else {
            if (allOrders > 0) {
                writeResult = "\nOrder data successfully saved to file.";
            } else {
                writeResult = "\nThere is no Order data to save.";
            }
        }
        return writeResult;
    }

    @Override
    public Order getActiveOrderByNumber(int orderNumber) throws NoExistingOrderException {
        Map<Integer, Order> activeOrders = ordersDao.getAllOrders().stream()
                .filter((c) -> !c.getCustomer().endsWith("(Cancelled)"))
                .collect(Collectors.toMap(Order::getOrderNumber, o -> o));

        Order order = activeOrders.get(orderNumber);

        if (order == null) {
            throw new NoExistingOrderException("\nThere is no active Order with Order Number " + orderNumber + ".");
        }

        return order;
    }

    @Override
    public String addOrder(Order order, boolean confirmation) {
        String result;

        if (confirmation) {
            ordersDao.addOrder(order);
            result = "\nOrder successfully saved.";
        } else {
            result = "\nSave cancelled.";
        }

        return result;
    }

    @Override
    public String updateOrder(Order order, Order editedOrder, boolean confirmation) {
        String result;

        if (confirmation) {
            ordersDao.updateOrder(editedOrder);
            result = "\nOrder successfully updated.";
        } else {
            result = "\nUpdate cancelled.";
        }

        return result;
    }

    @Override
    public String cancelOrder(Order cancelledOrder, boolean confirmation) {
        String result;

        if (confirmation) {
            cancelledOrder.setCustomer(cancelledOrder.getCustomer() + " (Cancelled)");
            ordersDao.updateOrder(cancelledOrder);
            result = "\nOrder successfully cancelled.";
        } else {
            result = "\nOrder is still active.";
        }

        return result;
    }

    @Override
    public List<Product> getProducts() {
        Collator productCollator = Collator.getInstance();
        return productsDao.getProducts().stream()
                .sorted((p1, p2) -> productCollator.compare(p1.getType(), p2.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductByIndex(int index) {
        return productsDao.getProductByIndex(index);
    }

    @Override
    public void setEditedProduct(int product, Order editedOrder) {
        if (product >= 0) {
            editedOrder.setProduct(getProductByIndex(product));
        }
    }

    @Override
    public List<Tax> getTaxes() {
        Collator taxCollator = Collator.getInstance();
        return taxesDao.getTaxes().stream()
                .sorted((t1, t2) -> taxCollator.compare(t1.getState(), t2.getState()))
                .collect(Collectors.toList());
    }

    @Override
    public Tax getTaxByIndex(int index) {
        return taxesDao.getTaxByIndex(index);
    }

    @Override
    public void setEditedTax(int tax, Order editedOrder) {
        if (tax >= 0) {
            editedOrder.setTax(getTaxByIndex(tax));
        }
    }

    @Override
    public void calculateTotalCost(Order order) {
        order.setMaterialCost(order.getProduct().getCostSqFt().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getProduct().getLaborCostSqFt().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setTaxCost(order.getTax().getRate().divide(new BigDecimal("100")).multiply(order.getMaterialCost().add(order.getLaborCost())).setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost().add(order.getTaxCost())));
    }
}
