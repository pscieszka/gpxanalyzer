package com.example.gpxanalyzer.services.afterDecrease;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;

import java.util.*;
import java.util.logging.Logger;

public class AverageHrAtPacesService implements AnalysisComponent {
    private static final Logger LOGGER = Logger.getLogger("PaceAnalysisLogger");

    @Override
    public void process(ParsedData data) {
        data.setHrAtPaces(getAverageHrAtPaces(data));
    }
    private Map<String, String> getAverageHrAtPaces(ParsedData data) {
        Map<String, String> hrAtPaces = new HashMap<>();
        List<Double> gapPaceData = data.getGapPaceChart();
        List<Integer> heartRateData = data.getHeartRates();


        double minPace = getMinPace(gapPaceData);
        double maxPace = getMaxPace(gapPaceData);


        Map<String, Integer> paceCounts = new HashMap<>();
        Map<String, Integer> paceHeartRateSums = new HashMap<>();


        for (double pace = maxPace; pace <= minPace; pace += 0.25) {
            String interval = String.format("%.2f - %.2f", pace, pace + 0.25);
            paceCounts.put(interval, 0);
            paceHeartRateSums.put(interval, 0);
        }

        for (int i = 0; i < gapPaceData.size(); i++) {
            if(heartRateData.size() <= i){
                break;
            }
            double pace = gapPaceData.get(i);
            int heartRate = heartRateData.get(i);
            for (double start = maxPace; start <= minPace; start += 0.25) {
                double end = start + 0.25;
                if (pace >= start && pace < end) {
                    String interval = String.format("%.2f - %.2f", start, end);
                    paceCounts.put(interval, paceCounts.get(interval) + 1);
                    paceHeartRateSums.put(interval, paceHeartRateSums.get(interval) + heartRate);
                    break;
                }
            }
        }
        Map<String, Double> paceAvgHeartRates = new HashMap<>();
        for (String interval : paceCounts.keySet()) {
            int count = paceCounts.get(interval);
            int sum = paceHeartRateSums.get(interval);
            double average = (count > 0) ? (double) sum / count : 0.0;
            paceAvgHeartRates.put(interval, average);
        }

        List<String> topIntervals = paceCounts.entrySet().stream()
                .filter(entry -> entry.getValue() >= 20)
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(20)
                .map(Map.Entry::getKey)
                .toList();

        for (String interval : topIntervals) {
            String[] parts = interval.split(" - ");
            parts[0] = parts[0].replace(',', '.');
            parts[1] = parts[1].replace(',', '.');
            double start = Double.parseDouble(parts[0]);
            double end = Double.parseDouble(parts[1]);
            String startFormatted = formatToMinutesSeconds(start);
            String endFormatted = formatToMinutesSeconds(end);
            String formattedInterval = String.format("%s - %s", startFormatted, endFormatted);
            hrAtPaces.put(formattedInterval, String.valueOf((int) Math.round(paceAvgHeartRates.get(interval))));
        }

        return hrAtPaces.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    String start1 = entry1.getKey().split(" - ")[0];
                    String start2 = entry2.getKey().split(" - ")[0];

                    int time1 = convertMinutesSecondsToSeconds(start1);
                    int time2 = convertMinutesSecondsToSeconds(start2);

                    return Integer.compare(time1, time2);
                })
                .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);
    }


    private Double getMinPace(List<Double> gapPaceData) {
        double minPace = gapPaceData.stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0);
        return roundUpToNearestQuarter(minPace, "max");
    }
    private Double getMaxPace(List<Double> gapPaceData) {
        double minPace = gapPaceData.stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0);
        return roundUpToNearestQuarter(minPace, "min");
    }
    private double roundUpToNearestQuarter(double pace, String type) {
        return Objects.equals(type, "min") ? Math.ceil(pace * 4) / 4.0 : Math.floor(pace * 4) / 4.0;
    }
    private String formatToMinutesSeconds(double pace) {
        int minutes = (int) Math.floor(pace);
        int seconds = (int) Math.round((pace - minutes) * 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
    private int convertMinutesSecondsToSeconds(String time) {
        String[] parts = time.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        return minutes * 60 + seconds;
    }
}
