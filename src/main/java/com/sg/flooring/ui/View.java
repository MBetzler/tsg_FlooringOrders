/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.ui;

import com.sg.flooring.dto.Order;
import com.sg.flooring.dto.Product;
import com.sg.flooring.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author betzler
 */
public class View {

    private UserIO io;
    private static final String ADD = "add";
    private static final String EDIT = "edit";

    public View(UserIO io) {
        this.io = io;
    }

    public int displayMainMenuGetSelection() throws UserIOException {
        io.printLine("\n*****************************************"
                + "\n*****         Flooring-R-Us         *****"
                + "\n*****************************************"
                + "\n1. Display Orders"
                + "\n2. Add an Order"
                + "\n3. Edit an Order"
                + "\n4. Cancel an Order"
                + "\n5. Save Current Work to File"
                + "\n6. Exit\n");

        return getRangedNumericResponse("Please select an option:   ", 1, 6, false);
    }

    private int getRangedNumericResponse(String prompt, int min, int max, boolean nextLine) throws UserIOException {
        int response = 0;
        boolean hasErrors = false;

        do {
            try {
                response = io.readInt(prompt, min, max, nextLine);
                hasErrors = false;
            } catch (UserIOException e) {
                hasErrors = true;
                displayErrorMessage(e.getMessage());
            }

        } while (hasErrors);

        return response;
    }

    public void displayErrorMessageBanner() {
        io.printLine("\n=== ERROR ===");
    }

    public void displayErrorMessage(String errorMsg) {
        io.printLine(errorMsg);
    }

    public int displayBannerGetSelectPreference() throws UserIOException {
        io.printLine("\n*****************************************"
                + "\n*****    Order Select Preference    *****"
                + "\n*****************************************"
                + "\n1. Select Order by Order Number"
                + "\n2. Select Order by Order Date\n");

        return getRangedNumericResponse("Please select an option:   ", 1, 2, false);
    }

    public int displayBannerGetOrderNumber() throws UserIOException {
        int orderNumber = 0;
        boolean hasErrors = false;

        io.printLine("\n*****************************************"
                + "\n*****      Enter Order Number       *****"
                + "\n*****************************************\n");

        do {
            try {
                orderNumber = io.readInt("Please enter the Order Number:   ", false);
                hasErrors = false;
            } catch (UserIOException e) {
                hasErrors = true;
                displayErrorMessage(e.getMessage());
            }

        } while (hasErrors);

        return orderNumber;
    }

    public void displayPauseForContinue() {
        io.readString("\nPress enter to continue.", true);
    }

    public void displayOrderDetails(Order order) {
        String costSqFt = "$" + order.getProduct().getCostSqFt().toString();
        String laborCostSqFt = "$" + order.getProduct().getLaborCostSqFt().toString();
        String cost = "$" + order.getMaterialCost().toString();
        String laborCost = "$" + order.getLaborCost().toString();
        String taxRate = order.getTax().getRate().toString() + "%";
        String taxCost = "$" + order.getTaxCost().toString();
        String total = "$" + order.getTotal().toString();

        io.printLine("\n=============================================================================="
                + "\n|                     Order Details                                          |"
                + "\n==============================================================================");

        System.out.format("| Order: %-24d | Customer: %-30s |%n", order.getOrderNumber(), order.getCustomer());
        io.printLine("==============================================================================");
        System.out.format("| Flooring Type: %16s | Area: %34s |%n", order.getProduct().getType(), order.getArea().toString());
        System.out.format("| Cost/sq ft: %19s | Labor Cost/sq ft: %22s |%n", costSqFt, laborCostSqFt);
        System.out.format("| Material Cost: %16s | Labor Cost: %28s |%n", cost, laborCost);
        System.out.format("| State: %24s | Tax Rate: %30s |%n", order.getTax().getState(), taxRate);
        io.printLine("==============================================================================");
        System.out.format("| %60s%14s |%n", "Tax:", taxCost);
        System.out.format("| %60s%14s |%n", "Total:", total);
        io.printLine("==============================================================================");

        io.readString("\nPress enter to continue.", true);
    }

