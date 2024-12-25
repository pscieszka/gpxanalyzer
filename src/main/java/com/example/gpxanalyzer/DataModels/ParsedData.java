package com.example.gpxanalyzer.DataModels;

import java.util.List;

public class ParsedData {
    private List<List<Double>> coordinates;

    private List<Integer> heartRates;


    public ParsedData() {}

    public ParsedData(List<List<Double>> coordinates, List<Integer> heartRates) {
        this.coordinates = coordinates;
        this.heartRates = heartRates;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public List<Integer> getHeartRates() {
        return heartRates;
    }
    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public void setHeartRates(List<Integer> heartRates) {
        this.heartRates = heartRates;
    }





}
