package com.example.gpxanalyzer.services.beforeDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;

import java.util.ArrayList;
import java.util.List;

public class AveragePaceService implements AnalysisComponent {
    public void process(ParsedData data) {
        data.setAveragePace(calculatePace(data));
        data.setPacePerKm(calculatePacePerKm(data));

    }

    private String calculatePace(ParsedData data) {
        double totalDistance = data.getTotalDistance() / 1000;
        double totalTime = (double) data.getTotalTimeInSeconds() / 60;
        double pace = totalTime / totalDistance;
        int minutes = (int) pace;
        int seconds = (int) ((pace - minutes) * 60);
        return String.format("%d:%02d", minutes, seconds);
    }

    private List<Integer> calculatePacePerKm(ParsedData data) {
        List<Integer> pacePerKm = new ArrayList<>();
        List<Double> distance = data.getDistanceList();
        List<Integer> time = data.getTime();

        double distancePerKm = 0;
        int prevTime = time.get(0);
        int size = Math.min(distance.size(), time.size());

        for (int i = 1; i < size; i++) {
            double distanceDelta = distance.get(i) - distance.get(i - 1);
            int timeDelta = time.get(i) - time.get(i - 1);

            distancePerKm += distanceDelta;

            while (distancePerKm >= 1000) {
                double excessDistance = distancePerKm - 1000;
                double interpolationFactor = (distanceDelta - excessDistance) / distanceDelta;
                int interpolatedTime = (int) (timeDelta * interpolationFactor);

                int kmTime = time.get(i - 1) - prevTime + interpolatedTime;
                pacePerKm.add(kmTime);

                prevTime += kmTime;
                distancePerKm -= 1000;
            }
        }

        if (distancePerKm > 0) {
            int remainingTime = time.get(size - 1) - prevTime;
            double pace = remainingTime / (distancePerKm / 1000.0);
            pacePerKm.add((int) Math.round(pace));
        }

        return pacePerKm;
    }

}
