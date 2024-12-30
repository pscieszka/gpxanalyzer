package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;

public class HeartRateService implements AnalysisComponent {
    public void process(ParsedData data) {
        int heartRateSum = 0;
        int heartRateCounter = 0;
        for (int heartRate : data.getHeartRates()) {
            heartRateSum += heartRate;
            heartRateCounter++;
        }
        data.setAverageHeartRate(heartRateSum / heartRateCounter);

    }
}
