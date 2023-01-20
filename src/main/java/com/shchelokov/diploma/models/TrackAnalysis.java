package com.shchelokov.diploma.models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrackAnalysis {

    private final File file;
    private final String extension;

    public TrackAnalysis(File file, String extension) {
        this.file = file;
        this.extension = extension;
    }

    public TrackAnalysisReport trackAnalysis() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        if (extension.equals("gpx")) {
            GPXHandler handler = new GPXHandler();
            parser.parse(file, handler);
            return handler.getReport();
        }
        else if (extension.equals("kml")) {
            KMLHandler handler = new KMLHandler();
            parser.parse(file, handler);
            return handler.getReport();
        } else {
            return null;
        }
    }


    private static class KMLHandler extends DefaultHandler {
        TrackAnalysisReport report;

        static final String COORDINATES_TAG = "coordinates";

        List<List<Point>> points;
        List<Point> currentSeg;
        String currentTag;

        @Override
        public void startDocument() throws SAXException {
            points = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentTag = qName;
            if (qName.equals(COORDINATES_TAG)) {
                currentSeg = new ArrayList<>();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length);
            if (currentTag == null || currentSeg == null) {
                return;
            }
            if (currentTag.equals(COORDINATES_TAG)) {
                String[] currentSeg0 = value.split("\n");
                for (String s : currentSeg0) {
                    s = s.replaceAll("\t", "");
                    if (s.equals("")) continue;
                    int index = s.indexOf(",");
                    double lon = Double.parseDouble(s.substring(0, index));
                    double lat = Double.parseDouble(s.substring(index + 1));
                    currentSeg.add(new Point(lat, lon));
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            currentTag = null;
            if (qName.equals(COORDINATES_TAG)) {
                points.add(currentSeg);
                currentSeg = null;
            }
        }

        @Override
        public void endDocument() throws SAXException {
            try {
                report = new TrackAnalysisReport(points, TrackAnalysisReport.Extension.KML);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public TrackAnalysisReport getReport() {
            return report;
        }
    }

    private static class GPXHandler extends DefaultHandler {
        TrackAnalysisReport report;

        static final String TRKSEG_TAG = "trkseg";
        static final String TRKPT_TAG = "trkpt";
        static final String LAT_ATTRIBUTE = "lat";
        static final String LON_ATTRIBUTE = "lon";
        static final String ELE_TAG = "ele";
        static final String TIME_TAG = "time";

        List<List<Point>> points;
        List<Point> currentTrkseg;
        Point currentTrkpt;
        String currentTag;

        boolean isT = false;
        boolean isH = false;

        @Override
        public void startDocument() throws SAXException {
            points = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentTag = qName;
            switch (qName) {
                case TRKSEG_TAG: {
                    currentTrkseg = new ArrayList<>();
                    break;
                }
                case TRKPT_TAG: {
                    currentTrkpt = new Point();
                    currentTrkpt.setLat(Double.parseDouble(attributes.getValue(LAT_ATTRIBUTE)));
                    currentTrkpt.setLon(Double.parseDouble(attributes.getValue(LON_ATTRIBUTE)));
                    break;
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length);
            if (currentTag == null || currentTrkpt == null) {
                return;
            }
            switch (currentTag) {
                case ELE_TAG: {
                    isH = true;
                    currentTrkpt.setEle(Double.parseDouble(value));
                    break;
                }
                case TIME_TAG: {
                    isT = !value.equals("1970-01-01T00:00:01.000Z");
                    currentTrkpt.setTime(ZonedDateTime.parse(value));
                    break;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            currentTag = null;
            switch (qName) {
                case TRKSEG_TAG: {
                    points.add(currentTrkseg);
                    currentTrkseg = null;
                    break;
                }
                case TRKPT_TAG: {
                    currentTrkseg.add(currentTrkpt);
                    currentTrkpt = null;
                    break;
                }
            }
        }

        @Override
        public void endDocument() throws SAXException {
            try {
                if (isT && isH) {
                    report = new TrackAnalysisReport(points, TrackAnalysisReport.Extension.GPX);
                } else if (!isT && isH) {
                    report = new TrackAnalysisReport(points, TrackAnalysisReport.Extension.GPXWT);
                } else if (isT && !isH) {
                    report = new TrackAnalysisReport(points, TrackAnalysisReport.Extension.GPXWH);
                } else {
                    report = new TrackAnalysisReport(points, TrackAnalysisReport.Extension.KML);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public TrackAnalysisReport getReport() {
            return report;
        }
    }
}
