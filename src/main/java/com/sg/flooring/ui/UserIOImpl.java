/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author betzler
 */
public class UserIOImpl implements UserIO {

    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void printLine(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt, boolean nextLine) {

        if (nextLine) {
            printLine(prompt);
        } else {
            print(prompt);
        }
        return (Double.parseDouble(sc.nextLine()));
    }

    @Override
    public double readDouble(String prompt, double min, double max, boolean nextLine) {
        boolean validResponse = false;
        double response = 0;

        do {
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }
            response = Double.parseDouble(sc.nextLine());

            if (response >= min && response <= max) {
                validResponse = true;
            } else {
                System.out.println("That value does not fall within the range of " + min + " to " + max + ".\n");
            }

        } while (!validResponse);

        return response;
    }

    @Override
    public float readFloat(String prompt, boolean nextLine) {

        if (nextLine) {
            printLine(prompt);
        } else {
            print(prompt);
        }

        return (Float.parseFloat(sc.nextLine()));
    }

    @Override
    public float readFloat(String prompt, float min, float max, boolean nextLine) {
        boolean validResponse = false;
        float response = 0;

        do {
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }
            response = Float.parseFloat(sc.nextLine());

            if (response >= min && response <= max) {
                validResponse = true;
            } else {
                System.out.println("That value does not fall within the range of " + min + " to " + max + ".\n");
            }

        } while (!validResponse);

        return response;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, boolean allowBlank, boolean nextLine) throws UserIOException {
        boolean validResponse = false;
        BigDecimal response = null;
        String temp;

        do {

            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }

            temp = sc.nextLine().trim();

            if (allowBlank && temp.length() == 0) {
                validResponse = true;
            } else {
                try {
                    response = new BigDecimal(temp);
                    validResponse = true;
                } catch (NumberFormatException e) {
                    throw new UserIOException("\nERROR: invalid input; numeric entry only.\n", e);
                }
            }

        } while (!validResponse);
        return response;
    }

    @Override
    public int readInt(String prompt, boolean nextLine) throws UserIOException {
        int response = 0;

        if (nextLine) {
            printLine(prompt);
        } else {
            print(prompt);
        }

        try {
            response = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            throw new UserIOException("\nERROR: invalid input; numeric entry only.\n", e);
        }

        return response;
    }

    @Override
    public int readInt(String prompt, int min, int max, boolean nextLine) throws UserIOException {
        boolean validResponse = false;
        int response = 0;

        do {
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }

            try {
                response = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                throw new UserIOException("\nERROR: invalid input; numeric entry only.\n", e);
            }

            if (response >= min && response <= max) {
                validResponse = true;
            } else {
                System.out.println("\nThat value does not fall within the range of " + min + " to " + max + ".\n");
            }

        } while (!validResponse);

        return response;
    }

    @Override
    public int readInt(String prompt, int min, int max, boolean allowBlank, boolean nextLine) throws UserIOException {
        boolean validResponse = false;
        int response = 0;
        String temp;

        do {
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }

            temp = sc.nextLine().trim();

            if (allowBlank && temp.length() == 0) {
                validResponse = true;
            } else {

                try {
                    response = Integer.parseInt(temp);
                } catch (NumberFormatException e) {
                    throw new UserIOException("\nERROR: invalid input; numeric entry only.\n", e);
                }

                if (response >= min && response <= max) {
                    validResponse = true;
                } else {
                    System.out.println("\nThat value does not fall within the range of " + min + " to " + max + ".\n");
                }
            }
        } while (!validResponse);

        return response;
    }

    @Override
    public int readInt(String prompt, Set<Integer> numbers, boolean nextLine) throws UserIOException {
        boolean validResponse = false;
        int response = 0;

        do {
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }

            try {
                response = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                throw new UserIOException("\nERROR: invalid input; numeric entry only.", e);
            }

            if (numbers.contains(response)) {
                validResponse = true;
            } else {
                System.out.println("\nThat value does not exist in the displayed list.");
            }

        } while (!validResponse);

        return response;
    }

    @Override
    public long readLong(String prompt, boolean nextLine) {

        if (nextLine) {
            printLine(prompt);
        } else {
            print(prompt);
        }
        return (Long.parseLong(sc.nextLine()));
    }

    @Override
    public long readLong(String prompt, long min, long max, boolean nextLine) {
        boolean validResponse = false;
        long response = 0;

        do {
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }
            response = Long.parseLong(sc.nextLine());

            if (response >= min && response <= max) {
                validResponse = true;
            } else {
                System.out.println("That value does not fall within the range of " + min + " to " + max + ".\n");
            }

        } while (!validResponse);

        return response;
    }

    @Override
    public String readString(String prompt, boolean nextLine) {

        if (nextLine) {
            printLine(prompt);
        } else {
            print(prompt);
        }

        return (sc.nextLine());
    }

    @Override
    public String readString(String prompt, boolean allowBlank, boolean nextLine) {
        boolean validResponse;
        String response = "";

        do {
            validResponse = false;
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }

            response = sc.nextLine().trim();

            if (allowBlank) {
                validResponse = true;
            } else {
                if (!response.equals("")) {
                    validResponse = true;
                } else {
                    System.out.println("\nNothing was entered.  A value must be entered.\n");
                }
            }

        } while (!validResponse);

        return response;
    }

    @Override
    public String readString(String prompt, String[] options, boolean nextLine) {
        boolean validResponse;
        String response = "";

        do {
            validResponse = false;
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }

            response = sc.nextLine();

            for (int i = 0; i < options.length; i++) {
                if (response.equalsIgnoreCase(String.valueOf(options[i].charAt(i)))) {
                    validResponse = true;
                    break;
                }
            }

            if (!validResponse) {
                System.out.print("\nThat isn't a valid response.  Please respond with ");
                for (int i = 0; i < options.length; i++) {
                    if (i < options.length - 1) {
                        System.out.print("\"" + options[i].charAt(i) + "\", ");
                    } else {
                        System.out.println("\"" + options[i].charAt(i) + "\".\n");
                    }
                }
            }

        } while (!validResponse);

        return response;
    }

    @Override
    public char readChar(String prompt, Set<Character> options, boolean nextLine) {
        boolean validResponse;
        String temp;
        char response = 'X';

        do {
            validResponse = false;
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }

            temp = sc.nextLine().trim();

            if (temp.length() == 1) {
                response = temp.toUpperCase().charAt(0);
                if (options.contains(response) || options.contains(Character.toLowerCase(response))) {
                    validResponse = true;
                }
            }

            if (!validResponse) {
                System.out.print("\nThat isn't a valid response.  Please respond with ");
                Iterator<Character> iter = options.iterator();
                while (iter.hasNext()) {
                    char nextChar = Character.toUpperCase(iter.next());

                    if (iter.hasNext()) {
                        System.out.print("\"" + nextChar + "\", ");
                    } else {
                        System.out.println("\"" + nextChar + "\".");
                    }
                }
            }

        } while (!validResponse);

        return response;
    }

    @Override
    public LocalDate readDate(String prompt, boolean allowBlank, boolean nextLine) throws UserIOException {
        boolean validResponse = false;
        LocalDate response = null;
        String temp;

        do {
            if (nextLine) {
                printLine(prompt);
            } else {
                print(prompt);
            }

            temp = sc.nextLine().trim();

            if (allowBlank && temp.length() == 0) {
                validResponse = true;
            } else {
                try {
                    response = LocalDate.parse(temp, DateTimeFormatter.ofPattern("M/d/yyyy"));
                    validResponse = true;
                } catch (DateTimeParseException e) {
                    throw new UserIOException("\nERROR: invalid input; enter a date as m/d/yyyy.\n", e);
                }
            }

        } while (!validResponse);
        return response;
    }
}
