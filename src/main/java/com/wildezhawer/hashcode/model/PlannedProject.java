package com.wildezhawer.hashcode.model;

import lombok.Data;

import java.util.List;

@Data
public class PlannedProject {
    public String project;
    public List<String> contributors;
}
