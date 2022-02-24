package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Contributor;
import com.wildezhawer.hashcode.model.Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class FileReaderService {

    private final static String DATA_FOLDER = System.getProperty("user.dir") + "/data/";

    public void read(String filename) throws Exception {
        String line = "";
        String[] items;

        int contributorCount;
        int projectCount;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Read first line
            line = br.readLine();
            items = line.split(" ");

            contributorCount = Integer.parseInt(items[0]);
            projectCount = Integer.parseInt(items[1]);

            // Read contributors
            for (int i = 0; i < contributorCount; i++){
                line = br.readLine();
                items = line.split(" ");

                Contributor contributor = new Contributor();
                contributor.name = items[0];

                contributor.skills = new HashMap<>();
                int numberOfSkills = Integer.parseInt(items[1]);
                for (int j = 0; j < numberOfSkills; j++){
                    // Skills
                    line = br.readLine();
                    items = line.split(" ");
                    contributor.skills.put(items[0], Integer.parseInt(items[1]));
                }
                DataStore.contributors.add(contributor);
            }

            // Read Projects
            for (int i = 0; i < projectCount; i++){
                line = br.readLine();
                items = line.split(" ");

                Project project = new Project();
                project.name = items[0];
                project.duration = Integer.parseInt(items[1]);
                project.score = Integer.parseInt(items[2]);
                project.bestBefore = Integer.parseInt(items[3]);

                int numberOfRoles = Integer.parseInt(items[4]);
                project.roles = new HashMap<>();
                for (int j = 0; j < numberOfRoles; j++){
                    // Roles
                    line = br.readLine();
                    items = line.split(" ");
                    project.roles.put(items[0], Integer.parseInt(items[1]));
                }
                DataStore.projects.add(project);
            }
        }
    }
}
