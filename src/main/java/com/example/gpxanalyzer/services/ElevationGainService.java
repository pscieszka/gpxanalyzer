package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Math.min;

public class ElevationGainService implements AnalysisComponent {
    public void process(ParsedData data) {
        data.setElevationGain(getTotalElevationGain(data));
        data.setElevationGainPerKm(getElevationGainPerKm(data));
    }

    private int getTotalElevationGain(ParsedData data) {
        double elevationGain = 0;
        for (int i = 1; i < data.getElevation().size(); i++) {
            if (data.getElevation().get(i) > data.getElevation().get(i - 1)) {
                elevationGain += data.getElevation().get(i) - data.getElevation().get(i - 1);
            }
        }
        return (int) elevationGain;
    }

    private List<Integer> getElevationGainPerKm(ParsedData data) {
        List<Integer> elevationGainPerKm = new ArrayList<>();
        List<Double> elevation = data.getElevation();
        List<Double> distance = data.getDistanceList();
        Logger logger = Logger.getLogger(ElevationGainService.class.getName());

        double distancePerKm = 0;
        double elevationGain = 0;
        int cnt = 1;
        int size = Math.min(elevation.size(), distance.size());

        for (int i = 1; i < size; i++) {
            double distanceDelta = distance.get(i) - distance.get(i - 1);  // różnica między kolejnymi punktami
            double elevationDelta = elevation.get(i) - elevation.get(i - 1);

            distancePerKm += distanceDelta;
            elevationGain += elevationDelta;

            if (distancePerKm >= 1000 * cnt) {
                elevationGainPerKm.add((int) elevationGain);
                elevationGain = 0;
                cnt++;
            }
        }
        elevationGainPerKm.add((int) elevationGain);

        return elevationGainPerKm;
    }
}
