package com.example.gpxanalyzer.services;

import java.util.ArrayList;
import java.util.List;

public class DataSmootherHandler {
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

    public static List<Double> interpolateInvalids(List<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            Double value = data.get(i);

            if (value == 0 || value.isNaN() || value.isInfinite()) {
                int prev = i - 1;
                int next = i + 1;

                while (prev >= 0 && (data.get(prev) == 0 || data.get(prev).isNaN() || data.get(prev).isInfinite())) {
                    prev--;
                }
                while (next < data.size() && (data.get(next) == 0 || data.get(next).isNaN() || data.get(next).isInfinite())) {
                    next++;
                }

                double interpolatedValue = 0;
                if (prev >= 0 && next < data.size()) {
                    interpolatedValue = (data.get(prev) + data.get(next)) / 2;
                } else if (prev >= 0) {
                    interpolatedValue = data.get(prev);
                } else if (next < data.size()) {
                    interpolatedValue = data.get(next);
                }

                data.set(i, interpolatedValue);
            }
        }
        return data;
    }




}
