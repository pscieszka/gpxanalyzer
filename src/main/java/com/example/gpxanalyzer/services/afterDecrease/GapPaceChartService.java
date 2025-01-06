package com.example.gpxanalyzer.services.afterDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;
import com.example.gpxanalyzer.services.Common.GapPaceCalculator;

import java.util.ArrayList;
import java.util.List;

import static com.example.gpxanalyzer.services.Common.DataSmootherHandler.movingAverage;

public class GapPaceChartService implements AnalysisComponent {
    @Override
    public void process(ParsedData data) {
        data.setGapPaceChart(getGapPaces(data));
    }

    public static List<Double> getGapPaces(ParsedData data) {
        List<Double> paceData = data.getPaceChart();
        List<Double> gradeData = data.getGradeChart();
        List<Double> gapPaceData = new ArrayList<>();

        gapPaceData = GapPaceCalculator.handle(paceData, gradeData);

        return movingAverage(gapPaceData, 3);
    }
}
