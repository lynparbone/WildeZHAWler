package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Contributor;
import com.wildezhawer.hashcode.model.Project;
import lombok.AllArgsConstructor;

import java.util.*;

public class SimulationRunner {

    public List<Project> projects;
    private List<Contributor> contributors;

    public SimulationRunner(List<Project> projects, List<Contributor> contributors) {
        this.projects = projects;
        this.contributors = contributors;
    }

    public HashMap<String, Status> projectStatus = new HashMap<>();
    public enum Status {
            NOT_STARTED,
            STARTED,
            TOO_LATE,
            COMPLETED
    };

    private HashMap<String, Boolean> contributerStatus = new HashMap<>();

    public HashMap<String, List<String>> projectStaffing = new HashMap<>();


    public void run() {
        init();

        int dayCounter = 0;

        while(continueRunningSimulation()) {
            List<Project> completedProjects = completeProject(dayCounter);
            levelUpContributers(completedProjects);

            changeProjectStatusToFalse(dayCounter);

            List<Contributor> availableContributors = getAllAvailableContributers();

            for (Project project : projects) {
                if (projectStatus.get(project.getName()) == Status.NOT_STARTED) {
                    List<Contributor> assignedContributors = BestProjectMatchFinder.findBestMatchesForProject(project, availableContributors);
                    if (!assignedContributors.isEmpty()) {
                        updateStaffingInformation(project, assignedContributors);
                    }
                }
            }

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

                    // set contributer status to true
                    for (String contributorName : projectStaffing.get(project.getName())) {
                        contributerStatus.put(contributorName, true);
                    }
                }
            }
        }

        return completedProjects;
    }

    private void levelUpContributers(List<Project> completedProjects) {
        for(Project project : completedProjects){
            List<String> staffedContributers = projectStaffing.get(project.getName());
            int i = 0;
            for(String skill : project.roles.keySet()){
                int level = project.roles.get(skill);
                String name = staffedContributers.get(i);
                for(Contributor cont : contributors){
                    if(cont.name.equals(name)){
                        if(level >= cont.skills.getOrDefault(skill, 0)){
                            Integer lvl = cont.skills.get(skill);
                            if(lvl != null){
                                lvl += 1;
                                cont.skills.put(skill, lvl);
                            }
                            else{
                                cont.skills.put(skill,1);
                            }
                        }
                    }
                }



            }
        }

    }

    private List<Contributor> getAllAvailableContributers() {
        List<Contributor> availableContributers = new ArrayList<>();

        for (Contributor contributor : contributors) {
            if (contributerStatus.get(contributor.getName())) {
                availableContributers.add(contributor);
            }
        }

        return availableContributers;
    }

    private void updateStaffingInformation(Project project, List<Contributor> assignedContributors) {
        List<String> contributorNames = new ArrayList<>();

        for (Contributor contributor : assignedContributors) {
            contributerStatus.put(contributor.getName(), false);
            contributorNames.add(contributor.getName());
        }

        projectStaffing.put(project.getName(), contributorNames);
        projectStatus.put(project.getName(), Status.STARTED);
    }




}
