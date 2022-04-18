package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Contributor;
import com.wildezhawer.hashcode.model.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.wildezhawer.hashcode.service.BestProjectMatchFinder.findBestMatchesForProject;

/*
    This class runs the actual simulation of the projects. It simulates each passing day and updates projects appropriately:
    - If the project is staffed, it's status is set to STARTED, which means it is running
    - Once a required amount of days for a staffed project has elapsed, the project status is changed to COMPLETED
    - If the project could not be completed before it's best by date, regardless whether it was staffed or not, its status is set to TOO_LATE
 */
public class SimulationRunner {

    public List<Project> projects;
    private List<Contributor> contributors;

    public enum ProjectStatus {
        NOT_STARTED,
        STARTED,
        TOO_LATE,
        COMPLETED
    };
    public HashMap<String, ProjectStatus> allProjectStatus = new HashMap<>();
    private enum ContributorStatus {
        AVAILABLE,
        STAFFED
    };
    private HashMap<String, ContributorStatus> allContributorStatus = new HashMap<>();

    public HashMap<String, List<String>> projectStaffing = new HashMap<>();

    public SimulationRunner(List<Project> projects, List<Contributor> contributors) {
        this.projects = projects;
        this.contributors = contributors;
    }

    public void run() {
        initProjectAndContributorStatus();

        int dayCounter = 0;

        while(hasUnfinishedProjects() && dayCounter < 50) {

            completeProjectsIfEnoughTimeHasElapsed(dayCounter);
            updateExpiredProjects(dayCounter);
            staffNotStartedProjectsIfContributorsAvailable();

            dayCounter++;
        }
    }

    private void staffNotStartedProjectsIfContributorsAvailable() {
        List<Contributor> availableContributors = getAllAvailableContributors();

        for (Project project : projects) {
            if (allProjectStatus.get(project.getName()) == ProjectStatus.NOT_STARTED) {
                List<Contributor> assignedContributors = findBestMatchesForProject(project, availableContributors);
                if (!assignedContributors.isEmpty()) {
                    staffAndStartProject(project, assignedContributors);
                }
            }
        }
    }

    private void initProjectAndContributorStatus() {
        for (Project project : projects) {
            allProjectStatus.put(project.getName(), ProjectStatus.NOT_STARTED);
        }

        for (Contributor contributor : contributors) {
            allContributorStatus.put(contributor.getName(), ContributorStatus.AVAILABLE);
        }
    }

    /*
     * true - we continue the simulation
     * false - we stop the simulation
     */
    private boolean hasUnfinishedProjects() {
        for (String projectName : allProjectStatus.keySet()) {
            if (allProjectStatus.get(projectName) == ProjectStatus.NOT_STARTED || allProjectStatus.get(projectName) == ProjectStatus.STARTED) {
                return true;
            }
        }

        return false;
    }

    private void updateExpiredProjects(int day) {
        for (Project project : projects) {
            if (day == project.getBestBefore() && allProjectStatus.get(project.getName()) == ProjectStatus.NOT_STARTED) {
                allProjectStatus.put(project.getName(), ProjectStatus.TOO_LATE);
            }
        }
    }

    private void completeProjectsIfEnoughTimeHasElapsed(int day) {
        List<Project> completedProjects = new ArrayList<>();

        for (Project project : projects) {
            if (allProjectStatus.get(project.getName()) == ProjectStatus.STARTED) {
                boolean projectIsFinished = project.getStartDay() + project.getDuration() == day;
                if (projectIsFinished) {
                    allProjectStatus.put(project.getName(), ProjectStatus.COMPLETED);
                    completedProjects.add(project);

                    // set contributor status to available as project has ended
                    for (String contributorName : projectStaffing.get(project.getName())) {
                        allContributorStatus.put(contributorName, ContributorStatus.AVAILABLE);
                    }
                }
            }
        }
    }

    private List<Contributor> getAllAvailableContributors() {
        List<Contributor> availableContributors = new ArrayList<>();

        for (Contributor contributor : contributors) {
            if (allContributorStatus.get(contributor.getName()) == ContributorStatus.AVAILABLE) {
                availableContributors.add(contributor);
            }
        }

        return availableContributors;
    }

    private void staffAndStartProject(Project project, List<Contributor> assignedContributors) {
        List<String> contributorNames = new ArrayList<>();

        for (Contributor contributor : assignedContributors) {
            allContributorStatus.put(contributor.getName(), ContributorStatus.STAFFED);
            contributorNames.add(contributor.getName());
        }

        projectStaffing.put(project.getName(), contributorNames);
        allProjectStatus.put(project.getName(), ProjectStatus.STARTED);
    }

}
