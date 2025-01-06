package com.example.gpxanalyzer.services.beforeDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;
import com.example.gpxanalyzer.services.Common.DataSmootherHandler;
import com.example.gpxanalyzer.services.Common.GapPaceCalculator;

import java.util.*;

public class GradeService implements AnalysisComponent {
    @Override
    public void process(ParsedData data) {

        data.setGradeChart(getGrades(data));
        data.setGradeChartMovingAverage(getGradesMovingAverage(data.getGradeChart()));
        data.setPacesAtGrades(getPacesAtGradeIntervals(data.getPaceChart(), data.getGradeChart()));

    }

    private List<Double> getGrades(ParsedData data) {
        List<Double> grades = new ArrayList<>();

        List<Double> elevation =  data.getElevation();
        List<Double> distance = data.getDistanceList();

        int size = Math.min(distance.size(), elevation.size());

        for (int i = 0; i < size-1; i++) {
            double elevationChange = elevation.get(i + 1) - elevation.get(i);
            double distanceChange = distance.get(i + 1) - distance.get(i);
            double gradeValue = (elevationChange / distanceChange) * 100;
            if (Double.isNaN(gradeValue) || Double.isInfinite(gradeValue)) {
                gradeValue = 0;
            }
            grades.add(gradeValue);
        }
        return grades;
    }

    private List<Double> getGradesMovingAverage(List<Double> grades) {
        return DataSmootherHandler.movingAverage(grades, 10);
    }

    private static List<Map<String, Object>> getPacesAtGradeIntervals(List<Double> pace, List<Double> grades) {
        Map<String, List<Double>> intervals = new HashMap<>();

        int size = Math.min(pace.size(), grades.size());
        for (int i = 0; i < size; i++) {
            double grade = grades.get(i);
            double paceValue = pace.get(i);

            if (paceValue != 0 && !Double.isNaN(grade) && !Double.isInfinite(grade)) {
                String interval = getGradeInterval(grade);
                intervals.computeIfAbsent(interval, k -> new ArrayList<>()).add(paceValue);
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<Double>> entry : intervals.entrySet()) {
            if (entry.getValue().size() > 10) {
                double averagePace = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                double averageGapPace = averagePace/GapPaceCalculator.calculateGapPaceMultiplier(extractLowerBound(entry.getKey()) + 2.5) ;
                Map<String, Object> row = new HashMap<>();
                row.put("interval", entry.getKey());
                row.put("averagePace", averagePace);
                row.put("averageGapPace",averageGapPace);
                result.add(row);
            }
        }

        result.sort(Comparator.comparingDouble(e -> extractLowerBound((String) e.get("interval"))));

        return result;
    }

    private static String getGradeInterval(double grade) {
        double intervalSize = 5.0;
        double shift = 2.5;
        double lowerBound = Math.floor((grade - shift) / intervalSize) * intervalSize + shift;
        double upperBound = lowerBound + intervalSize;

        return String.format("%.1f%% to %.1f%%", lowerBound, upperBound);
    }

    private static double extractLowerBound(String interval) {
        String lower = interval.split(" ")[0].replace("%", "").replace(",", ".");
        return Double.parseDouble(lower);
    }
}
