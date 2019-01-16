package com.edu.test;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/16 16:23
 */
public class Test {
    public static void main(String[] args) {
        String song_url = "D:/sotfWare/Tomcat 9.0/webapps/ROOT/resources/upload/15cbd4ca-9856-46ff-8407-fffbb62acc1e.mp3";
        int index = song_url.indexOf("upload/");
        String substring = song_url.substring(0,index + 7);
        System.out.println(substring);

//        String[] split = song_url.split("\\.");
//        for (String str : split){
//            System.out.println(str);
//        }
    }
}
