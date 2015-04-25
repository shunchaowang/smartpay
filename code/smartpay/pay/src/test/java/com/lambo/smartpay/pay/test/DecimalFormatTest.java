package com.lambo.smartpay.pay.test;

import java.text.DecimalFormat;

/**
 * Created by swang on 4/25/2015.
 */
public class DecimalFormatTest {

    public static void main(String[] args) {
        String amount = "12567.34";
        System.out.println("Original: " + amount);
        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        amount = decimalFormat.format(Float.parseFloat(amount));
        System.out.println("Formatted: " + amount);
        amount = "12567.00";
        System.out.println("Original: " + amount);
        amount = decimalFormat.format(Float.parseFloat(amount));
        System.out.println("Formatted: " + amount);
    }
}
