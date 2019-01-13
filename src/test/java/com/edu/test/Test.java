package com.edu.test;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/13 12:01
 */
public class Test {
    public static void main(String[] args) {
        String str = "D:/studyCodes/idea/OnlineMusic/target/com.edu/resources/upload/07718b10-d234-4c8c-8137-348b5639db41.mp3";
        int index = str.indexOf("/resources");
        String substring = str.substring(index);
        System.out.println(substring);
        System.out.println(index);
    }
}
