package com.example.gpxanalyzer.services;

import org.springframework.ui.Model;
import com.example.gpxanalyzer.DataModels.ParsedData;

public class ModelAttributeService {
    public void addAttributes(Model model, ParsedData data) {
        // Info
        model.addAttribute("totalTimeInString", data.getTotalTimeInString());
        model.addAttribute("totalDistance", data.getTotalDistanceInKm());
        model.addAttribute("elevationGain", data.getElevationGain());
        model.addAttribute("averageHeartRate", data.getAverageHeartRate());
        model.addAttribute("averagePace", data.getAveragePace());
        model.addAttribute("averageGapPace", data.getAverageGapPace());

        // Table
        model.addAttribute("pacePerKm", data.getPacePerKm());
        model.addAttribute("gapPacePerKm", data.getGapPacePerKm());
        model.addAttribute("heartRatePerKm", data.getHeartRatePerKm());
        model.addAttribute("averageElevationGainPerKm", data.getElevationGainPerKm());

        // Map
        model.addAttribute("coordinates", data.getCoordinates());

        // Chart
        model.addAttribute("paceData", data.getPaceChart());
        model.addAttribute("HrData", data.getHrChart());
        model.addAttribute("elevationData", data.getElevationRaw());
        model.addAttribute("gapPace", data.getGapPaceChart());
        model.addAttribute("gradeChart", data.getGradeChartMovingAverage());

        // Table
        model.addAttribute("pacesAtGrades", data.getPacesAtGrades());

        // Ratio
        model.addAttribute("hrPaceRatios", data.getHrPaceRatios());
        model.addAttribute("effortScore", data.getEffortScore());
        model.addAttribute("hrAtPaces", data.getHrAtPaces());
        model.addAttribute("fastestTimes", data.getFastestTimes());
    }
}