    public void displayGetOrderDateBanner() {
        io.printLine("\n*****************************************"
                + "\n*****       Enter Order Date        *****"
                + "\n*****************************************\n");
    }

    public LocalDate getOrderDate() throws UserIOException {
        LocalDate orderDate = LocalDate.now();
        boolean hasErrors = false;

        do {
            try {
                orderDate = io.readDate("Please enter the Order Date as m/d/yyyy:   ", false, false);
                hasErrors = false;
            } catch (UserIOException e) {
                hasErrors = true;
                displayErrorMessage(e.getMessage());
            }

        } while (hasErrors);

        return orderDate;
    }

    public void displayOrderSummariesPerDate(List<Order> orderList, Set<Integer> orderNumbers) {
        int orderNumber;
        String customer;
        String total;

        io.printLine("\n==========================================================\n"
                + "| Order # |    Customer                    |    Total    |\n"
                + "==========================================================");
        for (Order order : orderList) {
            orderNumber = order.getOrderNumber();
            customer = order.getCustomer();
            total = "$" + order.getTotal().toString();
            System.out.format("|%8d | %-30s | %11s |%n", orderNumber, customer, total);
            orderNumbers.add(order.getOrderNumber());
        }
        io.printLine("==========================================================\n");
    }

    public int getOrderNumber(Set<Integer> numbers) throws UserIOException {
        int orderNumber = 0;
        boolean hasErrors = false;

        do {
            try {
                orderNumber = io.readInt("\nPlease enter the Order Number:   ", numbers, false);
                hasErrors = false;
            } catch (UserIOException e) {
                hasErrors = true;
                displayErrorMessage(e.getMessage());
            }

        } while (hasErrors);

        return orderNumber;
    }

    public char getRepeatSubmenu(String action) {
        Character[] charArray = {'Y', 'N'};
        Set<Character> options = new HashSet<>();
        Collections.addAll(options, charArray);

        return io.readChar("\nWould you like to " + action + " another Order (Y/N)?   ", options, false);
    }

    public void displayAddOrderBanner() {
        io.printLine("\n*****************************************"
                + "\n*****           Add Order           *****"
                + "\n*****************************************");
    }

    public void getOrderDate(Order order) throws UserIOException {
        char dateToday = 'N';
        Character[] charArray = {'Y', 'N'};
        Set<Character> options = new HashSet<>();
        Collections.addAll(options, charArray);
        LocalDate date = LocalDate.now();

        dateToday = io.readChar("\nWould you like to use today's date as the Order Date (Y/N)?   ", options, false);

        if (dateToday == 'Y') {
            order.setOrderDate(date);
        } else {
            do {
                io.printLine("-->Order dates may only be backdated up to one month from today.\n-->Future dates are not allowed.\n");
                date = getOrderDate();
            } while (date.isAfter(LocalDate.now()) || date.plusMonths(1).isBefore(LocalDate.now()));
            order.setOrderDate(date);
        }
    }

    public void getCustomerName(Order order, String action) {
        boolean allowBlank = false;
        String insertedText = "";
        String temp;

        if (action.equals(EDIT)) {
            allowBlank = true;
            insertedText = " (Current: " + order.getCustomer() + ")";
        }

        temp = io.readString("Please enter the Customer Name" + insertedText + ":   ", allowBlank, false);

        if (action.equals(ADD)) {
            order.setCustomer(temp);
        } else if (action.equals(EDIT)) {
            if (temp.length() != 0) {
                order.setCustomer(temp);
            }
        }
    }

