package com;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args) throws Exception{
        String pathname = PrintUtil.prompt("What file would you like to select?", "bus_data.csv");
        File file = new File(pathname);
        if(!file.exists()){
            PrintUtil.error("File %s (%s) does not exist!", file.getName(), file.getAbsolutePath());
        }
        
        BusRoute[] busRoutes = null;
    
        String wholeFileStr = new String(Files.readAllBytes(file.toPath()));
        String[] fileLines = wholeFileStr.split("\n");
        boolean first = true;
        for(int i = 0;i< fileLines.length;i++){
            String[] values = fileLines[i].split(",");
            if(first){
                busRoutes = new BusRoute[values.length];
                for(int j = 0; j < values.length; j++){
                    busRoutes[j] = new BusRoute(values[j], fileLines.length-1);
                }
                first = false;
                continue;
            }
            for(int j = 0; j < values.length; j++){
                busRoutes[j].arrivals[i-1] = Integer.parseInt(values[j]);
            }
        }
        if(!testBusRoutines(busRoutes)){
            PrintUtil.error("The file provided has incorrect formatting");
        }else{
            PrintUtil.info("The file provided has been correctly parsed");
        }
        
        PrintUtil.newLine();
        
        List<Integer> mostLateArrivalsI = new ArrayList<>();
        for(int i = 0; i < busRoutes.length; i++){
            int lateArrivals = 0;
            double avgMinsLate = 0.0;
            double avgMinsOnlyLate = 0.0;
    
            for(int j = 0; j < busRoutes[i].arrivals.length; j++){
                int arrival = busRoutes[i].arrivals[j];
                if(arrival<0){
                    ++lateArrivals;
                    avgMinsOnlyLate += arrival;
                }
                avgMinsLate += arrival;
            }
            
            avgMinsLate /= busRoutes[i].arrivals.length;
            if(lateArrivals!=0){
                avgMinsOnlyLate /= lateArrivals;
            }
            
            busRoutes[i].lateArrivals = lateArrivals;
            busRoutes[i].avgMinsLate = avgMinsLate;
            busRoutes[i].avgMinsOnlyLate = avgMinsOnlyLate;
            PrintUtil.info(busRoutes[i].toString());
            
            if(mostLateArrivalsI.isEmpty() || busRoutes[mostLateArrivalsI.get(0)].lateArrivals<busRoutes[i].lateArrivals){
                mostLateArrivalsI.clear();
                mostLateArrivalsI.add(i);
            }else if(busRoutes[mostLateArrivalsI.get(0)].lateArrivals==busRoutes[i].lateArrivals){
                mostLateArrivalsI.add(i);
            }
        }
        PrintUtil.newLine();
        if(mostLateArrivalsI.size()>1){
            String busRoutesS = "";
            for(int i = 0; i < mostLateArrivalsI.size(); i++){
                if(i==mostLateArrivalsI.size()-1){
                    busRoutesS+=" and " + busRoutes[mostLateArrivalsI.get(i)].name;
                }else if(i==0){
                    busRoutesS+=busRoutes[mostLateArrivalsI.get(i)].name;
                }else{
                    busRoutesS+=", " + busRoutes[mostLateArrivalsI.get(i)].name;
                }
            }
            PrintUtil.info("Most late bus route were routes %s with %d late arrivals", busRoutesS, busRoutes[mostLateArrivalsI.get(0)].lateArrivals);
        }else{
            PrintUtil.info("Most late bus route was route %s with %d late arrivals", busRoutes[mostLateArrivalsI.get(0)].name, busRoutes[mostLateArrivalsI.get(0)].lateArrivals);
        }
        
        String[] daysString = new String[busRoutes[0].arrivals.length + 1];
        daysString[0] = "none";
        for(int i = 0; i < daysString.length-1; i++){
            String dayStr = "";
            switch(i % 5){
                case 0:
                    dayStr = "Mon";
                    break;
                case 1:
                    dayStr = "Tue";
                    break;
                case 2:
                    dayStr = "Wed";
                    break;
                case 3:
                    dayStr = "Thu";
                    break;
                case 4:
                    dayStr = "Fri";
                    break;
            }
            daysString[i+1] = dayStr + ((i/5)+1);
        }
    
        PrintUtil.newLines(3);
        
        int day = PrintUtil.option("What day do you want to look at specifically?", 0, daysString)-1;
        if(day==-1)return;
        PrintUtil.newLine();
        int numBusesLate = 0;
        for(int i = 0; i < busRoutes.length; i++){
            for(int j = 0; j < busRoutes[i].arrivals.length; j++){
                if(j!=day)continue;
                int arrival = busRoutes[i].arrivals[j];
                if(arrival<0){
                    numBusesLate++;
                    PrintUtil.info("Bus route %s was %d minutes late on %s", busRoutes[i].name, Math.abs(arrival), daysString[day+1]);
                }
            }
        }
        PrintUtil.info("In total %d buses were late on %s", numBusesLate, daysString[day+1]);
    }
    
    public static boolean testBusRoutines(BusRoute[] busRoutes){
        if(busRoutes==null)return false;
        for(int i = 1; i < busRoutes.length; i++){
            if(busRoutes[i].arrivals.length != busRoutes[i-1].arrivals.length){
                return false;
            }
        }
        return true;
    }
}
