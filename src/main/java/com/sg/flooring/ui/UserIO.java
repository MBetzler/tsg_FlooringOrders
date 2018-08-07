/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author betzler
 */
public interface UserIO {

    public void print(String message);

    public void printLine(String message);

    public double readDouble(String prompt, boolean nextLine);

    public double readDouble(String prompt, double min, double max, boolean nextLine);

    public float readFloat(String prompt, boolean nextLine);

    public float readFloat(String prompt, float min, float max, boolean nextLine);

    public BigDecimal readBigDecimal(String prompt, boolean allowBlank, boolean nextLine) throws UserIOException;

    public int readInt(String prompt, boolean nextLine) throws UserIOException;

    public int readInt(String prompt, int min, int max, boolean nextLine) throws UserIOException;

    public int readInt(String prompt, int min, int max, boolean allowBlank, boolean nextLine) throws UserIOException;

    public int readInt(String prompt, Set<Integer> numbers, boolean nextLine) throws UserIOException;

    public long readLong(String prompt, boolean nextLine);

    public long readLong(String prompt, long min, long max, boolean nextLine);

    public String readString(String prompt, boolean nextLine);

    public String readString(String prompt, boolean allowBlank, boolean nextLine);

    public String readString(String prompt, String[] options, boolean nextLine);

    public char readChar(String prompt, Set<Character> options, boolean nextLine);

    public LocalDate readDate(String prompt, boolean allowBlank, boolean nextLine) throws UserIOException;
}
