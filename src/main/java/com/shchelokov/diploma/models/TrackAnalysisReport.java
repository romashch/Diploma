package com.shchelokov.diploma.models;

import java.time.Duration;
import java.util.*;

public class TrackAnalysisReport {

    public enum Extension {
        GPX,
        GPXWT,
        GPXWH,
        KML
    }

    private final Extension extension;

    private final Point[][] points;

    private double length;
    private Duration totalTime;
    private Duration runningTime;
    private String totalTime2;
    private String runningTime2;
    private int minHeight;
    private int maxHeight;
    private int averageHeight;
    private double totalAverageSpeed;
    private double runningAverageSpeed;
    private int asc;
    private int desc;
    private int elevationChange;

    private String description;

    private static final double MAX_DISTANCE_FOR_SPLIT = 0.1;

    public TrackAnalysisReport(List<List<Point>> points, Extension extension) throws Exception {
        this.extension = extension;
        this.points = new Point[points.size()][];
        for (int i = 0; i < points.size(); i++) {
            this.points[i] = new Point[points.get(i).size()];
            for (int j = 0; j < points.get(i).size(); j++) {
                this.points[i][j] = points.get(i).get(j);
            }
        }
        initProperties();
    }

    public TrackAnalysisReport(Point[] points, Extension extension) throws Exception {
        this.extension = extension;
        this.points = new Point[1][];
        this.points[0] = points;
        initProperties();
    }

    public void initProperties() throws Exception {
        length = length();
        if (extension.equals(Extension.GPX)) {
            totalTime2 = totalTime();
            runningTime2 = runningTime();
            minHeight = minHeight();
            maxHeight = maxHeight();
            averageHeight = averageHeight();
            totalAverageSpeed = totalAverageSpeed();
            runningAverageSpeed = runningAverageSpeed();
            asc = ascent();
            desc = descent();
            elevationChange = elevationChange();
        }
        else if (extension.equals(Extension.GPXWT)) {
            minHeight = minHeight();
            maxHeight = maxHeight();
            averageHeight = averageHeight();
            asc = ascent();
            desc = descent();
            elevationChange = elevationChange();
        }
        else if (extension.equals(Extension.GPXWH)) {
            totalTime2 = totalTime();
            runningTime2 = runningTime();
            totalAverageSpeed = totalAverageSpeed();
            runningAverageSpeed = runningAverageSpeed();
        }
    }

    public boolean isManySections() {
        return points.length > 1;
    }

