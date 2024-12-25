package com.example.gpxanalyzer.FileTypesStrategies;

import com.example.gpxanalyzer.DataModels.ParsedData;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public interface FileParser {
    public ParsedData parseFile(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException;
}
