package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.LatLonPair;
import com.matching.segmentsmatching.resources.SegmentParsed;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLNomiResponseParser {

    public List<SegmentParsed> parseXMLNomiResponse(String response)
            throws XMLStreamException, XMLParseException {

        List<String> namesS = new ArrayList<>();
        List<String> segmentsS = new ArrayList<>();

        XMLInputFactory f = XMLInputFactory.newInstance();
        XMLStreamReader r = f.createXMLStreamReader(new StringReader(response));
        while (r.hasNext()) {
            r.next();
            if (r.getEventType() == XMLStreamReader.START_ELEMENT) {
                String localName = r.getLocalName();
                if (localName.equalsIgnoreCase("name")) {
                    String text = r.getElementText();
                    if (!text.trim().isEmpty()) {
                        namesS.add(text);
                    } else namesS.add("Segment");
                } else if (localName.equalsIgnoreCase("coordinates")) {
                    String text = r.getElementText();
                    if (!text.trim().isEmpty()) segmentsS.add(text);
                    else throw new XMLParseException("empty coordinates tag");
                }
            }
        }
        if (segmentsS.size() != namesS.size())
            throw new XMLParseException("name and location elements do not match: names.size(): "
                    + namesS.size() + ", segments.size(): " + segmentsS.size());
        List<SegmentParsed> segmentsData = new ArrayList<>();
        int count = 0;
        for (String segmentS : segmentsS) {
            String[] lines = segmentS.split("\n");
            List<LatLonPair> locations = new ArrayList<>();
            for (String line : lines) {
                String[] elements = line.split(",");
                if (elements.length == 3) {
                    try {
                        LatLonPair l = new LatLonPair(Double.valueOf(elements[1].trim()),
                                Double.valueOf(elements[0].trim()));
                        locations.add(l);
                    } catch (Exception e) {
                        throw new XMLParseException("error parsing coordinates");
                    }
                }
            }
            if (!locations.isEmpty()) {
                segmentsData.add(new SegmentParsed(locations, namesS.get(count)));
            } else throw new XMLParseException("empty list of locations");
            count++;
        }
        return segmentsData;
    }
}
