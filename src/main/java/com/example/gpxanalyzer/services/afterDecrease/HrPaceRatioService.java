package com.example.gpxanalyzer.services.afterDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;
import com.example.gpxanalyzer.services.Common.DataSmootherHandler;

import java.util.ArrayList;
import java.util.List;

public class HrPaceRatioService implements AnalysisComponent {
    @Override
    public void process(ParsedData data) {
        data.setHrPaceRatios(getHrPaceRatios(data));
        data.setEffortScore(getAverageEffortScore(data.getHrPaceRatios()));
    }

    public List<Integer> getHrPaceRatios(ParsedData data) {
        List<Integer> hrPaceRatios = new ArrayList<>();
        List<Integer> heartRateData = data.getHeartRates();
        List<Double> paceData = data.getGapPaceChart();

        int size = Math.min(heartRateData.size(), paceData.size());
        for (int i = 0; i < size; i++) {
            int minutes = (int) (paceData.get(i) / 60);
            int seconds = (int) ((paceData.get(i) - minutes) * 60) + minutes*60;
            double paceWeight = 0.7;
            int ratio = 900 - (heartRateData.get(i) * (int)(seconds*paceWeight))/100;
            hrPaceRatios.add(ratio);

        }

        return DataSmootherHandler.movingAverage(hrPaceRatios, 20);

    }

    public Integer getAverageEffortScore(List<Integer> hrPaceRatios) {
        int sum = 0;
        for (int ratio : hrPaceRatios) {
            sum += ratio;
        }
        return sum / hrPaceRatios.size();
    }
}
