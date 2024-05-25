package com.feefo.app;

import static org.apache.commons.lang3.StringUtils.getLevenshteinDistance;

public class Normaliser {

    private static final String[] NORMALIZED_JOB_TITLES = {
            "Architect",
            "Software engineer",
            "Quantity surveyor",
            "Accountant"
    };

    private String closestMatch;
    private double maxDistance;

    public String normalise(String jobTitle) {

        maxDistance = Double.MAX_VALUE;

        for (String normalizedJobTitle : NORMALIZED_JOB_TITLES) {
            findClosestMatch(jobTitle, normalizedJobTitle);
        }

        return closestMatch;
    }

    private void findClosestMatch(String input, String normalizedJobTitle) {

        double distance = getLevenshteinDistance(input.toLowerCase(), normalizedJobTitle.toLowerCase());

        if (distance < maxDistance) {
            maxDistance = distance;
            closestMatch = normalizedJobTitle;
        }
    }
}
