package com.example.gpxanalyzer.services;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.services.Common.AnalysisComponent;
import com.example.gpxanalyzer.services.Common.AnalysisProcessor;
import com.example.gpxanalyzer.services.afterDecrease.*;
import com.example.gpxanalyzer.services.beforeDecrease.*;

public class FileProcessingService {

    public ParsedData processData(ParsedData data) {
        // before decrease
        AnalysisProcessor processor = AnalysisProcessor.getInstance();
        processor.addComponent(new DistanceService());
        processor.addComponent(new TimeService());
        processor.addComponent(new ElevationGainService());
        processor.addComponent(new HeartRateService());
        processor.addComponent(new AveragePaceService());
        processor.addComponent(new AverageGapPaceService());

        processor.process(data);

        // reduce
        data.getDataInIntervals();

        // after decrease
        processor.clearComponents();
        processor.addComponent(new DistanceService());
        processor.addComponent(new PaceChartService());
        processor.addComponent(new HeartRateChartService());
        processor.addComponent(new GradeService());
        processor.addComponent(new ElevationGainService());
        processor.addComponent(new GapPaceChartService());
        processor.addComponent(new HrPaceRatioService());
        processor.addComponent(new AverageHrAtPacesService());
        processor.addComponent(new FastestTimesService());

        processor.process(data);

        return data;
    }
}
