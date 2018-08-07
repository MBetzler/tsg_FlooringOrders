/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.controller;

import com.sg.flooring.dao.DataPersistenceException;
import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.Tax;
import com.sg.flooring.service.NoExistingOrderException;
import com.sg.flooring.service.ServiceLayer;
import com.sg.flooring.ui.UserIOException;
import com.sg.flooring.ui.View;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author betzler
 */
public class Controller {

    private View view;
    private ServiceLayer service;
    private static final String ADD = "add";
    private static final String EDIT = "edit";
    private static final String VIEW = "view";
    private static final String SAVE = "save";
    private static final String CANCEL = "cancel";

    public Controller(ServiceLayer service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        int mainMenuSelection;
        int subMenuSelection;
        char repeat;

        try {
            service.loadAllData();

            do {
                mainMenuSelection = getMainMenuSelection();
                switch (mainMenuSelection) {
                    case 1:
                        do {
                            subMenuSelection = getSelectPreference();
                            switch (subMenuSelection) {
                                case 1:
                                    try {
                                        selectDisplayByOrderNumber();
                                    } catch (NoExistingOrderException e) {
                                        notifyErrorWithoutBanner(e.getMessage());
                                        pauseForContinue();
                                    }
                                    break;
                                case 2:
                                    try {
                                        selectDisplayByOrderDate();
                                    } catch (NoExistingOrderException e) {
                                        notifyErrorWithoutBanner(e.getMessage());
                                        pauseForContinue();
                                    }
                                    break;
                            }
                            repeat = getRepeatSubmenu(VIEW);
                        } while (repeat == 'Y');
                        break;
                    case 2:
                        do {
                            addOrder();
                            repeat = getRepeatSubmenu(ADD);
                        } while (repeat == 'Y');
                        break;
                    case 3:
                        do {
                            subMenuSelection = getSelectPreference();
                            switch (subMenuSelection) {
                                case 1:
                                    try {
                                        selectEditByOrderNumber();
                                    } catch (NoExistingOrderException e) {
                                        notifyErrorWithoutBanner(e.getMessage());
                                        pauseForContinue();
                                    }
                                    break;
                                case 2:
                                    try {
                                        selectEditByOrderDate();
                                    } catch (NoExistingOrderException e) {
                                        notifyErrorWithoutBanner(e.getMessage());
                                        pauseForContinue();
                                    }
                                    break;
                            }
                            repeat = getRepeatSubmenu(EDIT);
                        } while (repeat == 'Y');
                        break;
                    case 4:
                        do {
                            subMenuSelection = getSelectPreference();
                            switch (subMenuSelection) {
                                case 1:
                                    try {
                                        selectCancelByOrderNumber();
                                    } catch (NoExistingOrderException e) {
                                        notifyErrorWithoutBanner(e.getMessage());
                                        pauseForContinue();
                                    }
                                    break;
                                case 2:
                                    try {
                                        selectCancelByOrderDate();
                                    } catch (NoExistingOrderException e) {
                                        notifyErrorWithoutBanner(e.getMessage());
                                        pauseForContinue();
                                    }
                                    break;
                            }
                            repeat = getRepeatSubmenu(CANCEL);
                        } while (repeat == 'Y');
                        break;
                    case 5:
                        saveOrderData();
                        break;
                    case 6:
                        break;

                }

            } while (mainMenuSelection != 6);

            service.writeOrderData();
            exitingMessage();

        } catch (DataPersistenceException | UserIOException e) {
            notifyErrorWithBanner(e.getMessage());
        }

    }

    private int getMainMenuSelection() throws UserIOException {
        return view.displayMainMenuGetSelection();
    }

    private void notifyErrorWithBanner(String errorMessage) {
        view.displayErrorMessageBanner();
        view.displayErrorMessage(errorMessage);
    }

    private int getSelectPreference() throws UserIOException {
        return view.displayBannerGetSelectPreference();
    }

    private void selectDisplayByOrderNumber() throws UserIOException, NoExistingOrderException {
        int orderNumber = view.displayBannerGetOrderNumber();
        Order order = service.getActiveOrderByNumber(orderNumber);
        view.displayOrderDetails(order);
    }

