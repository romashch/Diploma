package com.shchelokov.diploma.models;

import java.time.ZonedDateTime;

import static java.lang.Math.*;

public class Point {

    private double lat;
    private double lon;
    private ZonedDateTime time;
    private double ele;

    private static final double RADIUS_OF_EARTH = 6371;

    public Point() {

    }

    public Point(double lat, double lon) {
        this.lat = toRadians(lat);
        this.lon = toRadians(lon);
    }

    public Point(double lat, double lon, double ele) {
        this.lat = toRadians(lat);
        this.lon = toRadians(lon);
        this.ele = ele;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public double getEle() {
        return ele;
    }

    public void setLat(double lat) {
        this.lat = toRadians(lat);
    }

    public void setLon(double lon) {
        this.lon = toRadians(lon);
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public void setEle(double ele) {
        this.ele = ele;
    }

    public double distance(Point p2) {
        double a = sin(lat) * sin(p2.lat) + cos(lat) * cos(p2.lat) * cos(p2.lon - lon);
        if (a > 1) a = 1;
        if (a < -1) a = -1;
        return RADIUS_OF_EARTH * acos(a);
    }

    /*public double distanceInRad(Point p2) {
        double a = sin(lat) * sin(p2.lat) + cos(lat) * cos(p2.lat) * cos(p2.lon - lon);
        if (a > 1) a = 1;
        if (a < -1) a = -1;
        return acos(a);
    }

    public static double angle(Point p, Point p1, Point p2) {
        double a = p1.distanceInRad(p2);
        double b = p.distanceInRad(p1);
        double c = p.distanceInRad(p2);
        return acos((cos(a) - cos(b) * cos(c)) / (sin(b) * sin(c)));
    }

    public static boolean isAcuteAngle(double angle) {
        return cos(angle) > 0;
    }

    public double distanceToSegment(Point e1, Point e2) {
        if (isAcuteAngle(angle(e1, this, e2)) && isAcuteAngle(angle(e2, this, e1))) {
            return RADIUS_OF_EARTH * distanceInRad(e1) * angle(e1, this, e2);
        } else if (!isAcuteAngle(angle(e1, this, e2))) {
            return distance(e1);
        } else {
            return distance(e2);
        }
    }*/

    /*public Point nearestPointOnSegment(Point e1, Point e2) {
        if (isAcuteAngle(angle(e1, this, e2)) && isAcuteAngle(angle(e2, this, e1))) {
            double e1lon = e1.lon / cos(e1.lat);
            double e2lon = e2.lon / cos(e2.lat);
            double denom = pow(e2.lat - e1.lat, 2) + pow(e2lon - e1lon, 2);
            double resLon = ((e2lon - e1lon) * ((e2lon - e1lon) * lon - (e1.lat - e2.lat) * lat) - (e1.lat - e2.lat) * (e1lon * e2.lat - e1.lat * e2lon)) / denom;
            double resLat = ((e1.lat - e2.lat) * (-(e2lon - e1lon) * lon + (e1.lat - e2.lat) * lat) - (e2lon - e1lon) * (e1lon * e2.lat - e1.lat * e2lon)) / denom;
            resLon *= cos(resLat);
            return new Point(toDegrees(resLat), toDegrees(resLon), e1.ele);
        } else if (!isAcuteAngle(angle(e1, this, e2))) {
            return e1;
        } else {
            return e2;
        }
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.lat, lat) == 0 && Double.compare(point.lon, lon) == 0;
    }
}
