package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.PlannedProject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileWriterService {

    private BufferedWriter bufferedWriter;

    public void writeFile(String filename) throws IOException {
        bufferedWriter = new BufferedWriter(new java.io.FileWriter(filename));

        write(String.valueOf(DataStore.plannedProjects.size()));
        newLine();
        for (PlannedProject plannedProject : DataStore.plannedProjects){
            write(plannedProject.project.name);
            List<String> contributorNamesAsList = plannedProject.contributors.stream().map(contributor -> contributor.name).collect(Collectors.toList());
            write(String.join( " ", contributorNamesAsList));
        }
        close();
    }


    private void newLine() {
        write("\n");
    }


    private void write(String string) {
        try {
            bufferedWriter.append(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
