package com.example.gpxanalyzer.ParsingDataState;

import com.example.gpxanalyzer.DataModels.Coordinate;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class CoordinateState implements ParsingState<List<Coordinate>> {
    @Override
    public List<Coordinate> handle(Document data) {
        NodeList trkpt = data.getElementsByTagName("trkpt");
        List<Coordinate> coordinates = new ArrayList<>();
        for(int i = 0; i < trkpt.getLength(); i++) {
            Node trkptNode = trkpt.item(i);
            NamedNodeMap attributes = trkptNode.getAttributes();
            Node latNode = attributes.getNamedItem("lat");
            Node lonNode = attributes.getNamedItem("lon");

            double latitude = Double.parseDouble(latNode.getNodeValue());
            double longitude = Double.parseDouble(lonNode.getNodeValue());

            coordinates.add(new Coordinate(latitude, longitude));
        }
        return coordinates;
    }
}
