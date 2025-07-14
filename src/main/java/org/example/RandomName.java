package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomName {
    private final List<String> names;
    private final Random random;
    RandomName(){
        names = new ArrayList<>(List.of("shadab", "anas", "utkarsh", "rakshit", "aalok", "pavneet", "aashita", "daksh", "harshit"));
        random = new Random();
    }

    public String getRandomName(){
        return names.get(random.nextInt(names.size()));
    }

}
