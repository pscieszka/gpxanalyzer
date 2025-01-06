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
        int cnt = 1;
        int size = Math.min(distance.size(), time.size());

        for (int i = 1; i < size; i++) {
            double distanceDelta = distance.get(i) - distance.get(i - 1);
            int timeDelta = time.get(i) - prevTime;

            distancePerKm += distanceDelta;

            if (distancePerKm >= 1000 * cnt) {
                double pace = (double) timeDelta / 60;
                int minutes = (int) pace;
                int seconds = (int) ((pace - minutes) * 60);
                pacePerKm.add(minutes * 60 + seconds);
                prevTime = time.get(i);
                cnt++;
            }
        }

            double remainingDistance = distance.get(distance.size() - 1) - 1000 * (cnt - 1);
            int remainingTime = time.get(time.size() - 1) - prevTime;

            double pace = (double) remainingTime / remainingDistance * 1000 / 60;
            int minutes = (int) pace;
            int seconds = (int) ((pace - minutes) * 60);
            pacePerKm.add(minutes * 60 + seconds);



        return pacePerKm;
    }

}
