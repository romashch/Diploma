package com.shchelokov.diploma.models;

import java.util.Arrays;
import java.util.HashMap;

public class DifficultyCategory {

    private static final HashMap<String, Double> areasKt = new HashMap<>();
    private static final HashMap<String, Integer> areasG = new HashMap<>();
    private static final HashMap<String, Double> as = new HashMap<>();

    private static final double[][] pointsForLPTable = new double[6][];
    private static final int[][][] maxNumberOfLPTable = new int[6][][];

    private static final int[][] categoryParametersTable = new int[][]{
            // 0 - дней (не менее)
            // 1 - км (не менее)
            // 2 - баллы за ЛП (не более)
            // 3 - баллы за ПП (не более)
            // 4 - общее кол-во баллов (минимальное)
            {6, 100, 10, 12, 7},
            {8, 120, 20, 24, 21},
            {10, 140, 40, 50, 60},
            {13, 170, 60, 70, 95},
            {16, 210, 80, 100, 135},
            {20, 250, 110, 140, 185}
    };

    private double l;
    private int t;
    private int[][] lp;
    private String area;
    private String a;
    private double dh;

    private int indicativeСategory;
    private int finalСategory;

    public static String[] getAreas(String area) {
        String[] res = new String[areas0.length];
        res[0] = area;
        int i = 1;
        for (; !areas0[i - 1].equals(area); i++) {
            res[i] = areas0[i - 1];
        }
        for (; i < res.length; i++) {
            res[i] = areas0[i];
        }
        return res;
    }

    public static String[] getAs(String a) {
        String[] res = new String[as0.length];
        res[0] = a;
        int i = 1;
        for (; !as0[i - 1].equals(a); i++) {
            res[i] = as0[i - 1];
        }
        for (; i < res.length; i++) {
            res[i] = as0[i];
        }
        return res;
    }

