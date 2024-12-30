package com.example.gpxanalyzer.services;

import java.util.ArrayList;
import java.util.List;

public class DataSmootherService {
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
    public static List<Double> clipOutliers(List<Double> data, double threshold) {
        List<Double> smoothed = movingAverage(data, 5);  // użyj MA
        for (int i = 0; i < data.size(); i++) {
            if (Math.abs(data.get(i) - smoothed.get(i)) > threshold) {
                data.set(i, smoothed.get(i));
            }
        }
        return data;
    }
    public static List<Double> applyLinearTrend(List<Double> data) {
        int n = data.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += i;
            sumY += data.get(i);
            sumXY += i * data.get(i);
            sumX2 += i * i;
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        List<Double> trend = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            trend.add(slope * i + intercept);
        }
        return trend;
    }
    public static List<Double> smoothAndRemoveOutliers(List<Double> data, int windowSize, double threshold) {
        List<Double> interpolated = interpolateInvalids(data);  // Najpierw interpolacja
        List<Double> smoothed = new ArrayList<>();

        data.removeIf(n->n == 0.0);

        double mean = interpolated.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double stdDev = Math.sqrt(interpolated.stream()
                .mapToDouble(v -> Math.pow(v - mean, 2))
                .average().orElse(0));

        for (int i = 0; i < interpolated.size(); i++) {
            double value = interpolated.get(i);
            if (Math.abs(value - mean) > threshold * stdDev) {
                interpolated.set(i, mean);  // Zamień outlier na średnią
            }
        }

        // 2. Średnia krocząca
        for (int i = 0; i < interpolated.size(); i++) {
            int start = Math.max(0, i - windowSize / 2);
            int end = Math.min(interpolated.size() - 1, i + windowSize / 2);

            double sum = 0;
            int count = 0;
            for (int j = start; j <= end; j++) {
                sum += interpolated.get(j);
                count++;
            }
            smoothed.add(sum / count);
        }

        return smoothed;
    }



}
