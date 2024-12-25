package com.example.gpxanalyzer;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.FileTypesStrategies.FileParser;
import com.example.gpxanalyzer.FileTypesStrategies.FileParserFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
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

        FileParser strategy = FileParserFactory.getParser(filename);
        ParsedData data = strategy.parseFile(file.getInputStream());


        model.addAttribute("coordinates", data.getCoordinates());
        model.addAttribute("heartRate", data.getHeartRates());

        return "map"; // Nazwa szablonu Thymeleaf
    }
}