    public static void initAreas() {
        if (areasKt.size() == 0) {
            areasKt.put("Кольский полуостров", 0.4);
            areasKt.put("Карелия", 0.3);
            areasKt.put("Архангельская обл. и Республика Коми", 0.3);
            areasKt.put("Ленинградская, Вологодская обл.", 0.28);
            areasKt.put("Европейская часть России", 0.28);
            areasKt.put("Беларусь", 0.28);
            areasKt.put("Украина", 0.28);
            areasKt.put("Карпаты", 0.3);
            areasKt.put("Крым", 0.3);
            areasKt.put("Западный Кавказ", 0.35);
            areasKt.put("Восточный Кавказ", 0.4);
            areasKt.put("Центральный Кавказ", 0.42);
            areasKt.put("Закавказье", 0.33);
            areasKt.put("Полярный Урал", 0.5);
            areasKt.put("Приполярный Урал", 0.63);
            areasKt.put("Северный Урал", 0.5);
            areasKt.put("Средний Урал", 0.35);
            areasKt.put("Южный Урал", 0.38);
            areasKt.put("Западная Сибирь", 0.42);
            areasKt.put("Средняя Азия и Казахстан (пуст.)", 0.4);
            areasKt.put("Западный Тянь-Шань", 0.5);
            areasKt.put("Северный Тянь-Шань", 0.53);
            areasKt.put("Центральный Тянь-Шань", 0.55);
            areasKt.put("Памир", 0.7);
            areasKt.put("Памиро-Алай", 0.63);
            areasKt.put("Джунгарский Алатау", 0.53);
            areasKt.put("Алтай", 0.6);
            areasKt.put("Кузнецкий Алатау", 0.55);
            areasKt.put("Горная Шория", 0.5);
            areasKt.put("Салаирский кряж", 0.38);
            areasKt.put("Западный Саян", 0.65);
            areasKt.put("Западная Тыва, Шапшальский хребет, Монгольский Алтай", 0.55);
            areasKt.put("Центральный и Восточный Саян", 0.7);
            areasKt.put("Северные тундровые районы", 0.55);
            areasKt.put("Плато Путорана", 0.65);
            areasKt.put("Хамар-Дабан", 0.6);
            areasKt.put("Байкальский хребет", 0.65);
            areasKt.put("Верхнеангарский хребет", 0.85);
            areasKt.put("Баргузинский хребет", 0.75);
            areasKt.put("Икатский и Муйский хребты", 1.0);
            areasKt.put("Хребты Кодар и Удокан", 1.0);
            areasKt.put("Становой хребет и Алданское нагорье", 0.9);
            areasKt.put("Верхоянский хребет", 0.83);
            areasKt.put("Хребет Черского", 0.9);
            areasKt.put("Хребет Сунтар-Хаята", 0.85);
            areasKt.put("Хабаровский край", 0.83);
            areasKt.put("Приморье", 0.83);
            areasKt.put("Сахалин", 0.75);
            areasKt.put("Курильские острова (сев.)", 0.8);
            areasKt.put("Курильские острова (южн.)", 0.95);
            areasKt.put("Камчатка (Срединный и Восточный хребты)", 0.88);
            areasKt.put("Камчатка (южная часть)", 0.63);
            areasKt.put("Камчатка (северная группа вулканов)", 0.65);
            areasKt.put("Магаданская область, Чукотка", 0.75);
            areasKt.put("Корякское нагорье", 0.75);
            areasKt.put("Северная Земля, Новая Земля, Земля Франца-Иосифа", 0.9);
        }
        if (areasG.size() == 0) {
            areasG.put("Кольский полуостров", 9);
            areasG.put("Карелия", 6);
            areasG.put("Архангельская обл. и Республика Коми", 8);
            areasG.put("Ленинградская, Вологодская обл.", 4);
            areasG.put("Европейская часть России", 1);
            areasG.put("Беларусь", 1);
            areasG.put("Украина", 1);
            areasG.put("Карпаты", 2);
            areasG.put("Крым", 2);
            areasG.put("Западный Кавказ", 4);
            areasG.put("Восточный Кавказ", 5);
            areasG.put("Центральный Кавказ", 5);
            areasG.put("Закавказье", 4);
            areasG.put("Полярный Урал", 9);
            areasG.put("Приполярный Урал", 10);
            areasG.put("Северный Урал", 7);
            areasG.put("Средний Урал", 6);
            areasG.put("Южный Урал", 6);
            areasG.put("Западная Сибирь", 8);
            areasG.put("Средняя Азия и Казахстан (пуст.)", 10);
            areasG.put("Западный Тянь-Шань", 8);
            areasG.put("Северный Тянь-Шань", 8);
            areasG.put("Центральный Тянь-Шань", 8);
            areasG.put("Памир", 9);
            areasG.put("Памиро-Алай", 8);
            areasG.put("Джунгарский Алатау", 8);
            areasG.put("Алтай", 8);
            areasG.put("Кузнецкий Алатау", 6);
            areasG.put("Горная Шория", 6);
            areasG.put("Салаирский кряж", 6);
            areasG.put("Западный Саян", 7);
            areasG.put("Западная Тыва, Шапшальский хребет, Монгольский Алтай", 8);
            areasG.put("Центральный и Восточный Саян", 9);
            areasG.put("Северные тундровые районы", 16);
            areasG.put("Плато Путорана", 15);
            areasG.put("Хамар-Дабан", 7);
            areasG.put("Байкальский хребет", 10);
            areasG.put("Верхнеангарский хребет", 12);
            areasG.put("Баргузинский хребет", 10);
            areasG.put("Икатский и Муйский хребты", 12);
            areasG.put("Хребты Кодар и Удокан", 13);
            areasG.put("Становой хребет и Алданское нагорье", 14);
            areasG.put("Верхоянский хребет", 18);
            areasG.put("Хребет Черского", 18);
            areasG.put("Хребет Сунтар-Хаята", 18);
            areasG.put("Хабаровский край", 12);
            areasG.put("Приморье", 9);
            areasG.put("Сахалин", 7);
            areasG.put("Курильские острова (сев.)", 10);
            areasG.put("Курильские острова (южн.)", 10);
            areasG.put("Камчатка (Срединный и Восточный хребты)", 13);
            areasG.put("Камчатка (южная часть)", 13);
            areasG.put("Камчатка (северная группа вулканов)", 13);
            areasG.put("Магаданская область, Чукотка", 18);
            areasG.put("Корякское нагорье", 19);
            areasG.put("Северная Земля, Новая Земля, Земля Франца-Иосифа", 30);
        }
    }

