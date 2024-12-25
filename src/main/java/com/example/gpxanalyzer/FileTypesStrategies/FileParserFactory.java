package com.example.gpxanalyzer.FileTypesStrategies;

public class FileParserFactory {
    public static FileParser getParser(String filePath) {
        if (filePath.endsWith(".gpx")) {
            return new GpxParser();
        } else if (filePath.endsWith(".tcx")) {
            return new TcxParser();
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + filePath);
        }
    }
}

