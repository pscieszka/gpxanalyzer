package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;

public class ElevationGainService implements AnalysisComponent {
    public void process(ParsedData data) {
        double elevationGain = 0;
        for (int i = 1; i < data.getElevation().size(); i++) {
            if (data.getElevation().get(i) > data.getElevation().get(i - 1)) {
                elevationGain += data.getElevation().get(i) - data.getElevation().get(i - 1);
            }
        }
        data.setElevationGain((int)elevationGain);
    }
}
