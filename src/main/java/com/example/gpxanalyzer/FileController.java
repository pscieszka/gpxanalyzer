package com.example.gpxanalyzer;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.FileTypesStrategies.FileParser;
import com.example.gpxanalyzer.FileTypesStrategies.FileParserFactory;
import com.example.gpxanalyzer.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Controller
public class FileController {


    @PostMapping("/upload")
    public String uploadGpxFile(@RequestParam("file") MultipartFile file, Model model) throws IOException, ParserConfigurationException, SAXException {

        String filename = file.getOriginalFilename();

        FileParser strategy = FileParserFactory.getParser(filename);
        ParsedData data = processData(strategy.parseFile(file.getInputStream()));



        addAtributes(model, data);
        Logger logger = LoggerFactory.getLogger(FileController.class);
        logger.info("total dystans: " + data.getTotalDistance());

        return "map";
    }

    private void addAtributes(Model model, ParsedData data){
        //info
        model.addAttribute("totalTimeInString", data.getTotalTimeInString());
        model.addAttribute("totalDistance", data.getTotalDistanceInKm());
        model.addAttribute("elevationGain", data.getElevationGain());
        model.addAttribute("averageHeartRate", data.getAverageHeartRate());
        model.addAttribute("averagePace", data.getAveragePace());
        model.addAttribute("averageGapPace", data.getAverageGapPace());
        //table
//        model.addAttribute("averageElevationGainPerKm", data.getElevationGainPerKm());
//        model.addAttribute("pacePerKm", data.getPacePerKm());
//        model.addAttribute("gapPacePerKm", data.getGapPacePerKm());
//        model.addAttribute("heartRatePerKm", data.getHeartRatePerKm());

        model.addAttribute("pacePerKm", data.getPacePerKm());
        model.addAttribute("gapPacePerKm", data.getGapPacePerKm());
        model.addAttribute("heartRatePerKm", data.getHeartRatePerKm());
        model.addAttribute("averageElevationGainPerKm", data.getElevationGainPerKm());

        model.addAttribute("coordinates", data.getCoordinates());

//        model.addAttribute("coordinates", data.getCoordinates());
//        model.addAttribute("heartRate", data.getHeartRates());
//        model.addAttribute("elevation", data.getElevation());
//        model.addAttribute("time", data.getTotalTimeInSeconds());
//        model.addAttribute("paceData", data.getPace());
//        model.addAttribute("grade",data.getGrade());
//        model.addAttribute("minGrade", data.getMinGrade(data.getGrade()));
//        model.addAttribute("maxGrade", data.getMaxGrade(data.getGrade()));
//        model.addAttribute("paceAtGrades", data.getPaceAtGrades());
//        model.addAttribute("paceForKm", data.getPaceForKm());
//        model.addAttribute("gapPace", data.getGapPace());
//        model.addAttribute("MApace", MovingAvgService.movingAverage(data.getPace(),10));
//        model.addAttribute("MAGapPace", MovingAvgService.movingAverage(data.getGapPace(),10));
//        model.addAttribute("avgPace", AveragesService.getAveragePace(data.getPace()));
//        model.addAttribute("avgGapPace", AveragesService.getAverageGapPace(data.getGapPace()));
//        model.addAttribute("avgHeartRate", AveragesService.getAverageHeartRate(data.getHeartRates()));
//        model.addAttribute("avgGapPacePerKm", AveragesService.getAverageGapPacePerKm(data));
    }
    private ParsedData processData(ParsedData data){
        AnalysisComponent distanceService = new DistanceService();
        AnalysisComponent timeService = new TimeService();
        AnalysisComponent elevationGainService = new ElevationGainService();
        AnalysisComponent heartRateService = new HeartRateService();
        AnalysisComponent averagePaceService = new AveragePaceService();
        AnalysisComponent averageGapPaceService = new AverageGapPaceService();

        AnalysisProcessor processor = AnalysisProcessor.getInstance();

        processor.addComponent(distanceService);
        processor.addComponent(timeService);
        processor.addComponent(elevationGainService);
        processor.addComponent(heartRateService);
        processor.addComponent(averagePaceService);
        processor.addComponent(averageGapPaceService);

        processor.process(data);
        //AFTER REDUCING TRACKING POINTS

        return data;
    }

}

