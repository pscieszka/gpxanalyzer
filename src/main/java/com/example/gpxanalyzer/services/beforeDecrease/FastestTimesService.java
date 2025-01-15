package com.example.gpxanalyzer.services.beforeDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;

import java.util.*;

public class FastestTimesService implements AnalysisComponent {

    @Override
    public void process(ParsedData data) {
        data.setFastestTimes(getFastestTimes(data));
    }
    private Map<String, String> getFastestTimes(ParsedData data) {
        List<List<Double>> coordinates = data.getCoordinates();
        List<Integer> time = data.getTime();
        double totalDistance = data.getTotalDistance();

        LinkedHashMap<Double, String> distanceLabels = new LinkedHashMap<>();
        distanceLabels.put(400.0, "400m");
        distanceLabels.put(800.0, "800m");
        distanceLabels.put(1609.34, "1 mile");
        distanceLabels.put(3218.68, "2 miles");
        distanceLabels.put(5000.0, "5 km");
        distanceLabels.put(10000.0, "10 km");
        distanceLabels.put(21097.5, "Half-Marathon");
        distanceLabels.put(42195.0, "Marathon");

        // Filter target distances based on total distance
        List<Double> targetDistances = new ArrayList<>();
        for (Map.Entry<Double, String> entry : distanceLabels.entrySet()) {
            if (entry.getKey() <= totalDistance) {
                targetDistances.add(entry.getKey());
            }
        }

        Map<Double, Integer> fastestTimes = new HashMap<>();
        for (double target : targetDistances) {
            fastestTimes.put(target, Integer.MAX_VALUE);
        }

        double[] cumulativeDistances = new double[coordinates.size()];
        cumulativeDistances[0] = 0;
        for (int i = 1; i < coordinates.size(); i++) {
            cumulativeDistances[i] = cumulativeDistances[i - 1] + DistanceService.calculateDistance(coordinates.get(i - 1), coordinates.get(i));
        }

        for (int start = 0; start < coordinates.size(); start++) {
            for (int end = start + 1; end < coordinates.size(); end++) {
                double distanceCovered = cumulativeDistances[end] - cumulativeDistances[start];

                for (double target : targetDistances) {
                    if (distanceCovered >= target) {
                        int timeTaken = time.get(end) - time.get(start);
                        if (timeTaken < fastestTimes.get(target)) {
                            fastestTimes.put(target, timeTaken);
                        }
                    }
                }

                if (distanceCovered >= targetDistances.get(targetDistances.size() - 1)) {
                    break;
                }
            }
        }

        Map<String, String> formattedFastestTimes = new LinkedHashMap<>();
        for (Map.Entry<Double, String> entry : distanceLabels.entrySet()) {
            Double distance = entry.getKey();
            String label = entry.getValue();
            if (fastestTimes.containsKey(distance) && fastestTimes.get(distance) != Integer.MAX_VALUE) {
                formattedFastestTimes.put(label, formatTime(fastestTimes.get(distance)));
            }
        }

        return formattedFastestTimes;
    }

    private String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