    public List<TrackAnalysisReport> trackSections() throws Exception {
        List<TrackAnalysisReport> res = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            List<List<Point>> arg = new ArrayList<>();
            List<Point> arg2 = new ArrayList<>(Arrays.asList(points[i]));
            arg.add(arg2);
            TrackAnalysisReport report = new TrackAnalysisReport(arg, extension);
            report.setDescription("С" + (i + 1));
            res.add(report);
        }
        return res;
    }

    /*private Point splitForKml(int bpi, int bpj, Point att) {
        Point p = points[bpi][bpj];
        Point res;
        int newIndex;
        if (bpj == 0) {
            res = att.nearestPointOnSegment(p, points[bpi][bpj + 1]);
            newIndex = bpj;
        }
        else if (bpj == points[bpi].length - 1) {
            res = att.nearestPointOnSegment(p, points[bpi][bpj - 1]);
            newIndex = bpj - 1;
        }
        else if (att.distanceToSegment(p, points[bpi][bpj - 1]) >= att.distanceToSegment(p, points[bpi][bpj + 1])) {
            res = att.nearestPointOnSegment(p, points[bpi][bpj - 1]);
            newIndex = bpj - 1;
        }
        else {
            res = att.nearestPointOnSegment(p, points[bpi][bpj + 1]);
            newIndex = bpj;
        }
        int j = 0;
        while (j < points[bpi].length && !points[bpi][j].equals(res)) {
            j++;
        }
        boolean isTrackPoint = (j < points[bpi].length);
        double d = res.distance(att);
        if (res.distance(att) <= MAX_DISTANCE_FOR_SPLIT && !isTrackPoint) {
            Point[] newPoints = new Point[points[bpi].length + 1];
            System.arraycopy(points[bpi], 0, newPoints, 0, newIndex);
            newPoints[newIndex] = res;
            System.arraycopy(points[bpi], newIndex, newPoints, newIndex + 1, points[bpi].length - newIndex);
            points[bpi] = newPoints;
        }
        return res;
    }*/

    public List<TrackAnalysisReport> trackSplit(List<Point> attractions) throws Exception {
        List<TrackAnalysisReport> res = new ArrayList<>();
        if (attractions != null && attractions.size() > 0) {
            Map<Integer, Point> borders = new HashMap();
            for (int k = 0; k < attractions.size(); k++) {
                double distance = Double.MAX_VALUE;
                int bpi = 0, bpj = 0;
                for (int i = 0; i < points.length; i++) {
                    for (int j = 0; j < points[i].length; j++) {
                        double d = points[i][j].distance(attractions.get(k));
                        if (d < distance) {
                            distance = d;
                            bpj = j;
                            bpi = i;
                        }
                    }
                }
                /*if (extension == Extension.KML || extension == Extension.GPXWT) {
                    borders.put(k + 1, splitForKml(bpi, bpj, attractions.get(k)));
                }
                else */
                if (distance <= MAX_DISTANCE_FOR_SPLIT) {
                    borders.put(k + 1, points[bpi][bpj]);
                }
            }

            for (int i = 0; i < points.length; i++) {
                int beginIndex = 0;
                int endIndex = 0;
                int prevPoint = 0;
                boolean isParts = false;
                for (int j = 0; j < points[i].length; j++) {
                    for (Map.Entry<Integer, Point> entry : borders.entrySet()) {
                        if (points[i][j].equals(entry.getValue())) {
                            isParts = true;
                            endIndex = j;
                            Point[] part = new Point[endIndex - beginIndex + 1];
                            System.arraycopy(points[i], beginIndex, part, 0, part.length);
                            TrackAnalysisReport report = new TrackAnalysisReport(part, extension);
                            if (beginIndex == 0) {
                                report.setDescription("нС" + (i + 1) + " - " + "т" + entry.getKey());
                            } else {
                                report.setDescription("т" + prevPoint + " - " + "т" + entry.getKey());
                            }
                            res.add(report);
                            beginIndex = j;
                            prevPoint = entry.getKey();
                        }
                    }
                }
                if (isParts) {
                    Point[] part = new Point[points[i].length - 1 - beginIndex + 1];
                    System.arraycopy(points[i], beginIndex, part, 0, part.length);
                    TrackAnalysisReport report = new TrackAnalysisReport(part, extension);
                    report.setDescription("т" + prevPoint + " - " + "кС" + (i + 1));
                    res.add(report);
                }
            }
        }
        return res;
    }

    public double length() {
        double res = 0;
        for (Point[] point : points) {
            for (int j = 0; j < point.length - 1; j++) {
                res += point[j].distance(point[j + 1]);
            }
        }
        return res;
    }

    public String totalTime() {
        Duration res0 = Duration.ZERO;
        for (Point[] point : points) {
            res0 = res0.plus(Duration.between(point[0].getTime(), point[point.length - 1].getTime()));
        }
        totalTime = res0;
        return String.format("%d:%02d:%02d",
                res0.toHours(),
                res0.toMinutesPart(),
                res0.toSecondsPart());
    }

    public String runningTime() {
        Duration res0 = Duration.ZERO;
        for (Point[] point : points) {
            for (int i = 0; i < point.length - 1; i++) {
                Duration interval = Duration.between(point[i].getTime(), point[i + 1].getTime());
                if (interval.toSeconds() <= 2) {
                    res0 = res0.plus(interval);
                }
            }
        }
        runningTime = res0;
        return String.format("%d:%02d:%02d",
                res0.toHours(),
                res0.toMinutesPart(),
                res0.toSecondsPart());
    }

    public int minHeight() {
        double res = points[0][0].getEle();
        for (Point[] point : points) {
            for (int j = 0; j < point.length - 1; j++) {
                if (point[j].getEle() < res)
                    res = point[j].getEle();
            }
        }
        return (int) Math.round(res);
    }

    public int maxHeight() {
        double res = points[0][0].getEle();
        for (Point[] point : points) {
            for (int j = 0; j < point.length - 1; j++) {
                if (point[j].getEle() > res)
                    res = point[j].getEle();
            }
        }
        return (int) Math.round(res);
    }

    public int averageHeight() throws Exception {
        double res = 0;
        for (Point[] point : points) {
            for (int j = 0; j < point.length - 1; j++) {
                res += point[j].distance(point[j + 1]) * point[j].getEle();
            }
        }
        if (length != 0)
            return (int) Math.round(res / length);
        else
            throw new Exception();
    }

    public double totalAverageSpeed() {
        return length / totalTime.toSeconds() * 3600;
    }

    public double runningAverageSpeed() {
        return length / runningTime.toSeconds() * 3600;
    }

    public int ascent() {
        double res = 0;
        for (Point[] point : points) {
            for (int j = 0; j < point.length - 1; j++) {
                double dh = point[j + 1].getEle() - point[j].getEle();
                if (dh > 0) {
                    res += dh;
                }
            }
        }
        return (int) Math.round(res);
    }

    public int descent() {
        double res = 0;
        for (Point[] point : points) {
            for (int j = 0; j < point.length - 1; j++) {
                double dh = point[j + 1].getEle() - point[j].getEle();
                if (dh < 0) {
                    res += Math.abs(dh);
                }
            }
        }
        return (int) Math.round(res);
    }

    public int elevationChange() {
        return asc + desc;
    }


    public double getLength() {
        return length;
    }

    public String getTotalTime2() {
        return totalTime2;
    }

    public String getRunningTime2() {
        return runningTime2;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getAverageHeight() {
        return averageHeight;
    }

    public double getTotalAverageSpeed() {
        return totalAverageSpeed;
    }

    public double getRunningAverageSpeed() {
        return runningAverageSpeed;
    }

    public int getAsc() {
        return asc;
    }

    public int getDesc() {
        return desc;
    }

    public int getElevationChange() {
        return elevationChange;
    }

    public String getDescription() {
        return description;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
