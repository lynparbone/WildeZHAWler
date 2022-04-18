package com.wildezhawer.hashcode.service;

import com.wildezhawer.hashcode.model.Contributor;
import com.wildezhawer.hashcode.model.Project;
import com.wildezhawer.hashcode.model.Skill;

import java.util.*;

public class BestProjectMatchFinder {
    // Returns contributor for each role, sorted by role (i.e. entry 0 is for role 0 etc.)
    public static List<Contributor> findBestMatchesForProject(Project project, List<Contributor> potentialContributors) {
        List<Contributor> bestMatches = new ArrayList<>();
        for (Skill skill : project.roles) {
            int requiredSkillLevel = skill.level;
            int bestDistanceToSkill = -2; // people with 2 or more skill points below requirement aren't candidates
            Contributor bestMatch = null;
            Contributor secondBestMatch = null;

            for (Contributor contributor : potentialContributors) {
                // Contributors may have other required skills. In this case they cannot be staffed twice.
                if (bestMatches.contains(contributor)) {
                    continue;
                }

                int currentSkillLevel = contributor.skills.getOrDefault(skill.name, 0);
                if (currentSkillLevel < requiredSkillLevel) {
                    continue;
                }

                // Exact matches are better because they get level ups
                if (currentSkillLevel == requiredSkillLevel) {
                    bestMatch = contributor;
                    break;
                }

                if (secondBestMatch == null) {
                    secondBestMatch = contributor;
                }

                if (currentSkillLevel < secondBestMatch.skills.getOrDefault(skill.name, 0)) {
                    secondBestMatch = contributor;
                }

            }

            if (bestMatch == null) {
                bestMatch = secondBestMatch;
            }

            // If there is no bestMatch for a skill, return an empty list, i.e. no matches
            if (bestMatch == null) {
                return List.of();
            }

            bestMatches.add(bestMatch);
            }


        return bestMatches;
    }

}
