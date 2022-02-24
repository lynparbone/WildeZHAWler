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
            write(plannedProject.project);
            newLine();
            write(String.join( " ", plannedProject.contributors));
        }
        close();
    }


    private void newLine() {
        try {
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
