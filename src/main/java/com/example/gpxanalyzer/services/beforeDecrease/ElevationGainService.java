package com.example.gpxanalyzer.services.beforeDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Math.min;


public class ElevationGainService implements AnalysisComponent {
    private final List<Integer> elevationGainPerKmForCalculation;

    public ElevationGainService() {
        elevationGainPerKmForCalculation = new ArrayList<>();
    }
    public void process(ParsedData data) {
        data.setElevationGain(getTotalElevationGain(data));
        data.setElevationGainPerKm(getElevationGainPerKm(data));
        data.setElevationGainPerKmForCalculation(elevationGainPerKmForCalculation);
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
        elevationGainPerKmForCalculation.clear();
        List<Integer> elevationGainPerKm = new ArrayList<>();
        List<Double> elevation = data.getElevation();
        List<Double> distance = data.getDistanceList();

        double distancePerKm = 0;
        double elevationGain = 0;
        double elevationDeltaForCalculation = 0;
        int cnt = 1;
        int size = Math.min(elevation.size(), distance.size());

        for (int i = 1; i < size; i++) {
            double distanceDelta = distance.get(i) - distance.get(i - 1);
            double elevationDelta = elevation.get(i) - elevation.get(i - 1);
            if(elevationDelta > 0){
                elevationDeltaForCalculation += elevationDelta;
            }
            distancePerKm += distanceDelta;
            elevationGain += elevationDelta;

            if (distancePerKm >= 1000 * cnt) {
                elevationGainPerKm.add((int) elevationGain);
                elevationGainPerKmForCalculation.add((int) elevationDeltaForCalculation);
                elevationGain = 0;
                elevationDeltaForCalculation = 0;
                cnt++;
            }
        }
        elevationGainPerKm.add((int) elevationGain);
        elevationGainPerKmForCalculation.add((int) elevationDeltaForCalculation);

        return elevationGainPerKm;
    }
}
