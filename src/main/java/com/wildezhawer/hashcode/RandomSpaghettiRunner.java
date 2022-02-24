package com.wildezhawer.hashcode;

import com.wildezhawer.hashcode.config.SimulationConfiguration;
import com.wildezhawer.hashcode.model.InputData;
import com.wildezhawer.hashcode.model.OutputData;
import com.wildezhawer.hashcode.service.FileReaderService;

public class RandomSpaghettiRunner {

    FileReaderService fileReader = new FileReaderService();

    public void start() throws Exception {
        for (String filename : SimulationConfiguration.FILES_TO_READ) {
            InputData inputData = readFileContent(filename);
            writeFileContent(null);
        }
    }

    private InputData readFileContent(String filename) throws Exception {
        fileReader.read("data/" + filename);
        return null;
    }

    private void writeFileContent(OutputData data) {

    }



}
