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
public class Tax {

    private final String state;
    private final BigDecimal rate;

    public Tax(String state, BigDecimal tax) {
        this.state = state.toUpperCase();
        this.rate = tax.setScale(2);
    }

    public Tax(Tax tax) {
        this.state = tax.getState();
        this.rate = tax.getRate();
    }

    public String getState() {
        return state;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.state);
        hash = 19 * hash + Objects.hashCode(this.rate);
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
        final Tax other = (Tax) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        return true;
    }

}
