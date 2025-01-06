package com.example.gpxanalyzer.services.Common;

import java.util.ArrayList;
import java.util.List;

public class DataSmootherHandler {

    public static <T extends Number> List<T> movingAverage(List<T> data, int windowSize) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int start = Math.max(0, i - windowSize / 2);
            int end = Math.min(data.size() - 1, i + windowSize / 2);
            double sum = 0;
            int count = 0;
            for (int j = start; j <= end; j++) {
                sum += data.get(j).doubleValue();
                count++;
            }
            if (data.get(0) instanceof Integer) {
                result.add((T) Integer.valueOf((int) (sum / count)));
            } else {
                result.add((T) Double.valueOf(sum / count));
            }
        }
        return result;
    }
}
