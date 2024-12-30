package com.example.gpxanalyzer.services;

import java.util.ArrayList;
import java.util.List;

public class MovingAvgService {
    public static List<Double> movingAverage(List<Double> data, int windowSize) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int start = Math.max(0, i - windowSize / 2);
            int end = Math.min(data.size() - 1, i + windowSize / 2);
            double sum = 0;
            int count = 0;
            for (int j = start; j <= end; j++) {
                sum += data.get(j);
                count++;
            }
            result.add(sum / count);
        }
        return result;
    }
}
