package com.example.cameron.wordsmith;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;

public class wordScore
{

    private static final Map<Integer, Integer> formulas;
    static
    {
        formulas = new LinkedHashMap<Integer, Integer>();
        formulas.put(1, 0);
        formulas.put(20, 2000);
        formulas.put(70, 7000);
        formulas.put(80, 8000);
        formulas.put(100, 10000);
        formulas.put(120, 12000);
        formulas.put(140, 15000);
        formulas.put(180, 20000);
        formulas.put(220, 25000);
        formulas.put(260, 30000);
        formulas.put(350, 40000);
        formulas.put(440, 50000);
    }

    private static final Map<String, Integer> values;
    static
    {
        values = new HashMap<String, Integer>();
        values.put("a", 3);
        values.put("b", 10);
        values.put("c", 7);
        values.put("d", 9);
        values.put("e", 1);
        values.put("f", 10);
        values.put("g", 9);
        values.put("h", 9);
        values.put("i", 4);
        values.put("j", 12);
        values.put("k", 6);
        values.put("l", 2);
        values.put("m", 9);
        values.put("n", 5);
        values.put("o", 5);
        values.put("p", 9);
        values.put("q", 12);
        values.put("r", 4);
        values.put("s", 6);
        values.put("t", 5);
        values.put("u", 8);
        values.put("v", 11);
        values.put("w", 11);
        values.put("x", 12);
        values.put("y", 10);
        values.put("z", 12);


    }
    public static int score(String word) {
        int sum = 0;
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            sum += values.get(Character.toString(c).toLowerCase());
        };

        int i = 0;
        int factor = 0;
        int offset = 0;

        Set<Integer> keys = formulas.keySet();
        for (Integer  k : keys) {
            System.out.println(i);
            if (i == word.length()-1){

                factor = k;
                offset = formulas.get(k);
                break;
            }

            ++i;
        };

        return factor * sum + offset;


    }
    public static boolean testBonus(int bonus, int time) {
        int b = 0;

        if (time > 120) {
            return false;
        } else if (time >= 100) {
            b = time * 100;
        } else if (time >= 50) {
            b = time * 200;
        } else if (time >= 25) {
            b = time * 300;
        } else {
            b = time * 400;
        }
        if (b != bonus) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {

    };

};



