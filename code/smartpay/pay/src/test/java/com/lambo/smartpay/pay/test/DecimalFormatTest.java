package com.lambo.smartpay.pay.test;

import org.apache.commons.codec.binary.Base64;

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
        System.out.println(Base64.encodeBase64String("1111222233334444".getBytes()));
        byte[] bytes = Base64.decodeBase64(Base64.encodeBase64String("1111222233334444".getBytes()));
        for(int i = 0; i< bytes.length; i++)
            System.out.println(bytes[i]);
        System.out.println(Base64.decodeBase64(Base64.encodeBase64String("1111222233334444".getBytes())).toString());
    }
}
