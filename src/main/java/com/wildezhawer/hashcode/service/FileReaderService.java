package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Contributor;
import com.wildezhawer.hashcode.model.Project;
import com.wildezhawer.hashcode.model.Skill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FileReaderService {

    public void read(String filename) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Read first line
            String line = br.readLine();
            String[] items = line.split(" ");

            int numberOfContributors = Integer.parseInt(items[0]);
            int numberOfProjects = Integer.parseInt(items[1]);

            // Read contributors
            for (int i = 0; i < numberOfContributors; i++){
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
            for (int i = 0; i < numberOfProjects; i++){
                line = br.readLine();
                items = line.split(" ");

                Project project = new Project();
                project.name = items[0];
                project.duration = Integer.parseInt(items[1]);
                project.score = Integer.parseInt(items[2]);
                project.bestBefore = Integer.parseInt(items[3]);

                int numberOfRoles = Integer.parseInt(items[4]);
                project.roles = new ArrayList<>();
                for (int j = 0; j < numberOfRoles; j++){
                    // Roles
                    line = br.readLine();
                    items = line.split(" ");
                    Skill skill = new Skill();
                    skill.name = items[0];
                    skill.level = Integer.parseInt(items[1]);
                    project.roles.add(skill);
                }
                DataStore.projects.add(project);
            }
        }
    }
}
