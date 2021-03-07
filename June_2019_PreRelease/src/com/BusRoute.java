package com;

import java.util.Arrays;
import java.util.Objects;

public class BusRoute{
    
    public final String name;
    public int[] arrivals;
    public int lateArrivals;
    public double avgMinsLate;
    public double avgMinsOnlyLate;
    
    public BusRoute(String name, int[] arrivals){
        this.name = name;
        this.arrivals = arrivals;
    }
    
    public BusRoute(String name, int arrivalsCount){
        this.name = name;
        this.arrivals = new int[arrivalsCount];
    }
    
    @Override
    public String toString(){
        return "BusRoute{" +
                "name='" + name + '\'' +
                ", lateArrivals=" + lateArrivals +
                ", avgMinsLate=" + avgMinsLate +
                ", avgMinsOnlyLate=" + avgMinsOnlyLate +
                '}';
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        BusRoute busRoute = (BusRoute) o;
        return Objects.equals(name, busRoute.name) && Arrays.equals(arrivals, busRoute.arrivals);
    }
    
    @Override
    public int hashCode(){
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(arrivals);
        return result;
    }
}
