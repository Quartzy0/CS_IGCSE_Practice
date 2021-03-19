package com;

import java.util.HashMap;
import java.util.Map;

public class Tests{
    private static HashMap<String, Double> tests = new HashMap<>();
    
    static {
        tests.put("5*(9-3*4/(3-4))*(5-7)", -210.0);
        tests.put("53*23/55-(4/(44*12+4))", 22.15611756664388);
        tests.put("(10*102.32)/234.234+(69/420+234.234)", 238.76656700564394);
        tests.put("((((((3)*3)*3)*3)/3)/3)-3", 6.0);
    }
    
    public static void doTests(){
        for(Map.Entry<String, Double> stringDoubleEntry : tests.entrySet()){
            double v = Interpreter.doCalculation(stringDoubleEntry.getKey());
            if(v != stringDoubleEntry.getValue()){
                throw new RuntimeException("Incorrect result from math interpreter test! " + stringDoubleEntry.getKey() + " = " + stringDoubleEntry.getValue() + " got " + v);
            }
        }
    }
}
