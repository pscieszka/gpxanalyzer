package com.example.gpxanalyzer.FileTypesStrategies;

import java.io.InputStream;

public class FitParsingStrategy implements FileParsingStrategy {
    @Override
    public String parseFile(InputStream inputStream) {
        return "Fit file parsed";
    }
}
