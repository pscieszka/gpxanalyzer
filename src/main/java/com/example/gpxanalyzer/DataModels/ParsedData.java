package com.example.gpxanalyzer.DataModels;

import com.example.gpxanalyzer.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
    private List<Integer> heartRatePerKm;
    private List<Integer> pacePerKm;
    private List<Integer> gapPacePerKm;



    public ParsedData() {}

    public ParsedData(List<List<Double>> coordinates, List<Integer> heartRates, List<Double> elevation, List<Integer> time) {
        this.coordinates = coordinates;
        this.heartRates = heartRates;
        this.elevation = elevation;
        this.time = time;
        //getDataInIntervals(); -> do wykresow tlyko
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


    //
//    public List<Integer> getHeartRates() {
//        return heartRates;
//    }
//
//    public List<Integer> getTime() {
//        return time;
//    }
//
//    public void setTime(List<Integer> time) {
//        this.time = time;
//    }
//
//    public List<Double> getElevation() {
//        return elevation;
//    }
//
//    public int getTotalTimeInSeconds() {
//        return TimeService.handle(time);
//    }
//
//    public List<Double> getGrade() {
//        return GradeService.handle(elevation, getDistanceList());
//    }
//
//    public List<Double> getPace() {
//        return PaceService.getPaces(getDistanceList(), time);
//    }
//
//    public List<Double> getPaceAtGrades() {
//        return GradeService.getPaceAtGrades(getPace(), getGrade());
//    }
//    public double getMaxGrade(List<Double> grades) {
//        return GradeService.getMaxGrade(grades);
//    }
//    public double getMinGrade(List<Double> grades) {
//        return GradeService.getMinGrade(grades);
//    }
//
//    public List<Double> getGapPace(){
//        return GapPaceService.handle(this.getPace(), this.getGrade());
//    }
//
//
//    public void getDataInIntervals() {
//        List<List<Double>> tempCoordinates = new ArrayList<>();
//        List<Integer> tempHeartRates = new ArrayList<>();
//        List<Integer> tempTime = new ArrayList<>();
//        List<Double> tempElevation = new ArrayList<>();
//        int distance = 0;
//        for(int i = 0; i < coordinates.size()-1; i++) {
//            distance += (int) DistanceService.calculateDistance(coordinates.get(i), coordinates.get(i + 1));
//            if (distance >= 20) {
//                tempCoordinates.add(coordinates.get(i));
//                tempHeartRates.add(this.heartRates.get(i));
//                tempTime.add(this.time.get(i));
//                tempElevation.add(this.elevation.get(i));
//                distance = 0;
//
//            }
//        }
//        coordinates = tempCoordinates;
//        heartRates = tempHeartRates;
//        time = tempTime;
//        elevation = tempElevation;
//    }
//    public List<Double> getPaceForKm() {
//        return PaceService.getPaceForKm();
//    }
//
//



}
