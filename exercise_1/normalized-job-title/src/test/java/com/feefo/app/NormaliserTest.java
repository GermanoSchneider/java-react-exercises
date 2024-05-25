package com.feefo.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NormaliserTest {

    private final Normaliser normaliser = new Normaliser();

    @Test
    public void shouldNormaliseAJobTitle() {

        String jobTitle = "Java Engineer";

        String normalizedJobTitle = normaliser.normalise(jobTitle);

        String expectedNormalizedJobTitle = "Software engineer";

        assertEquals(expectedNormalizedJobTitle, normalizedJobTitle);
    }
}
