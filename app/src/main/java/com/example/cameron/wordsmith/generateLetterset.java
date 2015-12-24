package com.example.cameron.wordsmith;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class generateLetterset
{
    static final String[] vowels = new String[] {
            "A", "E", "I", "O", "U"
    };

    private static final Map<String, Double> myMap;
    static
    {
        myMap = new HashMap<String, Double>();
        myMap.put("A", 0.079971);
        myMap.put("B", 0.020199);
        myMap.put("C", 0.043593);
        myMap.put("D", 0.032184);
        myMap.put("E", 0.112513);
        myMap.put("F", 0.014622);
        myMap.put("G", 0.022845);
        myMap.put("G", 0.023497);
        myMap.put("I", 0.08563);
        myMap.put("J", 0.001693);
        myMap.put("K", 0.008535);
        myMap.put("L", 0.060585);
        myMap.put("M", 0.028389);
        myMap.put("N", 0.071596);
        myMap.put("O", 0.065476);
        myMap.put("P", 0.029165);
        myMap.put("Q", 0.00183);
        myMap.put("R", 0.07264);
        myMap.put("S", 0.066044);
        myMap.put("T", 0.070926);
        myMap.put("U", 0.037091);
        myMap.put("V", 0.011369);
        myMap.put("W", 0.008899);
        myMap.put("X", 0.002925);
        myMap.put("Y", 0.024432);
        myMap.put("Z", 0.00335);


    }

    public static String[] main() {
        return ensureVowel(getLettsArr());
    }

    public static String[] getLettsArr() {
        String[] letterset = new String[8];
        for (int i = 0; i < 8; ++i) {
            letterset[i] = getWeightedRandom();
        }
        return letterset;
    }

    public static JSONArray ensureVowel(String[] letterset) {
        for (int i = 0; i < vowels.length; ++i) {
            if (Arrays.asList(letterset).contains(vowels[i])) {
                return letterset;
            }
        }
        JSONArray letterSetAsJSON = new JSONArray(
                Arrays.asList(ensureVowel(
                        getLettsArr())));
        return letterSetAsJSON;
    }


    public static String getWeightedRandom(){
        double totalWeight = 0.0;
        for (Map.Entry<String, Double> entry : myMap.entrySet()) {
            totalWeight += entry.getValue();
        }
        double random = Math.random() * totalWeight;
        double countWeight = 0.0;

        for (Map.Entry<String, Double> entry : myMap.entrySet()) {
            countWeight += entry.getValue();
            if (countWeight >= random) {
                System.out.println(entry.getKey());
                return entry.getKey();
            }
        };
        throw new RuntimeException("Error");
    }
}


