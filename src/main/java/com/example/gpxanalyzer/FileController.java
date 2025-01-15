package com.example.gpxanalyzer;

import com.example.gpxanalyzer.DataModels.ParsedData;
import com.example.gpxanalyzer.FileTypesStrategies.FileParser;
import com.example.gpxanalyzer.FileTypesStrategies.FileParserFactory;
import com.example.gpxanalyzer.services.FileProcessingService;
import com.example.gpxanalyzer.services.ModelAttributeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class FileController {
    private final FileProcessingService fileProcessingService;
    private final ModelAttributeService modelAttributeService;

    private List<ParsedData> parsedDataList = new ArrayList<>();

    public FileController() {
        this.fileProcessingService = new FileProcessingService();
        this.modelAttributeService = new ModelAttributeService();
    }

    @PostMapping("/upload")
    public String uploadGpxFiles(@RequestParam("files") MultipartFile[] files, Model model) throws IOException, ParserConfigurationException, SAXException {
        if (files.length == 0) {
            model.addAttribute("error", "No files uploaded.");
            return "error";
        }

        parsedDataList.clear();

        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();

            FileParser strategy = FileParserFactory.getParser(filename);
            ParsedData data = fileProcessingService.processData(strategy.parseFile(file.getInputStream()));
            data.name = filename;
            parsedDataList.add(data);
        }
        model.addAttribute("activities", parsedDataList);


        return "activityList";
    }
    @GetMapping("/activityList")
    public String getActivityList(Model model) {
        model.addAttribute("activities", parsedDataList);
        return "activityList";
    }

    @GetMapping("/activity/{id}")
    public String viewActivity(@PathVariable("id") int id, Model model) {
        if (id < 0 || id >= parsedDataList.size()) {
            model.addAttribute("error", "Activity not found.");
            return "error";
        }

        ParsedData selectedActivity = parsedDataList.get(id);

        modelAttributeService.addAttributes(model, selectedActivity);

        return "map";
    }
}


