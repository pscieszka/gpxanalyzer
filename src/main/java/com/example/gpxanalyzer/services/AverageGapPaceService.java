package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;
import org.slf4j.Logger;

public class AverageGapPaceService implements AnalysisComponent {
    public void process(ParsedData data) {
        String averagePace = data.getAveragePace();
        double totalDistance = data.getTotalDistance();
        int totalElevation = data.getElevationGain();
        double averagePaceInMinutes = (Double.parseDouble(averagePace.split(":")[0]) * 60 + Double.parseDouble(averagePace.split(":")[1]))/60;
        double grade = (double) totalElevation / (totalDistance/1000)/10;
        double gapPaceMultiplier = GapPaceCalculator.calculateGapPaceMultiplier(grade);
        double gapPace = averagePaceInMinutes / gapPaceMultiplier;
        int minutes = (int) gapPace;
        int seconds = (int) ((gapPace - minutes) * 60);
        data.setAverageGapPace(String.format("%d:%02d", minutes, seconds));
    }
}
