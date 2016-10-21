package com.dock.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtils
{
    public static final String NUMBER_PATTERN_1 = "####.0000";
    public static final String NUMBER_PATTERN_2 = "####.00";

    public static double mathDouble(double finalDouble, int num)
    {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(num);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);

        return parseDouble(formater.format(finalDouble));
    }

    public static double roundDouble(double finalDouble, int num) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(num);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.HALF_UP);

        return parseDouble(formater.format(finalDouble));
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.add(b2).doubleValue();
    }

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();
    }

    public static double multi(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2).doubleValue();
    }

    public static double div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.divide(b2, 10, 4).doubleValue();
    }

    public static int parseInt(String s) {
        int value;
        try {
            value = Integer.parseInt(s);
        } catch (Exception e) {
            value = 0;
        }

        return value;
    }

    public static long parseLong(String s) {
        long value;
        try {
            value = Long.parseLong(s);
        } catch (Exception e) {
            value = 0L;
        }

        return value;
    }

    public static float parseFloat(String s) {
        float value;
        try {
            value = Float.parseFloat(s);
        } catch (Exception e) {
            value = 0.0F;
        }

        return value;
    }

    public static double parseDouble(String s) {
        double value;
        try {
            value = Double.parseDouble(s);
        } catch (Exception e) {
            value = 0.0D;
        }

        return value;
    }
}