    private static void initA() {
        if (as.size() == 0) {
            as.put("Полная автономия", 1.0);
            as.put("С привлечением траспорта для организации заброски (промежуточных баз)", 0.7);
            as.put("Проходит через один населенный пункт", 0.5);
            as.put("Проходит через два и более населенных пункта", 0.2);
        }
    }

    private void initPointsForLPTable() {
        //переправа
        pointsForLPTable[0] = new double[]{0.5, 1, 4, 7, 12};
        //перевал
        pointsForLPTable[1] = new double[]{2, 4, 6, 8, 12};
        //вершина
        pointsForLPTable[2] = new double[]{4, 5, 7, 9};
        //траверс гребня
        pointsForLPTable[3] = new double[]{4, 5, 7, 9};
        //каньон
        pointsForLPTable[4] = new double[]{1, 1, 3, 5, 8};
        //водный участок
        pointsForLPTable[5] = new double[]{8, 20, 40, 56};
    }

    private void initMaxNumberOfLPTable() {
        //переправа
        maxNumberOfLPTable[0] = new int[][]{
                {8, 8, 8, 0, 0, 0},
                {0, 6, 6, 6, 0, 0},
                {0, 0, 3, 3, 3, 1},
                {0, 0, 0, 2, 2, 3},
                {0, 0, 0, 0, 2, 4}
        };
        //перевал
        maxNumberOfLPTable[1] = new int[][]{
                {2, 2, 2, 0, 0, 0},
                {0, 2, 2, 3, 0, 0},
                {0, 0, 2, 2, 2, 2},
                {0, 0, 0, 2, 2, 3},
                {0, 0, 0, 0, 3, 4}
        };
        //вершина
        maxNumberOfLPTable[2] = new int[][]{
                {0, 2, 2, 2, 0, 0},
                {0, 0, 2, 2, 3, 2},
                {0, 0, 0, 2, 3, 3},
                {0, 0, 0, 0, 2, 4}
        };
        //траверс гребня
        maxNumberOfLPTable[3] = new int[][]{
                {0, 2, 2, 2, 1, 0},
                {0, 0, 2, 2, 2, 2},
                {0, 0, 0, 2, 2, 3},
                {0, 0, 0, 0, 2, 4}
        };
        //каньон
        maxNumberOfLPTable[4] = new int[][]{
                {4, 4, 4, 4, 4, 4},
                {0, 4, 4, 4, 4, 4},
                {0, 0, 4, 4, 4, 4},
                {0, 0, 0, 4, 4, 4},
                {0, 0, 0, 0, 2, 4}
        };
        //водный участок
        maxNumberOfLPTable[5] = new int[][]{
                {0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 1}
        };
    }

    private void checkArgs(double l, int t, int[][] lp, String area, String a, double dh) {
        if (l <= 0 || t <= 0 || lp == null || area == null || a == null || dh < 0) {
            throw new IllegalArgumentException();
        }
        if (lp.length != 6 || lp[0].length != 6 || lp[1].length != 6 || lp[2].length != 5 || lp[3].length != 5 || lp[4].length != 6 || lp[5].length != 5) {
            throw new IllegalArgumentException();
        } else {
            for (int[] i : lp) {
                for (int j : i) {
                    if (j < 0)
                        throw new IllegalArgumentException();
                }
            }
        }
    }

