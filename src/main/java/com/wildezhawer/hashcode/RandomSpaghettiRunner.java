package com.wildezhawer.hashcode;

import com.wildezhawer.hashcode.config.SimulationConfiguration;
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
            List<String> lines = readFileContent(filename);
        }
    }

    private List<String> readFileContent(String filename) throws Exception {
        List<String> lines = fileReader.read(filename);
        return lines;
    }



}
