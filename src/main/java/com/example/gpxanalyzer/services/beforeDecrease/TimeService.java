package com.example.gpxanalyzer.services.beforeDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;

public class TimeService implements AnalysisComponent {
    public void process(ParsedData data) {
         data.setTotalTimeInSeconds(data.getTime().get(data.getTime().size() - 1) - data.getTime().get(0));
    }
}
