package com.example.gpxanalyzer.services.beforeDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;

import java.util.ArrayList;
import java.util.List;

public class DistanceService implements AnalysisComponent {
    public void process(ParsedData data) {
        double distance = 0;
        List<Double> distanceList = new ArrayList<>();

        distanceList.add(0.0);
        for (int i = 1; i < data.getCoordinates().size() - 1; i++) {
            double a = getaDouble(data.getCoordinates(), i);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double EARTH_RADIUS = 6371e3;
            distance += EARTH_RADIUS * c;
            distanceList.add(distance);
        }
        data.setDistanceList(distanceList);
        data.setTotalDistance(distance);
    }

    private static double getaDouble(List<List<Double>> coordinates, int i) {
        double lat1 = Math.toRadians(coordinates.get(i).get(0));
        double lon1 = Math.toRadians(coordinates.get(i).get(1));
        double lat2 = Math.toRadians(coordinates.get(i + 1).get(0));
        double lon2 = Math.toRadians(coordinates.get(i + 1).get(1));

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        return Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
    }
    public static double calculateDistance(List<Double> coord1, List<Double> coord2) {
        double lat1 = Math.toRadians(coord1.get(0));
        double lon1 = Math.toRadians(coord1.get(1));
        double lat2 = Math.toRadians(coord2.get(0));
        double lon2 = Math.toRadians(coord2.get(1));

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double EARTH_RADIUS = 6371e3;  // Promień Ziemi w metrach

        return EARTH_RADIUS * c;
    }
}

