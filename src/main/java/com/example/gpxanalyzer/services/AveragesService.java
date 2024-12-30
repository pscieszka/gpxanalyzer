//package com.example.gpxanalyzer.services;
//
//import com.example.gpxanalyzer.DataModels.ParsedData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AveragesService {
//    public static double getAverageGapPace(List<Double> gapPace) {
//        double sum = 0;
//        for (int i = 0; i < gapPace.size(); i++) {
//            sum += gapPace.get(i);
//        }
//        return sum / gapPace.size();
//    }
//
//    public static List<Double> getAverageGapPacePerKm(ParsedData data){
//        List<Double> gapPacePerKm = new ArrayList<>();
//        List<Double> gapPace = GapPaceService.handle(data.getPace(), data.getGrade());
//        List<Double> distance = data.getDistanceList();
//        double sum = 0;
//        int count = 0;
//        double distanceSum = 0;
//        for (int i = 0; i < gapPace.size(); i++) {
//            sum += gapPace.get(i);
//            count++;
//            distanceSum += distance.get(i);
//            if(distanceSum >=1000){
//                gapPacePerKm.add(sum/count);
//                sum = 0;
//                count = 0;
//                distanceSum = 0;
//            }
//        }
//        return gapPacePerKm;
//    }
//
//}
