package com.example.gpxanalyzer.FileTypesStrategies;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class GpxParsingStrategy implements FileParsingStrategy {
    @Override
    public Document parseFile(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
