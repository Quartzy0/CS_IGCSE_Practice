package com;

import java.util.Scanner;

public class Main{
    
    public static double doAddSub(String in){
        String[] addition = in.split("\\+");
    
        double result = 0;
        for(String s : addition){
            if(s.contains("-")){
                result+=doSub(s);
            }else{
                result+=Double.parseDouble(s);
            }
        }
        return result;
    }
    
    private static double doSub(String in){
        String[] sub = in.split("-");
    
        double result = Double.parseDouble(sub[0]);
        for(int i = 1; i < sub.length; i++){
            result-=Double.parseDouble(sub[i]);
        }
        
        return result;
    }
    
    public static double doMultDiv(String in){
        String[] div = in.split("/");
    
        double res = 0;
        for(int i = 0; i < div.length; i++){
            if(div[i].contains("*")){
                if(i==0){
                    res = doMult(div[i]);
                    continue;
                }
                res/=doMult(div[i]);
            }else if(div[i].contains("-") || div[i].contains("+")){
                res += doAddSub(div[i]);
            }else{
                if(i==0){
                    res = Double.parseDouble(div[i]);
                    continue;
                }
                res/=Double.parseDouble(div[i]);
            }
        }
        return res;
    }
    
    private static double doMult(String in){
        String[] div = in.split("\\*");
    
        double res = Double.parseDouble(div[0]);
        for(int i = 1; i < div.length; i++){
            if(div[i].contains("-") || div[i].contains("+")){
                res *= doAddSub(div[i]);
            }else{
                res *= Double.parseDouble(div[i]);
            }
        }
        return res;
    }
    
    public static String doCalculation(){
        return "";
    }
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(doMultDiv(input));
    }
}
