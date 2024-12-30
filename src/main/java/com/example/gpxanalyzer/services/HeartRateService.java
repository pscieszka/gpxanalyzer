package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;

import java.util.ArrayList;
import java.util.List;

public class HeartRateService implements AnalysisComponent {
    public void process(ParsedData data) {
        data.setAverageHeartRate(calculateHeartRate(data));
        data.setHeartRatePerKm(calculateHeartRatePerKm(data));
    }
    private int calculateHeartRate(ParsedData data){
        int heartRateSum = 0;
        int heartRateCounter = 0;
        for (int heartRate : data.getHeartRates()) {
            heartRateSum += heartRate;
            heartRateCounter++;
        }
        return (int)heartRateSum / heartRateCounter;
    }

    private List<Integer> calculateHeartRatePerKm(ParsedData data) {
        List<Integer> heartRatePerKm = new ArrayList<>();
        List<Double> distance = data.getDistanceList();
        List<Integer> heartRates = data.getHeartRates();
        double distancePerKm = 0;
        int cnt = 1;
        int hrSum = 0;
        int hrCounter = 0;

        int size = Math.min(distance.size(), heartRates.size());

        for (int i = 1; i < size; i++) {
            double distanceDelta = distance.get(i) - distance.get(i - 1);

            distancePerKm += distanceDelta;
            hrSum += heartRates.get(i);
            hrCounter++;


            if (distancePerKm >= 1000 * cnt) {
                cnt++;
                heartRatePerKm.add(hrSum/hrCounter);
                hrSum = 0;
                hrCounter = 0;

            }
        }
        heartRatePerKm.add(hrSum/hrCounter);
        return heartRatePerKm;
    }
}
