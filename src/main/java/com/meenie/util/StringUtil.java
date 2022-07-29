package com.meenie.util;

public class StringUtil {
    public static String subStringByLength(String originString,Integer length){
        return originString.length()>length?originString.substring(0,length):originString.substring(0);
    }
}
