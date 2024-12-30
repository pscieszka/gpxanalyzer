package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.FileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class GradeService {
    public static List<Double> handle(List<Double> elevation, List<Double> distances) {
        List<Double> grades = new ArrayList<>();
        List<Double> tempGrades = new ArrayList<>();

        List<Double> elevationChange = getElevationChange(elevation);
        List<Double> distance = getDistanceChange(distances);

        int size = Math.min(distance.size(), elevationChange.size());

        for (int i = 0; i < size; i++) {
            double gradeValue = (elevationChange.get(i) / distance.get(i)) * 100;
            if (Double.isNaN(gradeValue) || Double.isInfinite(gradeValue)) {
                gradeValue = 0;
            }
            tempGrades.add(gradeValue);
        }

        int count = 0;
        double gradeSum = 0;
        for (int i = 0; i < tempGrades.size(); i++) {
            gradeSum += tempGrades.get(i);
            count++;
            if (count % 10 == 0) {
                for (int j = 0; j < 10; j++) {
                    grades.add(gradeSum / 10);
                }
                gradeSum = 0;
                count = 0;
            }

        }
        return grades;
    }

    public static List<Double> getPaceAtGrades(List<Double> pace, List<Double> grades) {

        List<Double> paceAtGrades = new ArrayList<>();
        int minGrade = (int) getMinGrade(grades);

        int maxGrade = (int) getMaxGrade(grades);

        double[] sums = new double[maxGrade - minGrade + 1];
        int[] counts = new int[maxGrade - minGrade + 1];
        int size = Math.min(pace.size(), grades.size());
        for (int i = 0; i < size; i++) {
            if (pace.get(i) != 0 && !grades.get(i).isNaN() && !grades.get(i).isInfinite()) {
                for (int j = minGrade; j <= maxGrade; j++) {
                    if (grades.get(i) >= (double) j - 0.5 && grades.get(i) < (double) j + 0.5) {
                        sums[j - minGrade] += pace.get(i);
                        counts[j - minGrade]++;
                    }
                }
            }
        }
        for (int i = 0; i < sums.length; i++) {
            if (counts[i] > 20 ) {
                paceAtGrades.add(sums[i] / counts[i]);
            } else {
                paceAtGrades.add(0.0);
            }
        }
       paceAtGrades = DataSmootherService.smoothAndRemoveOutliers(paceAtGrades,8, 2.5);
        return paceAtGrades;
    }


    private static List<Double> getElevationChange(List<Double> elevation) {
        return getDoubles(elevation);
    }

    private static List<Double> getDistanceChange(List<Double> distance) {
        return getDoubles(distance);
    }

    private static List<Double> getDoubles(List<Double> distance) {
        List<Double> distanceChange = new ArrayList<>();
        for (int i = 0; i < distance.size() - 1; i++) {
            distanceChange.add(distance.get(i + 1) - distance.get(i));
        }
        return distanceChange;
    }
    public static double getMinGrade(List<Double> grades){
        return (int) Math.floor(grades.stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0));
    }
    public static double getMaxGrade(List<Double> grades){
        return (int) Math.floor(grades.stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0));
    }
}

