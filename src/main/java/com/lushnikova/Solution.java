package com.lushnikova;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Solution {
    public static void main(String[] args) {
        String time = "2024-10-11";
        LocalDate date = LocalDate.parse(time);
        LocalDateTime now = LocalDateTime.now();

        LocalDate localDate = now.toLocalDate();


        System.out.println(date);
        System.out.println(time.equals(localDate.toString()));
    }
}
