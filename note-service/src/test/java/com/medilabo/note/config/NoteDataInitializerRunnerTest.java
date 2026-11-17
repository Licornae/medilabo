package com.medilabo.note.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;

import static org.assertj.core.api.Assertions.assertThat;

class NoteDataInitializerRunnerTest {

    @Test
    public void shouldBeACommandLineRunner() {

        assertThat(CommandLineRunner.class)
                .isAssignableFrom(NoteDataInitializer.class);
    }
}
