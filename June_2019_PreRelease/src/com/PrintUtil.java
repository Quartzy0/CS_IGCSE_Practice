package com;

import java.util.Scanner;

public class PrintUtil{
    
    private static Scanner scanner;
    
    static {
        scanner = new Scanner(System.in);
    }
    
    public static String prompt(String message, Object... args){
        return prompt(message, "", args);
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
                optionsString.append(options[i].toUpperCase());
            }else{
                optionsString.append(options[i].toLowerCase());
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
            if(s.equalsIgnoreCase(options[i])){
                return i;
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
    
    public static void newLine(){
        newLines(1);
    }
    
    public static void newLines(int count){
        String str = "";
        for(int i = 0; i < count; i++){
            str+="\n";
        }
        System.out.print(str);
    }
}
