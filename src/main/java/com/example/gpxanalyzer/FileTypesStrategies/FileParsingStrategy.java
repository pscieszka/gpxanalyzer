package com.example.gpxanalyzer.FileTypesStrategies;

import java.io.InputStream;

public interface FileParsingStrategy {
    public String parseFile(InputStream inputStream);
}
