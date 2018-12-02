package com.alienvault.service;

import java.util.Scanner;

public class InputService {

    public InputService(){

    }

    public static String[] getArgs(){
        String[] args;
        String input;
        try {
            input = inputArgsFromCmdLine();
            args = input.split(",");
            return args;
        } catch (Exception e){
            System.out.println("Invalid input.");
            getArgs();
            return null;
        }
    }

    public static String inputArgsFromCmdLine(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input missing.  Enter now:");
        return scanner.next();
    }
}
