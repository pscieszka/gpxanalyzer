package com.example.gpxanalyzer.services.beforeDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;
import com.example.gpxanalyzer.services.Common.GapPaceCalculator;


import java.util.ArrayList;
import java.util.List;

public class AverageGapPaceService implements AnalysisComponent {
    public void process(ParsedData data) {
        data.setAverageGapPace(calculateAvgGapPace(data));
        data.setGapPacePerKm(calculateGapPacePerKm(data));
    }
    private String calculateAvgGapPace(ParsedData data){
        String averagePace = data.getAveragePace();
        double totalDistance = data.getTotalDistance();
        int totalElevation = data.getElevationGain();
        double averagePaceInMinutes = (Double.parseDouble(averagePace.split(":")[0]) * 60 + Double.parseDouble(averagePace.split(":")[1]))/60;
        double grade = (double) totalElevation / (totalDistance/1000)/10;
        double gapPaceMultiplier = GapPaceCalculator.calculateGapPaceMultiplier(grade);
        double gapPace = averagePaceInMinutes / gapPaceMultiplier;
        int minutes = (int) gapPace;
        int seconds = (int) ((gapPace - minutes) * 60);
        return String.format("%d:%02d", minutes, seconds);
    }

    private List<Integer> calculateGapPacePerKm(ParsedData data){
        List<Integer> pacePerKm = data.getPacePerKm();
        List<Integer> elevationGainPerKm = data.getElevationGainPerKmForCalculation();
        List<Integer> gapPacePerKm = new ArrayList<>();
        for(int i=0; i<pacePerKm.size(); i++){
            double pace = (double) pacePerKm.get(i)/60;
            double grade = (double) elevationGainPerKm.get(i)/10;
            double gapPaceMultiplier = GapPaceCalculator.calculateGapPaceMultiplier(grade);
            double gapPace = (pace / gapPaceMultiplier);
            int minutes = (int) gapPace;
            int seconds = (int) ((gapPace - minutes) * 60);
            gapPacePerKm.add(minutes * 60 + seconds);
        }
        return gapPacePerKm;
    }

}
