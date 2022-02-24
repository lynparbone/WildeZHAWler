package com.wildezhawer.hashcode;

import com.wildezhawer.hashcode.config.SimulationConfiguration;
import com.wildezhawer.hashcode.model.PlannedProject;
import com.wildezhawer.hashcode.model.Project;
import com.wildezhawer.hashcode.service.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RandomSpaghettiRunner {

    FileReaderService fileReader = new FileReaderService();
    FileWriterService fileWriter = new FileWriterService();

    public void start() throws Exception {
        for (String filename : SimulationConfiguration.FILES_TO_READ) {
            read(filename);

            //Simulation here
            DataStore.projects = ProjectSorter.sortProjects(DataStore.projects);
            SimulationRunner simulationRunner = new SimulationRunner(DataStore.projects, DataStore.contributors);

            simulationRunner.run();

            List<Project> projectList = simulationRunner.projects.stream().filter(project -> simulationRunner.projectStatus.get(project.getName()) == SimulationRunner.Status.COMPLETED).collect(Collectors.toList());

            List<PlannedProject> plannedProjectList =new ArrayList<>();
            for (Project project : projectList) {
                PlannedProject plannedProject = new PlannedProject();
                plannedProject.setProject(project.getName());
                plannedProject.setContributors(simulationRunner.projectStaffing.get(project.getName()));
                plannedProjectList.add(plannedProject);
            }

            DataStore.plannedProjects = plannedProjectList;

            write(filename);
            reset();
        }
    }

    private void reset() {
        DataStore.projects = new ArrayList<>();
        DataStore.contributors = new ArrayList<>();
        DataStore.plannedProjects = new ArrayList<>();
    }

    void read(String filename) throws Exception {
        fileReader.read("data/" + filename);
    }

    private void write(String filename) throws Exception {
        fileWriter.writeFile("out/" + filename);
    }



}
