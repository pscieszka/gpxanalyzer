package com.example.gpxanalyzer.FileTypesStrategies;

import java.io.InputStream;

public class TcxParsingStrategy implements FileParsingStrategy {
    @Override
    public String parseFile(InputStream inputStream) {
        return "Tcx file parsed";
    }
}
