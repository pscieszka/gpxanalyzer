package com.example.gpxanalyzer;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.FileTypesStrategies.FileParser;
import com.example.gpxanalyzer.FileTypesStrategies.FileParserFactory;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;
import com.example.gpxanalyzer.services.Common.AnalysisProcessor;
import com.example.gpxanalyzer.services.afterDecrease.*;
import com.example.gpxanalyzer.services.beforeDecrease.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


@Controller
public class FileController {


    @PostMapping("/upload")
    public String uploadGpxFile(@RequestParam("file") MultipartFile file, Model model) throws IOException, ParserConfigurationException, SAXException {

        String filename = file.getOriginalFilename();

        FileParser strategy = FileParserFactory.getParser(filename);
        ParsedData data = processData(strategy.parseFile(file.getInputStream()));

        addAtributes(model, data);

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
        model.addAttribute("pacePerKm", data.getPacePerKm());
        model.addAttribute("gapPacePerKm", data.getGapPacePerKm());
        model.addAttribute("heartRatePerKm", data.getHeartRatePerKm());
        model.addAttribute("averageElevationGainPerKm", data.getElevationGainPerKm());
        //map
        model.addAttribute("coordinates", data.getCoordinates());
        //chart
        model.addAttribute("paceData", data.getPaceChart());
        model.addAttribute("HrData",data.getHrChart());
        model.addAttribute("elevationData", data.getElevationRaw());
        model.addAttribute("gapPace", data.getGapPaceChart());
        model.addAttribute("gradeChart", data.getGradeChartMovingAverage());
        //table
        model.addAttribute("pacesAtGrades", data.getPacesAtGrades());
        //ratio
        model.addAttribute("hrPaceRatios", data.getHrPaceRatios());
        model.addAttribute("effortScore", data.getEffortScore());

        model.addAttribute("hrAtPaces", data.getHrAtPaces());

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
        data.getDataInIntervals();
        processor.clearComponents();
        AnalysisComponent paceChartService = new PaceChartService();
        AnalysisComponent HeartRateChartService = new HeartRateChartService();
        AnalysisComponent gradeChartService = new GradeService();
        AnalysisComponent gapPaceChartService = new GapPaceChartService();
        AnalysisComponent HrPaceRatioService = new HrPaceRatioService();
        AnalysisComponent HrAtPaceService = new AverageHrAtPacesService();



        processor.addComponent(distanceService);
        processor.addComponent(paceChartService);
        processor.addComponent(HeartRateChartService);
        processor.addComponent(gradeChartService);
        processor.addComponent(elevationGainService);
        processor.addComponent(gapPaceChartService);
        processor.addComponent(HrPaceRatioService);
        processor.addComponent(HrAtPaceService);


        processor.process(data);


        return data;
    }

}

