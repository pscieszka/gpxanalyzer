package com.example.gpxanalyzer.FileTypesStrategies;

import com.example.gpxanalyzer.DataModels.ParsedData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GpxParser implements FileParser {
    @Override
    public ParsedData parseFile(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList trkpts = document.getElementsByTagName("trkpt");
            List<List<Double>> coordinates = new ArrayList<>();
            List<Integer> heartRates = new ArrayList<>();

            for (int i = 0; i < trkpts.getLength(); i++) {
                Element trkpt = (Element) trkpts.item(i);
                double lat = Double.parseDouble(trkpt.getAttribute("lat"));
                double lon = Double.parseDouble(trkpt.getAttribute("lon"));
                NodeList extensions = trkpt.getElementsByTagName("extensions");
                if (extensions.getLength() > 0) {
                    Element extension = (Element) extensions.item(0);
                    NodeList hr = extension.getElementsByTagName("gpxtpx:hr");
                    if (hr.getLength() > 0) {
                        Element hrElement = (Element) hr.item(0);
                        int heartRate = Integer.parseInt(hrElement.getTextContent());
                        heartRates.add(heartRate);
                    }
                }
                List<Double> coordinate = new ArrayList<>();
                coordinate.add(lat);
                coordinate.add(lon);
                coordinates.add(coordinate);
            }

            return new ParsedData(coordinates, heartRates);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}