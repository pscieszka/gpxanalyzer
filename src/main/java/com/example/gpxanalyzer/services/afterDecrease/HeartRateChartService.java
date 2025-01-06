package com.example.gpxanalyzer.services.afterDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;
import com.example.gpxanalyzer.services.Common.DataSmootherHandler;

import java.util.List;

public class HeartRateChartService implements AnalysisComponent {
    @Override
    public void process(ParsedData data) {
        data.setHrChart(getHeartRates(data));
    }

    public static List<Integer> getHeartRates(ParsedData data) {
        List<Integer> heartRateData = data.getHeartRates();
        DataSmootherHandler.movingAverage(heartRateData, 10);
        return heartRateData;
    }
}
