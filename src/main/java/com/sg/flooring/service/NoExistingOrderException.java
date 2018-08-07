/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.service;

/**
 *
 * @author betzler
 */
public class NoExistingOrderException extends Exception {

    public NoExistingOrderException(String message) {
        super(message);
    }

    public NoExistingOrderException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
