package com.example.gpxanalyzer;

import com.example.gpxanalyzer.DataModels.Coordinate;
import com.example.gpxanalyzer.FileTypesStrategies.FileParsingStrategy;
import com.example.gpxanalyzer.FileTypesStrategies.FileParsingStrategyFactory;
import com.example.gpxanalyzer.FileTypesStrategies.GpxParsingStrategy;
import com.example.gpxanalyzer.ParsingDataState.CoordinateState;
import com.example.gpxanalyzer.ParsingDataState.ParsingState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class FileController {

    @PostMapping("/upload")
    public String uploadGpxFile(@RequestParam("file") MultipartFile file, Model model) throws IOException, ParserConfigurationException, SAXException {

        String filename = file.getOriginalFilename();

        FileParsingStrategy strategy = FileParsingStrategyFactory.getStrategy(filename);
        ParsingState<List<Coordinate>> coordinateState = new CoordinateState();
        InputStream inputStream = file.getInputStream();
        List<Coordinate> coordinateData = coordinateState.handle(strategy.parseFile(inputStream));
        List<List<Double>> coordinates = coordinateData.stream().collect(ArrayList::new, (list, coordinate) -> {
            list.add(Arrays.asList(coordinate.getLatitude(), coordinate.getLongitude()));
        }, ArrayList::addAll);


        // Przekazanie danych JSON do widoku
        model.addAttribute("coordinates", coordinates);

        return "map"; // Nazwa szablonu Thymeleaf
    }
}

