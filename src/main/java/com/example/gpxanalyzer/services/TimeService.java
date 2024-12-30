package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;

import java.util.List;

public class TimeService implements AnalysisComponent {
    public void process(ParsedData data) {
         data.setTotalTimeInSeconds(data.getTime().get(data.getTime().size() - 1) - data.getTime().get(0));
    }
}
