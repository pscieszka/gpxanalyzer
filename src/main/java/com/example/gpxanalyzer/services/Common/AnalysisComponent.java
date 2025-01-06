package com.example.gpxanalyzer.services.Common;

import com.example.gpxanalyzer.DataModels.ParsedData;

public interface AnalysisComponent {
    void process(ParsedData data);
}
