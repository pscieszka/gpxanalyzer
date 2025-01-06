package com.example.gpxanalyzer.DataModels;

import com.example.gpxanalyzer.services.beforeDecrease.DistanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParsedData {
    //Raw data
    private List<List<Double>> coordinates;
    private List<Integer> heartRates;
    private List<Integer> time;
    private List<Double> elevation;


    //Processed raw data
    private List<Double> distanceList;

    private double totalDistance;
    private int totalTimeInSeconds;
    private int elevationGain;
    private int averageHeartRate;
    private String averagePace;
    private String averageGapPace;

    private List<Integer> elevationGainPerKm;
    private List<Integer> elevationGainPerKmForCalculation;
    private List<Integer> heartRatePerKm;
    private List<Integer> pacePerKm;
    private List<Integer> gapPacePerKm;

    private List<Double> paceChart;
    private List<Integer> HrChart;
    private List<Double> gapPaceChart;
    private List<Double> gradeChart;
    private List<Double> gradeChartMovingAverage;
    private List<Map<String, Object>> pacesAtGrades;
    private List<Integer> hrPaceRatios;
    private Integer effortScore;
    private Map<String, String> hrAtPaces;


    public ParsedData() {}

    public ParsedData(List<List<Double>> coordinates, List<Integer> heartRates, List<Double> elevation, List<Integer> time) {
        this.coordinates = coordinates;
        this.heartRates = heartRates;
        this.elevation = elevation;
        this.time = time;
    }
    public List<Double> getDistanceList() {
        return distanceList;
    }

    public  void setDistanceList(List<Double> distanceList) {
        this.distanceList = distanceList;
    }
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
    public List<List<Double>> getCoordinates() {
        return coordinates;
    }
    public double getTotalDistance() {
        return totalDistance;
    }

    public int getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }
    public void setTotalTimeInSeconds(int totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
    }
    public List<Integer> getTime() {
        return time;
    }
    public List<Double> getElevation() {
        return elevation;
    }
    public void setElevationGain(int elevationGain) {
        this.elevationGain = elevationGain;
    }
    public int getElevationGain() {
        return elevationGain;
    }
    public List<Integer> getHeartRates() {
        return heartRates;
    }
    public void setAverageHeartRate(int averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
    }
    public int getAverageHeartRate() {
        return averageHeartRate;
    }
    public void setAveragePace(String averagePace) {
        this.averagePace = averagePace;
    }
    public String getAveragePace() {
        return averagePace;
    }
    public String getTotalTimeInString(){
        int hours = totalTimeInSeconds / 3600;
        int minutes = (totalTimeInSeconds % 3600) / 60;
        int seconds = totalTimeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    public String getTotalDistanceInKm(){
        return String.format("%.2f", totalDistance / 1000);
    }
    public void setAverageGapPace(String averageGapPace) {
        this.averageGapPace = averageGapPace;
    }
    public String getAverageGapPace() {
        return averageGapPace;
    }

    public void setElevationGainPerKm(List<Integer> elevationGainPerKm) {
        this.elevationGainPerKm = elevationGainPerKm;
    }
    public List<Integer> getElevationGainPerKm() {
        return elevationGainPerKm;
    }
    public void setHeartRatePerKm(List<Integer> heartRatePerKm) {
        this.heartRatePerKm = heartRatePerKm;
    }
    public List<Integer> getHeartRatePerKm() {
        return heartRatePerKm;
    }
    public void setPacePerKm(List<Integer> pacePerKm) {
        this.pacePerKm = pacePerKm;
    }
    public List<Integer> getPacePerKm() {
        return pacePerKm;
    }
    public void setGapPacePerKm(List<Integer> gapPacePerKm) {
        this.gapPacePerKm = gapPacePerKm;
    }
    public List<Integer> getGapPacePerKm() {
        return gapPacePerKm;
    }

    public void setPaceChart(List<Double> paceChart) {
        this.paceChart = paceChart;
    }
    public List<Double> getPaceChart() {
        return paceChart;
    }
    public void setHrChart(List<Integer> hrChart) {
        this.HrChart = hrChart;
    }
    public List<Integer> getHrChart() {
        return HrChart;
    }
    public void setGapPaceChart(List<Double> gapPaceChart) {
        this.gapPaceChart = gapPaceChart;
    }
    public List<Double> getGapPaceChart() {
        return gapPaceChart;
    }
    public void setGradeChart(List<Double> gradeChart) {
        this.gradeChart = gradeChart;
    }
    public List<Double> getGradeChart() {
        return gradeChart;
    }
    public void setGradeChartMovingAverage(List<Double> gradeChartMovingAverage) {
        this.gradeChartMovingAverage = gradeChartMovingAverage;
    }
    public List<Double> getGradeChartMovingAverage() {
        return gradeChartMovingAverage;
    }

    public void setPacesAtGrades(List<Map<String, Object>> pacesAtGradesIntervals) {
        this.pacesAtGrades = pacesAtGradesIntervals;
    }
    public List<Map<String, Object>> getPacesAtGrades() {
        return pacesAtGrades;
    }

    public void setElevationGainPerKmForCalculation(List<Integer> elevationGainPerKmForCalculation) {
        this.elevationGainPerKmForCalculation = elevationGainPerKmForCalculation;
    }
    public List<Integer> getElevationGainPerKmForCalculation() {
        return elevationGainPerKmForCalculation;
    }
    public void setHrPaceRatios(List<Integer> hrPaceRatios) {
        this.hrPaceRatios = hrPaceRatios;
    }
    public List<Integer> getHrPaceRatios() {
        return hrPaceRatios;
    }
    public void setEffortScore(Integer effortScore) {
        this.effortScore = effortScore;
    }
    public Map<String, String> getHrAtPaces() {
        return hrAtPaces;
    }
    public void setHrAtPaces(Map<String, String> hrAtPaces) {
        this.hrAtPaces = hrAtPaces;
    }
    public String getEffortScore() {
        return effortScore.toString();
    }

    public List<Double> getElevationRaw() {
        return elevation;
    }

    public void getDataInIntervals() {
        List<List<Double>> tempCoordinates = new ArrayList<>();
        List<Integer> tempHeartRates = new ArrayList<>();
        List<Integer> tempTime = new ArrayList<>();
        List<Double> tempElevation = new ArrayList<>();
        int distance = 0;
        for(int i = 0; i < coordinates.size()-1; i++) {
            distance += (int) DistanceService.calculateDistance(coordinates.get(i), coordinates.get(i + 1));
            if (distance >= 20) {
                tempCoordinates.add(coordinates.get(i));
                tempHeartRates.add(this.heartRates.get(i));
                tempTime.add(this.time.get(i));
                tempElevation.add(this.elevation.get(i));
                distance = 0;

            }
        }
        coordinates = tempCoordinates;
        heartRates = tempHeartRates;
        time = tempTime;
        elevation = tempElevation;
    }
}
