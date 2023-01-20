package com.shchelokov.diploma.controllers;

import com.shchelokov.diploma.models.DifficultyCategory;
import com.shchelokov.diploma.models.LP;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("lp", new LP());
        model.addAttribute("areas", DifficultyCategory.getAreas(DifficultyCategory.areas0[0]));
        model.addAttribute("as", DifficultyCategory.getAs(DifficultyCategory.as0[0]));
        return "category";
    }

    @PostMapping("/category")
    public String calcCategory(@RequestParam String l,
                               @RequestParam String t,
                               @ModelAttribute LP lp,
                               @RequestParam String area,
                               @RequestParam String a,
                               @RequestParam String dh,
                               Model model) {
        DifficultyCategory dc;
        model.addAttribute("lp", lp);
        model.addAttribute("areas", DifficultyCategory.getAreas(area));
        model.addAttribute("as", DifficultyCategory.getAs(a));

        model.addAttribute("l", l);
        model.addAttribute("t", t);
        model.addAttribute("dh", dh);
        model.addAttribute("area", area);
        model.addAttribute("a", a);

        try {
            dc = new DifficultyCategory(
                    Double.parseDouble(l),
                    Integer.parseInt(t),
                    lp.getLP(),
                    area,
                    a,
                    Double.parseDouble(dh));
        }
        catch (Exception ex) {
            model.addAttribute("error", "Произошла ошибка. Проверьте корректность введенных данных.");
            return "category";
        }
        try {
            String res = dc.calculationOfDifficultyCategoryOfWalkingRoute();
            model.addAttribute("result", "Категория сложности: " + res);
        }
        catch (Exception ex) {
            model.addAttribute("error", "Произошла ошибка при расчете.");
        }
        return "category";
    }
}
