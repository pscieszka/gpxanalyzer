package com.example.gpxanalyzer.FileTypesStrategies;

import com.example.gpxanalyzer.DataModels.ParsedData;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class TcxParser implements FileParser {
    @Override
    public ParsedData parseFile(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return new ParsedData();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
