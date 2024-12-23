package com.example.gpxanalyzer;

import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class GpxParser {

    public List<Coordinate> parseGpxFile(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList trkptNodes = document.getElementsByTagName("trkpt");
            List<Coordinate> coordinates = new ArrayList<>();

            for (int i = 0; i < trkptNodes.getLength(); i++) {
                Node trkptNode = trkptNodes.item(i);
                NamedNodeMap attributes = trkptNode.getAttributes();
                Node latNode = attributes.getNamedItem("lat");
                Node lonNode = attributes.getNamedItem("lon");

                double latitude = Double.parseDouble(latNode.getNodeValue());
                double longitude = Double.parseDouble(lonNode.getNodeValue());

                coordinates.add(new Coordinate(latitude, longitude));
            }

            return coordinates;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
