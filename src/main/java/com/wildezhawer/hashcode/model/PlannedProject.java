package com.wildezhawer.hashcode.model;

import lombok.Data;

import java.util.List;

@Data
public class PlannedProject {
    public Project project;
    public List<Contributor> contributors;
}