    public DifficultyCategory(double l, int t, int[][] lp, String area, String a, double dh) {
        checkArgs(l, t, lp, area, a, dh);

        initAreas();
        initA();
        initPointsForLPTable();
        initMaxNumberOfLPTable();

        this.l = l;
        this.t = t;
        this.lp = lp;
        this.area = area;
        this.a = a;
        this.dh = dh;
    }

    public String calculationOfDifficultyCategoryOfWalkingRoute() {
        double points = 0;
        double points_i;
        int resIndCat = 0;
        int s1 = stage1();
        for (indicativeСategory = 0; indicativeСategory <= s1; indicativeСategory++) {
            //stage5
            points_i = stage2() + stage3() + stage4();
            if (points_i > points) {
                points = points_i;
                resIndCat = indicativeСategory;
            }
        }
        indicativeСategory = resIndCat;
        //stage6
        finalСategory = 0;
        while (finalСategory < 6 && points >= categoryParametersTable[finalСategory][4]) {
            finalСategory++;
        }
        return stage6_2();
    }

    private int stage1() {
        int ic1 = 0;
        while (ic1 < 6 && l >= categoryParametersTable[ic1][1]) {
            ic1++;
        }

        int ic2 = 0;
        while (ic2 < 6 && t >= categoryParametersTable[ic2][0]) {
            ic2++;
        }

        int ic3 = Math.max(ic1, ic2);
        /*boolean isOK = false;
        while (!isOK) {
            if (ic3 == 0) return ic3;
            for (int i = 0; i < lp.length; i++) {
                if (isOK)
                    break;
                for (int j = 0; j < lp[i].length; j++) {
                    //если есть препятствие определенного типа i и определенной к.т. j
                    //которое учитывается при заданной ориентировочной к.с. ic3
                    if (lp[i][j] != 0 && maxNumberOfLPTable[i][j][ic3 - 1] != 0) {
                        isOK = true;
                        break;
                    }
                }
            }
            if (!isOK)
                ic3--;
        }*/

        return ic3;
    }

    private double stage2() {
        double res = 0;
        if (indicativeСategory == 0)
            return res;
        int[][] lp2 = new int[lp.length][];
        for (int i = 0; i < lp.length; i++) {
            lp2[i] = new int[lp[i].length];
            System.arraycopy(lp[i], 0, lp2[i], 0, lp[i].length);
        }

        int buf = 0;
        for (int i = 0; i < lp2.length; i++) {
            if (lp[i][lp2[i].length - 1] != 0) {
                buf += lp[i][lp2[i].length - 1];
            }
            for (int j = lp2[i].length - 2; j >= 0; j--) {
                if (maxNumberOfLPTable[i][j][indicativeСategory - 1] == 0) {
                    buf += lp[i][j];
                    lp2[i][j] = 0;
                } else if (maxNumberOfLPTable[i][j][indicativeСategory - 1] - lp2[i][j] >= buf) {
                    lp2[i][j] += buf;
                    buf = 0;
                } else {
                    buf -= (maxNumberOfLPTable[i][j][indicativeСategory - 1] - lp2[i][j]);
                    lp2[i][j] = maxNumberOfLPTable[i][j][indicativeСategory - 1];
                }
            }
            buf = 0;
        }

        for (int i = 0; i < lp2.length; i++)
            for (int j = 0; j < lp2[i].length - 1; j++) {
                if (maxNumberOfLPTable[i][j][indicativeСategory - 1] != 0) {
                    res += lp2[i][j] * pointsForLPTable[i][j];
                }
            }

        if (res > categoryParametersTable[indicativeСategory - 1][2]) {
            res = categoryParametersTable[indicativeСategory - 1][2];
        }

        return res;
    }

    private double stage3() {
        if (indicativeСategory == 0) return 0;
        return areasKt.get(area) * categoryParametersTable[indicativeСategory - 1][3] * l / categoryParametersTable[indicativeСategory - 1][1];
    }

