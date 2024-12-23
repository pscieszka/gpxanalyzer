package com.example.gpxanalyzer.FileTypesStrategies;

import java.io.InputStream;

public class GpxParsingStrategy implements FileParsingStrategy {
    @Override
    public String parseFile(InputStream inputStream) {
        return "Gpx file parsed";
    }
}
