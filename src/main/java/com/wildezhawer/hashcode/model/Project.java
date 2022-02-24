package com.wildezhawer.hashcode.model;

import lombok.Data;

import java.util.Map;

@Data
public class Project {

    public String name;
    public int duration;
    public int score;
    public int bestBefore;
    public Map<String, Integer> roles;

    public int startDay;
}
