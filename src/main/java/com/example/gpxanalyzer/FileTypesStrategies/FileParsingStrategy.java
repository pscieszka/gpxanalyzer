package com.example.gpxanalyzer.FileTypesStrategies;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public interface FileParsingStrategy {
    public Document parseFile(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException;
}
