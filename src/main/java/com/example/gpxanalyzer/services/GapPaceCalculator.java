package com.example.gpxanalyzer.services;

import java.util.List;
import java.util.ArrayList;

public class GapPaceCalculator {

    public static List<Double> handle(List<Double> pace, List<Double> grade){
        List<Double> gapPace = new ArrayList<>();
        int size = Math.min(pace.size(), grade.size());
        for (int i = 0; i < size; i++) {
            gapPace.add(pace.get(i)/calculateGapPaceMultiplier(grade.get(i)));
        }

        return gapPace;
    }
    public static double calculateGapPaceMultiplier(Double grade){
        if(grade >= -0.5 && grade <= 0.5){
            return 1;
        }
        else if(grade > 0.5){
            return 1 + 0.0181038 * Math.pow(grade, 1.39476) + 0.00480209;
        }
        else if(grade < -0.5 && grade >= -18){
            return 1 + 0.00194385 * Math.pow(grade, 2) +  0.0361512 * grade + 0.024512;
        }
        else{
            return 1 + -0.000151561 * Math.pow(grade,2) - 0.0502818 * grade - 0.861458;

        }
    }
    

}
