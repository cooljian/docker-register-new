package com.dock.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {
    private static final Logger logger = LoggerFactory.getLogger(NumberUtils.class);
    public static final String NUMBER_PATTERN_1 = "####.0000";
    public static final String NUMBER_PATTERN_2 = "####.00";

    public NumberUtils() {
    }

//    public static double mathDouble(double finalDouble, int num) {
//        DecimalFormat formater = new DecimalFormat();
//        formater.setMaximumFractionDigits(num);
//        formater.setGroupingSize(0);
//        formater.setRoundingMode(RoundingMode.FLOOR);
//        return toDouble(formater.format(finalDouble));
//    }
//
//    public static double roundDouble(double finalDouble, int num) {
//        DecimalFormat formater = new DecimalFormat();
//        formater.setMaximumFractionDigits(num);
//        formater.setGroupingSize(0);
//        formater.setRoundingMode(RoundingMode.HALF_UP);
//        return toDouble(formater.format(finalDouble));
//    }

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

    public static int toInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException var2) {
            logger.error("invalid int type {}", s);
            return 0;
        }
    }

    public static int toInteger(String s, int defval) {
        return StringUtils.isEmpty(s)?defval:toInteger(s);
    }

    public static long toLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException var2) {
            logger.error("invalid long type {}", s);
            return 0L;
        }
    }

    public static float toFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException var2) {
            logger.error("invalid float type {}", s);
            logger.error(var2.getMessage(), var2);
            return 0.0F;
        }
    }

    public static double toDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException var2) {
            logger.error("invalid double type {}", s);
            logger.error(var2.getMessage(), var2);
            return 0.0D;
        }
    }

    public static String format(Object target, String format) {
        try {
            return (new DecimalFormat(format)).format(target);
        } catch (IllegalArgumentException var3) {
            logger.error("decimal format error, target {}, format {}", target, format);
            logger.error(var3.getMessage(), var3);
            return "";
        }
    }
}
