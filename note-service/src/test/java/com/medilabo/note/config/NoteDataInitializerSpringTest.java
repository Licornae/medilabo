package com.medilabo.note.config;

import com.medilabo.note.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class NoteDataInitializerSpringTest {

    @Configuration
    static class TestConfig {

        @Bean
        NoteRepository noteRepository() {
            return mock(NoteRepository.class);
        }
    }

    @Test
    public void shouldBeRegisteredOutsideTestProfile() {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {

            context.getEnvironment().setActiveProfiles("dev");

            context.register(TestConfig.class);
            context.register(NoteDataInitializer.class);

            context.refresh();

            assertThat(context.getBeansOfType(NoteDataInitializer.class)).hasSize(1);
        }
    }

    @Test
    public void shouldNotBeRegisteredWithTestProfile() {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {

            context.getEnvironment().setActiveProfiles("test");

            context.register(TestConfig.class);
            context.register(NoteDataInitializer.class);

            context.refresh();

            assertThat(context.getBeansOfType(NoteDataInitializer.class)).isEmpty();
        }
    }
}