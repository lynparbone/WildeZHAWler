package com.wildezhawer.hashcode;

import com.wildezhawer.hashcode.config.SimulationConfiguration;
import com.wildezhawer.hashcode.service.DataStore;
import com.wildezhawer.hashcode.service.FileReaderService;
import com.wildezhawer.hashcode.service.FileWriterService;

import java.util.ArrayList;

public class RandomSpaghettiRunner {

    FileReaderService fileReader = new FileReaderService();
    FileWriterService fileWriter = new FileWriterService();

    public void start() throws Exception {
        for (String filename : SimulationConfiguration.FILES_TO_READ) {
            read(filename);

            //Simulation here




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
