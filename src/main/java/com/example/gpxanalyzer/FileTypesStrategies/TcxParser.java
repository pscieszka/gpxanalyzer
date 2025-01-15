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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TcxParser implements FileParser {
    @Override
    public ParsedData parseFile(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList trackpoints = document.getElementsByTagName("Trackpoint");
            List<List<Double>> coordinates = new ArrayList<>();
            List<Integer> heartRates = new ArrayList<>();
            List<Double> elevationList = new ArrayList<>();
            List<Integer> times = new ArrayList<>();
            String name = document.getElementsByTagName("Id").item(0).getTextContent();

            for (int i = 0; i < trackpoints.getLength(); i++) {
                Element trackpoint = (Element) trackpoints.item(i);

                Element position = (Element) trackpoint.getElementsByTagName("Position").item(0);
                double lat = Double.parseDouble(position.getElementsByTagName("LatitudeDegrees").item(0).getTextContent());
                double lon = Double.parseDouble(position.getElementsByTagName("LongitudeDegrees").item(0).getTextContent());

                String timeStr = trackpoint.getElementsByTagName("Time").item(0).getTextContent();
                Instant instant = Instant.parse(timeStr);

                double elevation = Double.parseDouble(trackpoint.getElementsByTagName("AltitudeMeters").item(0).getTextContent());

                NodeList hrNodeList = trackpoint.getElementsByTagName("HeartRateBpm");
                if (hrNodeList.getLength() > 0) {
                    Element hrElement = (Element) hrNodeList.item(0);
                    int heartRate = Integer.parseInt(hrElement.getElementsByTagName("Value").item(0).getTextContent());
                    heartRates.add(heartRate);
                } else {
                    heartRates.add(null);
                }

                List<Double> coordinate = new ArrayList<>();
                coordinate.add(lat);
                coordinate.add(lon);
                coordinates.add(coordinate);
                elevationList.add(elevation);

                int time = (int) instant.getEpochSecond();
                times.add(time);
            }

            return new ParsedData(name, coordinates, heartRates, elevationList, times);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
