/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author betzler
 */
public class Product {

    private final String type;
    private final BigDecimal costSqFt;
    private final BigDecimal laborCostSqFt;

    public Product(String type, BigDecimal costSqFt, BigDecimal laborCostSqFt) {
        this.type = type;
        this.costSqFt = costSqFt.setScale(2);
        this.laborCostSqFt = laborCostSqFt.setScale(2);
    }

    public Product(Product product) {
        this.type = product.getType();
        this.costSqFt = product.getCostSqFt();
        this.laborCostSqFt = product.getLaborCostSqFt();
    }

    public String getType() {
        return type;
    }

    public BigDecimal getCostSqFt() {
        return costSqFt;
    }

    public BigDecimal getLaborCostSqFt() {
        return laborCostSqFt;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.costSqFt);
        hash = 29 * hash + Objects.hashCode(this.laborCostSqFt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.costSqFt, other.costSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostSqFt, other.laborCostSqFt)) {
            return false;
        }
        return true;
    }
}
