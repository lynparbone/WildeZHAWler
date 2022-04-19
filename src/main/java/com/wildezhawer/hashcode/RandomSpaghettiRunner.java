package com.wildezhawer.hashcode;

import com.wildezhawer.hashcode.model.PlannedProject;
import com.wildezhawer.hashcode.model.Project;
import com.wildezhawer.hashcode.service.DataStore;
import com.wildezhawer.hashcode.service.FileReaderService;
import com.wildezhawer.hashcode.service.FileWriterService;
import com.wildezhawer.hashcode.service.SimulationRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wildezhawer.hashcode.config.SimulationConfiguration.FILES_TO_READ;
import static com.wildezhawer.hashcode.service.ProjectSorter.sortProjectsByDurationAscending;

public class RandomSpaghettiRunner {

    FileReaderService fileReader = new FileReaderService();
    FileWriterService fileWriter = new FileWriterService();

    public static void main(String[] args) throws Exception {
        new RandomSpaghettiRunner().start();
    }

    public void start() throws Exception {
        for (String filename : FILES_TO_READ) {
            // Preparation for simulation
            read(filename);
            DataStore.projects = sortProjectsByDurationAscending(DataStore.projects);

            // Run simulation, i.e. plan the projects
            SimulationRunner simulationRunner = new SimulationRunner(DataStore.projects, DataStore.contributors);

            simulationRunner.run();

            // Retrieve all the completed projects
            List<Project> completedProjects = simulationRunner.projects.stream()
                    .filter(project -> simulationRunner.allProjectStatus.get(project.getName()) == SimulationRunner.ProjectStatus.COMPLETED)
                    .collect(Collectors.toList());

            // Setup results for correct output format
            List<PlannedProject> plannedProjects = new ArrayList<>();
            for (Project project : completedProjects) {
                PlannedProject plannedProject = new PlannedProject();
                plannedProject.setProject(project.getName());
                plannedProject.setContributors(simulationRunner.projectStaffing.get(project.getName()));
                plannedProjects.add(plannedProject);
            }

            DataStore.plannedProjects = plannedProjects;

            // Write the output files in the correct format
            write(filename);
            resetDatastore();
        }
    }

    private void read(String filename) throws Exception {
        fileReader.read("input/" + filename);
    }

    private void write(String filename) throws Exception {
        fileWriter.writeFile("output/" + filename);
    }

    private void resetDatastore() {
        DataStore.projects = new ArrayList<>();
        DataStore.contributors = new ArrayList<>();
        DataStore.plannedProjects = new ArrayList<>();
    }

}
