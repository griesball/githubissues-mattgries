package com.alienvault.service;

import java.util.HashMap;
import java.util.Scanner;

public class InputService {

    public InputService(){

    }

    //No args were passed when starting the app, run a cmd line prompt
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

    //User prompt for inputs in the owner/repo format
    private static String inputArgsFromCmdLine(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input missing.  Enter now:");
        return scanner.next();
    }
}
