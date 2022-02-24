package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Contributor;
import com.wildezhawer.hashcode.model.Project;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class SimulationRunner {

    private List<Project> projects;
    private Collection<Contributor> contributors;
    private HashMap<String, Status> projectStatus = new HashMap<>();
    private enum Status {
            NOT_STARTED,
            STARTED,
            TOO_LATE,
            COMPLETED
    };

    private HashMap<String, Boolean> contributerStatus = new HashMap<>();

    private HashMap<String, List<String>> projectStaffing = new HashMap<>();


    public void run() {
        init();

        int dayCounter = 0;

        while(continueRunningSimulation()) {
            List<Project> completedProjects = completeProject(dayCounter);
            levelUpContributers(completedProjects);

            changeProjectStatusToFalse(dayCounter);

            // get not staffet contributers
            // get not started projects

            // Staffing

            // Update staffing list

            dayCounter++;
        }
    }

    private void init() {
        for (Project project : projects) {
            projectStatus.put(project.getName(), Status.NOT_STARTED);
        }

        for (Contributor contributor : contributors) {
            contributerStatus.put(contributor.getName(), true);
        }
    }

    /**
     * true - wir machen weiter
     * false - wir stoppen simulation
     * @return
     */
    private boolean continueRunningSimulation() {
        for (String projectName : projectStatus.keySet()) {
            if (projectStatus.get(projectName) == Status.NOT_STARTED || projectStatus.get(projectName) == Status.STARTED) {
                return true;
            }
        }

        return false;
    }

    private void changeProjectStatusToFalse(int day) {
        for (Project project : projects) {
            if (day == project.getBestBefore() && projectStatus.get(project.getName()) == Status.NOT_STARTED) {
                projectStatus.put(project.getName(), Status.TOO_LATE);
            }
        }
    }

    private List<Project> completeProject(int day) {
        List<Project> completedProjects = new ArrayList<>();

        for (Project project : projects) {
            if (projectStatus.get(project.getName()) == Status.STARTED) {
                if (project.getStartDay()+project.getDuration() == day) {
                    projectStatus.put(project.getName(), Status.COMPLETED);
                    completedProjects.add(project);
                }
            }
        }

        return completedProjects;
    }

    private void levelUpContributers(List<Project> completedProjects) {

    }


}