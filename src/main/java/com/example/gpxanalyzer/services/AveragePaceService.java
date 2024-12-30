package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;
import org.slf4j.Logger;

public class AveragePaceService implements AnalysisComponent {
    public void process(ParsedData data) {
        double totalDistance = data.getTotalDistance() / 1000;
        double totalTime = (double) data.getTotalTimeInSeconds() / 60;
        double pace = totalTime / totalDistance;
        int minutes = (int) pace;
        int seconds = (int) ((pace - minutes) * 60);
        data.setAveragePace(String.format("%d:%02d", minutes, seconds));
    }

}
