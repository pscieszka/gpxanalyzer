package com.example.gpxanalyzer.services;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class PaceService {
    private static List<Double> paceForKm = new ArrayList<>();
//dystans bez zmian
    public static List<Double> getPaces(List<Double> distanceList, List<Integer> time) {
        List<Double> paceData = new ArrayList<>();
        paceForKm = new ArrayList<>();
        double numerator=0;
        double denominator=0;
        double numeratorPerKm = 0;
        double denominatorPerKm = 0;
        for (int i = 0; i < distanceList.size() - 1; i++) {
            numerator+= distanceList.get(i+1) - distanceList.get(i);
            denominator+= time.get(i+1) - time.get(i);
            numeratorPerKm+= distanceList.get(i+1) - distanceList.get(i);
            denominatorPerKm+= time.get(i+1) - time.get(i);
            if(numeratorPerKm >= 1000){
                double rawPacePerKm = ((1000 / numeratorPerKm) * denominatorPerKm) / 60;
                numeratorPerKm = 0;
                denominatorPerKm = 0;
                paceForKm.add(rawPacePerKm);
            }

            double rawPace = ((1000 / numerator) * denominator) / 60;
            numerator = 0;
            denominator = 0;
            if(rawPace > 21.0){
                rawPace = 21.05;
            }
            paceData.add(rawPace);

        }
        return paceData;
    }
    public static  List<Double> getPaceForKm(){
        return paceForKm;
    }


}
