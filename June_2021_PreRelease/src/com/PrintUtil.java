package com;

import java.util.Scanner;

public class PrintUtil{
    
    private static Scanner scanner;
    
    static {
        scanner = new Scanner(System.in);
    }
    
    public static String[] promptList(String message, Object... args){
        String val = prompt(message, args);
        String[] arr = val.split(",");
        for(int i = 0; i < arr.length; i++){
            arr[i] = arr[i].trim();
        }
        return arr;
    }
    
    public static String[] promptList(String message, int max, Object... args){
        String val = prompt(message, args);
        String[] arr = val.split(",");
        if(arr.length>max){
            System.out.println("Please enter no more than " + max + " elements!");
            return promptList(message, max, args);
        }
        for(int i = 0; i < arr.length; i++){
            arr[i] = arr[i].trim();
        }
        return arr;
    }
    
    public static String prompt(String message, Object... args){
        return prompt(message, "", args);
    }
    
    public static int promptInt(String message, Object... args){
        return promptInt(message, true, args);
    }
    
    public static int promptInt(String message, boolean positive, Object... args){
        try{
            int i = Integer.parseInt(prompt(message, -1, args));
            if(i<0){
                System.out.println("Please enter a positive number");
                return promptInt(message, positive, args);
            }
            return i;
        }catch(NumberFormatException e){
            System.out.println("Please enter a valid number");
            return promptInt(message, positive, args);
        }
    }
    
    public static int promptInt(String message, int defaultValue, Object... args){
        try{
            return Integer.parseInt(prompt(message, defaultValue, args));
        }catch(NumberFormatException e){
            System.out.println("Please enter a valid number");
            return promptInt(message, defaultValue, args);
        }
    }
    
    public static String prompt(String message, String defaultOption, Object... args){
        System.out.print(String.format(message, args) + " " + (!defaultOption.isEmpty() ? "(" + defaultOption + ") " : ""));
        String s = scanner.nextLine();
        return s.isEmpty() ? defaultOption : s;
    }
    
    public static int option(String message, int defaultOption, String... options){
        StringBuilder optionsString = new StringBuilder();
        for(int i = 0; i < options.length; i++){
            if(i==defaultOption){
                optionsString.append(options[i].toUpperCase() + "[" + (i+1) + "]");
            }else{
                optionsString.append(options[i].toLowerCase() + "[" + (i+1) + "]");
            }
            if(i!=options.length-1){
                optionsString.append("/");
            }
        }
        System.out.print(message + " (" + optionsString + ") ");
        String s = scanner.nextLine();
        if(s.isEmpty()){
            return defaultOption;
        }
        for(int i = 0; i < options.length; i++){
            try{
                if(s.equalsIgnoreCase(options[i]) || Integer.parseInt(s) == i+1){
                    return i;
                }
            }catch(NumberFormatException e){
                //Ignored
            }
        }
        return -1;
    }
    
    public static void error(String message, Object... args){
        System.err.println(String.format(message, args));
        System.exit(1);
    }
    
    public static void info(String message, Object... args){
        System.out.println(String.format(message, args));
    }
    
    public static void blockUntilPress(String message, Object... args){
        info(message, args);
        scanner.nextLine();
    }
    
    public static void newLine(){
        newLine(1);
    }
    
    public static void newLine(int count){
        String str = "";
        for(int i = 0; i < count; i++){
            str+="\n";
        }
        System.out.print(str);
    }
}
