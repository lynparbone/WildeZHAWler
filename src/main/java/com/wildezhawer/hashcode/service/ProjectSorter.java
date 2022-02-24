package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Project;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectSorter {

    public static List<Project> sortProjects(Collection<Project> unsortedProjects) {
        return unsortedProjects.stream()
                .sorted(Comparator.comparing(Project::getDuration))
                .collect(Collectors.toList());
    }

}
