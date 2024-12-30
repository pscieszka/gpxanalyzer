package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;

public interface AnalysisComponent {
    void process(ParsedData data);
}