    public int getTax(Order order, List<Tax> taxList, String action) throws UserIOException {
        boolean allowBlank = false;
        String insertedText = "";
        boolean hasErrors = false;
        int selection = 0;
        String state;
        BigDecimal taxRate;
        int index;

        if (action.equals(EDIT)) {
            allowBlank = true;
            insertedText = " (Current: " + order.getTax().getState() + ")";
        }

        io.printLine("\n==============================\n" + "| Item # | State  | Tax Rate |\n" + "==============================");
        for (int i = 0; i < taxList.size(); i++) {
            index = i + 1;
            state = taxList.get(i).getState();
            taxRate = taxList.get(i).getRate();
            System.out.format("|%5d   |%5s   |%7.2f%%  |%n", index, state, taxRate);
        }
        io.printLine("==============================\n");

        do {
            try {
                selection = io.readInt("Please select the Tax (1 - " + taxList.size() + ")" + insertedText
                        + ":   ", 1, taxList.size(), allowBlank, false) - 1;
                hasErrors = false;
            } catch (UserIOException e) {
                hasErrors = true;
                displayErrorMessage(e.getMessage());
            }

        } while (hasErrors);

        return selection;
    }

    public int getProduct(Order order, List<Product> productList, String action) throws UserIOException {
        boolean allowBlank = false;
        String insertedText = "";
        boolean hasErrors = false;
        int selection = 0;
        int index;
        String type;
        String cost;
        String laborCost;

        if (action.equals(EDIT)) {
            allowBlank = true;
            insertedText = " (Current: " + order.getProduct().getType() + ")";
        }

        io.printLine("\n==============================================================\n"
                + "| Item # |  Flooring Type  | Cost/SQ FT |  Labor Cost/SQ FT  |\n"
                + "==============================================================");
        for (int i = 0; i < productList.size(); i++) {
            index = i + 1;
            type = productList.get(i).getType();
            cost = "$" + productList.get(i).getCostSqFt().toString();
            laborCost = "$" + productList.get(i).getLaborCostSqFt().toString();
            System.out.format("|%5d   |%16s |%10s  |%19s |%n", index, type, cost, laborCost);
        }
        io.printLine("==============================================================\n");

        do {
            try {
                selection = io.readInt("Please select the Product (1 - " + productList.size() + ")" + insertedText
                        + ":   ", 1, productList.size(), allowBlank, false) - 1;
                hasErrors = false;
            } catch (UserIOException e) {
                hasErrors = true;
                displayErrorMessage(e.getMessage());
            }

        } while (hasErrors);

        return selection;
    }

    public void getArea(Order order, String action) throws UserIOException {
        boolean allowBlank = false;
        String insertedText = "";
        boolean hasErrors = false;
        BigDecimal temp = null;

        if (action.equals(EDIT)) {
            allowBlank = true;
            insertedText = " (Current: " + order.getArea() + ")";
        }

        do {

            try {
                temp = io.readBigDecimal("Please enter the sq ft of flooring Area" + insertedText + ":   ", allowBlank, false);
                hasErrors = false;
            } catch (UserIOException e) {
                hasErrors = true;
                displayErrorMessage(e.getMessage());
            }

        } while (hasErrors);

        if (action.equals(ADD)) {
            order.setArea(temp);
        } else if (action.equals(EDIT)) {
            if (temp != null) {
                order.setArea(temp);
            }
        }
    }

    public boolean getConfirmation(String action) {
        Character[] charArray = {'Y', 'N'};
        Set<Character> options = new HashSet<>();
        Collections.addAll(options, charArray);
        boolean save = false;

        if (io.readChar("\nWould you like to " + action + " the Order (Y/N)?   ", options, false) == 'Y') {
            save = true;
        }

        return save;
    }

    public void displayEditOrderBanner() {
        io.printLine("\n*****************************************"
                + "\n*****           Edit Order          *****"
                + "\n*****************************************\n");
    }

    public void displayResult(String writeResult) {
        io.readString(writeResult + "  Press enter to continue.", true);
    }

    public void displayThankYouBanner() {
        io.printLine("\n==============================================");
        io.printLine("=    Always strive to floor the customer.    =");
        io.printLine("==============================================");
    }
}
