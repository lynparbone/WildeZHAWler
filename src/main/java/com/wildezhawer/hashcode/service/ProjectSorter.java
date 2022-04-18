package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Project;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectSorter {

    /*
        Sort the projects by duration (ascending), so the shorter projects are addressed first.
     */
    public static List<Project> sortProjectsByDurationAscending(Collection<Project> unsortedProjects) {
        return unsortedProjects.stream()
                .sorted(Comparator.comparing(Project::getDuration))
                .collect(Collectors.toList());
    }

}
