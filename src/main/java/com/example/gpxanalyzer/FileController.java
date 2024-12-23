package com.example.gpxanalyzer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class FileController {

    @PostMapping("/upload")
    public String uploadGpxFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        GpxParser gpxParser = new GpxParser();
        InputStream fileContent = file.getInputStream();

        List<Coordinate> coordinates = gpxParser.parseGpxFile(fileContent);

        List<List<Double>> coordinateData = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            List<Double> coordList = new ArrayList<>();

            coordList.add(coordinate.getLatitude());
            coordList.add(coordinate.getLongitude());

            coordinateData.add(coordList);
        }


        // Przekazanie danych JSON do widoku
        model.addAttribute("coordinates", coordinateData);

        return "map"; // Nazwa szablonu Thymeleaf
    }
}

