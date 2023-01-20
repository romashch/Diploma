package com.shchelokov.diploma.controllers;

import com.shchelokov.diploma.models.Point;
import com.shchelokov.diploma.models.TrackAnalysis;
import com.shchelokov.diploma.models.TrackAnalysisReport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TrackController {
    private static File uploadedFile;
    private static String extension;

    private static final String GPX_PATH = "file.gpx";
    private static final String KML_PATH = "file.kml";

    @GetMapping("/track")
    public String track(Model model) {
        model.addAttribute("extension", "");
        return "track";
    }

    @PostMapping(value = "/track")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String attractions, Model model) {
        String msg;
        model.addAttribute("attractions", attractions);
        if (!file.isEmpty() || uploadedFile != null) {
            try {
                if (!file.isEmpty()) {
                    String ext = getFileExtension(file);
                    if (ext.equals("gpx")) {
                        extension = ext;
                        uploadedFile = new File(GPX_PATH);
                        writer(new String(file.getBytes(), StandardCharsets.UTF_8), GPX_PATH);
                    }
                    else if (ext.equals("kml")) {
                        extension = ext;
                        uploadedFile = new File(KML_PATH);
                        writer(new String(file.getBytes(), StandardCharsets.UTF_8), KML_PATH);
                    }
                    else {
                        msg = "Произошла ошибка. Недопустимое расширение файла.";
                        model.addAttribute("error", msg);
                        return "track";
                    }
                }

                TrackAnalysis trackAnalysis = new TrackAnalysis(uploadedFile, extension);
                List<TrackAnalysisReport> reports = new ArrayList<>();

                TrackAnalysisReport report = trackAnalysis.trackAnalysis();
                if (report.getExtension() == TrackAnalysisReport.Extension.GPX) {
                    model.addAttribute("extension", "gpx");
                }
                else if (report.getExtension() == TrackAnalysisReport.Extension.GPXWT) {
                    model.addAttribute("extension", "gpxwt");
                }
                else if (report.getExtension() == TrackAnalysisReport.Extension.GPXWH) {
                    model.addAttribute("extension", "gpxwh");
                }
                else {
                    model.addAttribute("extension", "kml");
                }
                report.setDescription("ВСЕГО");
                reports.add(report);
                if (report.isManySections()) {
                    reports.addAll(report.trackSections());
                }

                model.addAttribute("error", "");

                List<Point> attractions2;
                try {
                    attractions2 = attractionsParse(attractions);
                    List<TrackAnalysisReport> r = report.trackSplit(attractions2);
                    reports.addAll(r);
                } catch (Exception e) {
                    msg = "Некорректно введены промежуточные точки.";
                    model.addAttribute("error", msg);
                }

                model.addAttribute("tracks", reports);

                return "track";
            } catch (Exception e) {
                msg = "При анализе трека произошла ошибка.";
                model.addAttribute("error", msg);
                return "track";
            }
        } else {
            msg = "Произошла ошибка. Файл пуст.";
            model.addAttribute("error", msg);
            return "track";
        }
    }

    private static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    private void writer(String fileStr, String path) {
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(fileStr);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private List<Point> attractionsParse(String attractions) {
        List<Point> res = new ArrayList<>();
        if (attractions != null && !attractions.equals("")) {
            String[] points0 = attractions.split(";");
            for (String s : points0) {
                int index = s.indexOf(",");
                double lat = Double.parseDouble(s.substring(0, index).trim());
                double lon = Double.parseDouble(s.substring(index + 1).trim());
                res.add(new Point(lat, lon));
            }
        }
        return res;
    }
}