    private void selectDisplayByOrderDate() throws UserIOException, NoExistingOrderException {
        view.displayGetOrderDateBanner();
        LocalDate orderDate = view.getOrderDate();
        List<Order> orderList = service.getActiveOrdersPerDate(orderDate);
        Set<Integer> orderNumbers = new HashSet<>(orderList.size());
        view.displayOrderSummariesPerDate(orderList, orderNumbers);
        int orderNumber = view.getOrderNumber(orderNumbers);
        Order order = service.getActiveOrderByNumber(orderNumber);
        view.displayOrderDetails(order);
    }

    private void pauseForContinue() {
        view.displayPauseForContinue();
    }

    private void notifyErrorWithoutBanner(String errorMessage) {
        view.displayErrorMessage(errorMessage);
    }

    private char getRepeatSubmenu(String action) {
        return view.getRepeatSubmenu(action);
    }

    private void addOrder() throws UserIOException {
        List<Product> productList = service.getProducts();
        List<Tax> taxList = service.getTaxes();
        Order order = new Order();
        view.displayAddOrderBanner();
        view.getOrderDate(order);
        view.getCustomerName(order, ADD);
        int tax = view.getTax(order, taxList, ADD);
        order.setTax(service.getTaxByIndex(tax));
        int product = view.getProduct(order, productList, ADD);
        order.setProduct(service.getProductByIndex(product));
        view.getArea(order, ADD);
        order.setOrderNumber(service.getNextOrderNumber());
        service.calculateTotalCost(order);
        view.displayOrderDetails(order);
        view.displayResult(service.addOrder(order, view.getConfirmation(SAVE)));
    }

    private void selectEditByOrderNumber() throws UserIOException, NoExistingOrderException {
        int orderNumber = view.displayBannerGetOrderNumber();
        Order order = service.getActiveOrderByNumber(orderNumber);
        Order editedOrder = new Order(order);
        editOrder(order, editedOrder);
    }

    private void selectEditByOrderDate() throws UserIOException, NoExistingOrderException {
        view.displayGetOrderDateBanner();
        LocalDate orderDate = view.getOrderDate();
        List<Order> orderList = service.getActiveOrdersPerDate(orderDate);
        Set<Integer> orderNumbers = new HashSet<>(orderList.size());
        view.displayOrderSummariesPerDate(orderList, orderNumbers);
        int orderNumber = view.getOrderNumber(orderNumbers);
        Order order = service.getActiveOrderByNumber(orderNumber);
        Order editedOrder = new Order(order);
        editOrder(order, editedOrder);
    }

    private void editOrder(Order order, Order editedOrder) throws UserIOException {
        List<Product> productList = service.getProducts();
        List<Tax> taxList = service.getTaxes();
        view.displayEditOrderBanner();
        view.getCustomerName(editedOrder, EDIT);
        int tax = view.getTax(editedOrder, taxList, EDIT);
        service.setEditedTax(tax, editedOrder);
        int product = view.getProduct(editedOrder, productList, EDIT);
        service.setEditedProduct(product, editedOrder);
        view.getArea(editedOrder, EDIT);
        service.calculateTotalCost(editedOrder);
        view.displayOrderDetails(editedOrder);
        view.displayResult(service.updateOrder(order, editedOrder, view.getConfirmation(SAVE)));
    }

    private void selectCancelByOrderNumber() throws UserIOException, NoExistingOrderException {
        int orderNumber = view.displayBannerGetOrderNumber();
        Order cancelledOrder = service.getActiveOrderByNumber(orderNumber);
        cancelOrder(cancelledOrder);
    }

    private void selectCancelByOrderDate() throws UserIOException, NoExistingOrderException {
        view.displayGetOrderDateBanner();
        LocalDate orderDate = view.getOrderDate();
        List<Order> orderList = service.getActiveOrdersPerDate(orderDate);
        Set<Integer> orderNumbers = new HashSet<>(orderList.size());
        view.displayOrderSummariesPerDate(orderList, orderNumbers);
        int orderNumber = view.getOrderNumber(orderNumbers);
        Order cancelledOrder = service.getActiveOrderByNumber(orderNumber);
        cancelOrder(cancelledOrder);
    }

    private void cancelOrder(Order cancelledOrder) {
        view.displayOrderDetails(cancelledOrder);
        view.displayResult(service.cancelOrder(cancelledOrder, view.getConfirmation(CANCEL)));
    }

    private void saveOrderData() throws DataPersistenceException {
        view.displayResult(service.writeOrderData());

    }

    private void exitingMessage() {
        view.displayThankYouBanner();
    }
}
