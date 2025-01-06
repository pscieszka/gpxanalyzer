package com.example.gpxanalyzer.services.afterDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;
import com.example.gpxanalyzer.services.Common.DataSmootherHandler;

import java.util.ArrayList;
import java.util.List;


public class PaceChartService implements AnalysisComponent {
    @Override
    public void process(ParsedData data) {
        data.setPaceChart(getPaces(data));
    }
    public static List<Double> getPaces(ParsedData data) {
        List<Double> paceData = new ArrayList<>();
        List<Double> distanceList = data.getDistanceList();
        List<Integer> time = data.getTime();

        double numerator=0;
        double denominator=0;
        int size = Math.min(distanceList.size(), time.size());
        for (int i = 0; i < size - 1; i++) {
            numerator+= distanceList.get(i+1) - distanceList.get(i);
            denominator+= time.get(i+1) - time.get(i);
            double rawPace = ((1000 / numerator) * denominator) / 60;
            numerator = 0;
            denominator = 0;
            if(rawPace > 21.0){
                rawPace = 21.05;
            }
            paceData.add(rawPace);
        }
        return DataSmootherHandler.movingAverage(paceData, 10);
    }
}
