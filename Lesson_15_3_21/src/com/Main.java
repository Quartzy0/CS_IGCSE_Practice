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
    
    public static String[] isolateParts(String part1, String part2){
        int indexPart1 = -1;
        int indexPart2 = -1;
        for(OperationType value : OperationType.values()){
            int newIndex1 = part1.lastIndexOf(value.symbol);
            int newIndex2 = part2.lastIndexOf(value.symbol);
            if(indexPart1==-1 || newIndex1>indexPart1){
                indexPart1 = newIndex1;
            }
            if(indexPart2==-1 || (newIndex2<indexPart2 && newIndex2!=-1))
                indexPart2 = part2.indexOf(value.symbol);
        }
        
        return new String[]{part1.substring(indexPart1==-1 ? 0 : indexPart1+1), part2.substring(0, indexPart2==-1 ? part2.length() : indexPart2)};
    }
    
    public static OperationType getType(String str){
        if(str.contains(OperationType.DIVISION.symbol)){
            return OperationType.DIVISION;
        }
        if(str.contains(OperationType.MULTIPLICATION.symbol)){
            return OperationType.MULTIPLICATION;
        }
        if(str.contains(OperationType.ADDITION.symbol)){
            return OperationType.ADDITION;
        }
        if(str.contains(OperationType.SUBTRACTION.symbol)){
            return OperationType.SUBTRACTION;
        }
        return null;
    }
    
    public static double doCalculation(String operation){
        double resPart1 = 0;
        double resPart2 = 0;
    
        int i = -1;
        if((i = operation.indexOf("/"))!=-1){
            String[] split = operation.split("/", 2);
            String[] split1 = isolateParts(split[0], split[1]);
            double v = Operation.doOperation(Double.parseDouble(split1[0]), Double.parseDouble(split1[1]), OperationType.DIVISION);
            operation = operation.replace(split1[0] + "/" +  split1[1], v + "");
            if(getType(operation)!=null){
                return doCalculation(operation);
            }else{
                return Double.parseDouble(operation);
            }
        }else if((i = operation.indexOf("*"))!=-1){
            String[] split = operation.split("\\*", 2);
            String[] split1 = isolateParts(split[0], split[1]);
            double v = Operation.doOperation(Double.parseDouble(split1[0]), Double.parseDouble(split1[1]), OperationType.MULTIPLICATION);
            operation = operation.replace(split1[0] + "*" +  split1[1], v + "");
            if(getType(operation)!=null){
                return doCalculation(operation);
            }else{
                return Double.parseDouble(operation);
            }
        }else if((i = operation.indexOf("+"))!=-1){
            String[] split = operation.split("\\+", 2);
            String[] split1 = isolateParts(split[0], split[1]);
            double v = Operation.doOperation(Double.parseDouble(split1[0]), Double.parseDouble(split1[1]), OperationType.ADDITION);
            operation = operation.replace(split1[0] + "+" +  split1[1], v + "");
            if(getType(operation)!=null){
                return doCalculation(operation);
            }else{
                return Double.parseDouble(operation);
            }
        }else if((i = operation.indexOf("-"))!=-1){
            String[] split = operation.split("-", 2);
            if(split[0].isEmpty()){
                return Double.parseDouble(operation);
            }
            String[] split1 = isolateParts(split[0], split[1]);
            double v = Operation.doOperation(Double.parseDouble(split1[0]), Double.parseDouble(split1[1]), OperationType.SUBTRACTION);
            operation = operation.replace(split1[0] + "-" +  split1[1], v + "");
            if(getType(operation)!=null){
                return doCalculation(operation);
            }else{
                return Double.parseDouble(operation);
            }
        }
    
        return -1;
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
