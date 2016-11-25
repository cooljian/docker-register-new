package com.dock.test;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by gaojian on 2016/11/25.
 */
public class test {
    public static void main(String[] args) {
        String i = "000010000";
        System.out.print(i.contains("1"));
//        String s = "111111111111000000000";
//        transferTime(s);
    }

    public static List<String> transferTime(String s){
        String s0 = "";
        if(s.length() < 32){
            for(int i = 0 ; i < 32 - s.length(); i++){
                s0 +="0";
            }
        }
        s = s0 + s;

        System.out.println(s);

        List<String> splitStr = Lists.newArrayList();
        for(int i = 0; i < s.length(); i += 4){
            int j= i+4;
            splitStr.add(s.substring(i, j));
        }

        System.out.println(splitStr);
        return splitStr;
    }
}
