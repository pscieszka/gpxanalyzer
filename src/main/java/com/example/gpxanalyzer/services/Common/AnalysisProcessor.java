package com.example.gpxanalyzer.services.Common;

import com.example.gpxanalyzer.DataModels.ParsedData;

import java.util.ArrayList;
import java.util.List;

public class AnalysisProcessor implements AnalysisComponent {
    private static AnalysisProcessor instance;

    private List<AnalysisComponent> components = new ArrayList<>();

    private AnalysisProcessor() {}

    public static AnalysisProcessor getInstance() {
        if (instance == null) {
            instance = new AnalysisProcessor();
        }
        return instance;
    }
    public void addComponent(AnalysisComponent component) {
        components.add(component);
    }
    public void process(ParsedData data) {
        for (AnalysisComponent component : components) {
            component.process(data);
        }
    }
    public void clearComponents() {
        components.clear();
    }
}
