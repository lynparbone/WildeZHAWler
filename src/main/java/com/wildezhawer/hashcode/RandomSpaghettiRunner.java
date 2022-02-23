package com.wildezhawer.hashcode;

import com.wildezhawer.hashcode.config.SimulationConfiguration;
import com.wildezhawer.hashcode.model.InputData;
import com.wildezhawer.hashcode.model.OutputData;
import com.wildezhawer.hashcode.service.FileReaderService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RandomSpaghettiRunner {

    FileReaderService fileReader = new FileReaderService();

    @Bean
    public void start() throws Exception {
        for (String filename : SimulationConfiguration.FILES_TO_READ) {
            InputData inputData = readFileContent(filename);
            writeFileContent(null);
        }
    }

    private InputData readFileContent(String filename) throws Exception {
        List<String> lines = fileReader.read(filename);
        return null;
    }

    private void writeFileContent(OutputData data) {

    }



}
