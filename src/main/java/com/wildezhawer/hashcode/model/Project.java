package com.wildezhawer.hashcode.model;

import lombok.Data;

import java.util.List;

@Data
public class Project {

    public String name;
    public int duration;
    public int score;
    public int bestBefore;
    public List<Skill> roles;

    public int startDay;
}