    private double stage4() {
        double k = 1 + dh / 12;
        if (indicativeСategory == 1 || indicativeСategory == 2)
            a = "Полная автономия";
        return areasG.get(area) * k * as.get(a);
    }

    private String stage6_2() {
        if (indicativeСategory == finalСategory) {
            if (finalСategory == 0) return "некатегорийный";
            else return String.valueOf(finalСategory);
        } else if (indicativeСategory > finalСategory) {
            int higherCategory = 1;
            for (int i = 0; i < lp.length; i++) {
                for (int j = 0; j < lp[i].length; j++) {
                    if (lp[i][j] > 0 && (finalСategory == 0 || maxNumberOfLPTable[i][j][indicativeСategory - 1] > 0 && maxNumberOfLPTable[i][j][finalСategory - 1] == 0)) {
                        int index = 0;
                        while (maxNumberOfLPTable[i][j][index] == 0)
                            index++;
                        if (higherCategory < index + 1)
                            higherCategory = index + 1;
                    }
                }
            }
            if (higherCategory <= finalСategory) {
                if (finalСategory == 0) return "некатегорийный";
                else return String.valueOf(finalСategory);
            } else {
                if (finalСategory == 0) return "некатегорийный (с элементами " + higherCategory + " к.с.)";
                else return finalСategory + " (с элементами " + higherCategory + " к.с.)";
            }
        } else {
            if (l >= 0.75 * categoryParametersTable[finalСategory - 1][1]) {
                if (indicativeСategory == 0) return "некатегорийный (может быть присвоена " + finalСategory + " к.с.)";
                else return indicativeСategory + " (может быть присвоена " + finalСategory + " к.с.)";
            } else {
                if (indicativeСategory == 0) return "некатегорийный";
                else return String.valueOf(indicativeСategory);
            }
        }
    }

    public static String[] areas0 = new String[]{
            "Кольский полуостров",
            "Карелия",
            "Архангельская обл. и Республика Коми",
            "Ленинградская, Вологодская обл.",
            "Европейская часть России",
            "Беларусь",
            "Украина",
            "Карпаты",
            "Крым",
            "Западный Кавказ",
            "Восточный Кавказ",
            "Центральный Кавказ",
            "Закавказье",
            "Полярный Урал",
            "Приполярный Урал",
            "Северный Урал",
            "Средний Урал",
            "Южный Урал",
            "Западная Сибирь",
            "Средняя Азия и Казахстан (пуст.)",
            "Западный Тянь-Шань",
            "Северный Тянь-Шань",
            "Центральный Тянь-Шань",
            "Памир",
            "Памиро-Алай",
            "Джунгарский Алатау",
            "Алтай",
            "Кузнецкий Алатау",
            "Горная Шория",
            "Салаирский кряж",
            "Западный Саян",
            "Западная Тыва, Шапшальский хребет, Монгольский Алтай",
            "Центральный и Восточный Саян",
            "Северные тундровые районы",
            "Плато Путорана",
            "Хамар-Дабан",
            "Байкальский хребет",
            "Верхнеангарский хребет",
            "Баргузинский хребет",
            "Икатский и Муйский хребты",
            "Хребты Кодар и Удокан",
            "Становой хребет и Алданское нагорье",
            "Верхоянский хребет",
            "Хребет Черского",
            "Хребет Сунтар-Хаята",
            "Хабаровский край",
            "Приморье",
            "Сахалин",
            "Курильские острова (сев.)",
            "Курильские острова (южн.)",
            "Камчатка (Срединный и Восточный хребты)",
            "Камчатка (южная часть)",
            "Камчатка (северная группа вулканов)",
            "Магаданская область, Чукотка",
            "Корякское нагорье",
            "Северная Земля, Новая Земля, Земля Франца-Иосифа",
    };

    public static String[] as0 = new String[]{
            "Полная автономия",
            "С привлечением траспорта для организации заброски (промежуточных баз)",
            "Проходит через один населенный пункт",
            "Проходит через два и более населенных пункта"
    };
}
