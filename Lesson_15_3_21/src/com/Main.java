package com;

/*
    B
    O
    D
    M
    A
    S
*/

import java.util.Scanner;

public class Main{
    
    public static double doCalculation(String in){
        if(in.contains(OperationType.SUBTRACTION.symbol)){
            String[] split = in.split(OperationType.SUBTRACTION.symbolRegex, 2);
            return doCalculation(new Operation(split[0], split[1], OperationType.SUBTRACTION));
        }
        if(in.contains(OperationType.ADDITION.symbol)){
            String[] split = in.split(OperationType.ADDITION.symbolRegex, 2);
            return doCalculation(new Operation(split[0], split[1], OperationType.ADDITION));
        }
        if(in.contains(OperationType.MULTIPLICATION.symbol)){
            String[] split = in.split(OperationType.MULTIPLICATION.symbolRegex, 2);
            return doCalculation(new Operation(split[0], split[1], OperationType.MULTIPLICATION));
        }
        if(in.contains(OperationType.DIVISION.symbol)){
            String[] split = in.split(OperationType.DIVISION.symbolRegex, 2);
            return doCalculation(new Operation(split[0], split[1], OperationType.DIVISION));
        }
        try{
            return Double.parseDouble(in);
        }catch(NumberFormatException e){}
        return 0;
    }
    
    public static double doCalculation(Operation operation){
        double resPart1 = 0;
        double resPart2 = 0;
    
        if(operation.hasType1(OperationType.ADDITION)){
            String[] division = operation.part1.split(OperationType.ADDITION.symbolRegex, 2);
            Operation operation1 = new Operation(division[0], division[1], OperationType.ADDITION);
            if(!operation1.completed()){
                resPart1 = doCalculation(operation1);
            }else{
                resPart1 = operation1.doOperation();
            }
        }else if(operation.hasType1(OperationType.SUBTRACTION)){
            String[] parts = operation.part1.split(OperationType.SUBTRACTION.symbolRegex, 2);
            Operation operation1 = new Operation(parts[0], parts[1], OperationType.SUBTRACTION);
            if(!operation1.completed()){
                resPart1 = doCalculation(operation1);
            }else{
                resPart1 = operation1.doOperation();
            }
        } else if(operation.hasType1(OperationType.MULTIPLICATION)){
            String[] parts = operation.part1.split(OperationType.MULTIPLICATION.symbolRegex, 2);
            Operation operation1 = new Operation(parts[0], parts[1], OperationType.MULTIPLICATION);
            if(!operation1.completed()){
                resPart1 = doCalculation(operation1);
            }else{
                resPart1 = operation1.doOperation();
            }
        }else if(operation.hasType1(OperationType.DIVISION)){
            String[] parts = operation.part1.split(OperationType.DIVISION.symbolRegex, 2);
            Operation operation1 = new Operation(parts[0], parts[1], OperationType.DIVISION);
            if(!operation1.completed()){
                resPart1 = doCalculation(operation1);
            }else{
                resPart1 = operation1.doOperation();
            }
        }else{
            resPart1 = Double.parseDouble(operation.part1);
        }
    
        if(operation.hasType2(OperationType.SUBTRACTION)){
            String[] division = operation.part2.split(OperationType.SUBTRACTION.symbolRegex, 2);
            Operation operation1 = new Operation(division[0], division[1], OperationType.SUBTRACTION);
            if(!operation1.completed()){
                resPart2 = doCalculation(operation1);
            }else{
                resPart2 = operation1.doOperation();
            }
        }else if(operation.hasType2(OperationType.ADDITION)){
            String[] parts = operation.part2.split(OperationType.ADDITION.symbolRegex, 2);
            Operation operation1 = new Operation(parts[0], parts[1], OperationType.ADDITION);
            if(!operation1.completed()){
                resPart2 = doCalculation(operation1);
            }else{
                resPart2 = operation1.doOperation();
            }
        } else if(operation.hasType2(OperationType.MULTIPLICATION)){
            String[] parts = operation.part2.split(OperationType.MULTIPLICATION.symbolRegex, 2);
            Operation operation1 = new Operation(parts[0], parts[1], OperationType.MULTIPLICATION);
            if(!operation1.completed()){
                resPart2 = doCalculation(operation1);
            }else{
                resPart2 = operation1.doOperation();
            }
        }else if(operation.hasType2(OperationType.DIVISION)){
            String[] parts = operation.part2.split(OperationType.DIVISION.symbolRegex, 2);
            Operation operation1 = new Operation(parts[0], parts[1], OperationType.DIVISION);
            if(!operation1.completed()){
                resPart2 = doCalculation(operation1);
            }else{
                resPart2 = operation1.doOperation();
            }
        }else{
            resPart2 = Double.parseDouble(operation.part2);
        }
    
        return Operation.doOperation(resPart1, resPart2, operation.type);
    }
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(doCalculation(input));
    }
    
    public static class Operation{
        public final String part1, part2;
        public final OperationType type;
        public final boolean completed1, completed2;
        
        public Operation(String part1, String part2, OperationType type){
            this.part1 = part1;
            this.part2 = part2;
            this.type = type;
            boolean comp1;
            boolean comp2;
            try{
                Double.parseDouble(part1);
                comp1 = true;
            }catch(NumberFormatException e){
                comp1 = false;
            }
            try{
                Double.parseDouble(part2);
                comp2 = true;
            }catch(NumberFormatException e){
                comp2 = false;
            }
            this.completed1 = comp1;
            this.completed2 = comp2;
        }
        
        public boolean hasType1(OperationType type){
            return !completed1 && part1.contains(type.symbol);
        }
    
        public boolean hasType2(OperationType type){
            return !completed2 && part2.contains(type.symbol);
        }
        
        public static double doOperation(double a, double b, OperationType type){
            switch(type){
                case DIVISION:
                    return a/b;
                case MULTIPLICATION:
                    return a*b;
                case ADDITION:
                    return a+b;
                case SUBTRACTION:
                    return a-b;
            }
            return 0;
        }
        
        public double doOperation(){
            if(!completed1 || !completed2)return 0;
            return doOperation(Double.parseDouble(part1), Double.parseDouble(part2), type);
        }
        
        public boolean completed(){
            return completed1 && completed2;
        }
    }
    
    public static enum OperationType{
        ADDITION("+", "\\+"), SUBTRACTION("-", "-"), MULTIPLICATION("*", "\\*"), DIVISION("/", "/");
        
        public final String symbol;
        public final String symbolRegex;
    
        OperationType(String symbol, String symbolRegex){
            this.symbol = symbol;
            this.symbolRegex = symbolRegex;
        }
    }
}
