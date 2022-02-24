package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Contributor;
import com.wildezhawer.hashcode.model.Project;

import java.util.ArrayList;
import java.util.List;

public class BestProjectMatchFinder {
    // Returns contributor for each role, sorted by role (i.e. entry 0 is for role 0 etc.)
    public static List<Contributor> findBestMatchesForProject(Project project, List<Contributor> potentialContributors) {
        List<Contributor> bestMatches = new ArrayList<>();
        for (String skill : project.roles.keySet()) {
            int requiredSkillLevel = project.roles.get(skill);
            int bestDistanceToSkill = -2; // people with 2 or more skill points below requirement aren't candidates
            Contributor bestMatch = null;
            for (Contributor c : potentialContributors) {
                if (bestMatches.contains(c)) {
                    continue;
                }

                int currentSkillLevel = c.skills.getOrDefault(skill, 0);
                int currentDistanceToSkill = currentSkillLevel - requiredSkillLevel;

                if (currentSkillLevel == requiredSkillLevel && currentDistanceToSkill != bestDistanceToSkill) {
                    bestMatch = c;
                    break;
                }

                // Optimization: Allow people with skill level -1 of requirement, but they need a mentor
                if (bestDistanceToSkill < -1 && currentDistanceToSkill > bestDistanceToSkill) {
                    bestMatch = c;
                    bestDistanceToSkill = currentDistanceToSkill;
                    continue;
                }

                if (bestDistanceToSkill > 0 && currentDistanceToSkill < bestDistanceToSkill) {
                    bestMatch = c;
                    bestDistanceToSkill = currentDistanceToSkill;
                }

            }

            bestMatches.add(bestMatch);

        }

        return bestMatches;
    }

}
