package com.wildezhawer.hashcode.model;

import lombok.Data;

import java.util.Map;

@Data
public class Contributor {
    public String name;
    public Map<String, Integer> skills;
    public Contributor mentoring;
}
