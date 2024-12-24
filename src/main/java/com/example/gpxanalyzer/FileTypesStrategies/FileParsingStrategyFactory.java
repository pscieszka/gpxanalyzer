package com.example.gpxanalyzer.FileTypesStrategies;

public class FileParsingStrategyFactory {
    public static FileParsingStrategy getStrategy(String filename) {
        if (filename.endsWith(".gpx")) {
            return new GpxParsingStrategy();
//        } else if (filename.endsWith(".tcx")) {
//            return new TcxParsingStrategy();
//        } else if (filename.endsWith(".fit")) {
//            //return new FitParsingStrategy();
        } else {
            throw new IllegalArgumentException("Nieobsługiwany format pliku: " + filename);
        }
    }
}
