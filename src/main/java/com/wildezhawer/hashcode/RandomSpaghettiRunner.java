package com.wildezhawer.hashcode;

import com.wildezhawer.hashcode.config.SimulationConfiguration;
import com.wildezhawer.hashcode.model.InputData;
import com.wildezhawer.hashcode.model.OutputData;
import com.wildezhawer.hashcode.service.DataStore;
import com.wildezhawer.hashcode.service.FileReaderService;

import java.util.List;

public class RandomSpaghettiRunner {

    FileReaderService fileReader = new FileReaderService();

    public void start() throws Exception {
        for (String filename : SimulationConfiguration.FILES_TO_READ) {
            InputData inputData = readFileContent(filename);
            writeFileContent(null);
        }
        System.out.println(DataStore.text);
    }

    private InputData readFileContent(String filename) throws Exception {
        List<String> lines = fileReader.read(filename);
        return null;
    }

    private void writeFileContent(OutputData data) {

    }



}
