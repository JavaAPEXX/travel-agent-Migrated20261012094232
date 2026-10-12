package org.trypticon.gradle.plugins.travelagent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.trypticon.gradle.plugins.travelagent.extension.TravelAgentExtension;
import org.trypticon.gradle.plugins.travelagent.extension.TravelAgentTaskExtension;
import org.trypticon.gradle.plugins.travelagent.task.TravelAgentArgumentProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TravelAgentPluginTest {

    @InjectMocks
    private TravelAgentPlugin travelAgentPlugin;

    @Mock
    private TravelAgentExtension globalExtension;

    @Mock
    private TravelAgentTaskExtension taskExtension;

    @BeforeEach
    void setUp() {
        travelAgentPlugin = new TravelAgentPlugin();
        travelAgentPlugin.apply(mock(Project.class));
        when(globalExtension.prefer(any(), any(), any())).thenReturn("default");
        when(taskExtension.getTravelAgentExtension()).thenReturn(globalExtension);
    }

    @Test
    @DisplayName("Given null project, when apply, then no exceptions")
    void givenNullProject_whenApply_thenNoExceptions() {
        travelAgentPlugin.apply(null);
    }

    @Test
    @DisplayName("Given default preferences, when apply, then no exceptions")
    void givenDefaultPreferences_whenApply_thenNoExceptions() {
        travelAgentPlugin.apply(mock(Project.class));
    }

    @Test
    @DisplayName("Given null preference, when apply, then no exceptions")
    void givenNullPreference_whenApply_thenNoExceptions() {
        when(globalExtension.prefer(any(), any(), any())).thenReturn(null);
        travelAgentPlugin.apply(mock(Project.class));
    }

    @Test
    @DisplayName("Given empty preference, when apply, then no exceptions")
    void givenEmptyPreference_whenApply_thenNoExceptions() {
        when(globalExtension.prefer(any(), any(), any())).thenReturn("");
        travelAgentPlugin.apply(mock(Project.class));
    }

    @Test
    @DisplayName("Given valid preferences, when apply, then no exceptions")
    void givenValidPreferences_whenApply_thenNoExceptions() {
        when(globalExtension.prefer("en", "US", "America/New_York")).thenReturn("en_US");
        travelAgentPlugin.apply(mock(Project.class));
    }

    @Test
    @DisplayName("Given null task extension, when apply, then no exceptions")
    void givenNullTaskExtension_whenApply_thenNoExceptions() {
        travelAgentPlugin.apply(mock(Project.class));
    }

    @Test
    @DisplayName("Given valid task extension, when apply, then no exceptions")
    void givenValidTaskExtension_whenApply_thenNoExceptions() {
        travelAgentPlugin.apply(mock(Project.class));
    }

    @Test
    @DisplayName("Given null travel agent extension, when apply, then no exceptions")
    void givenNullTravelAgentExtension_whenApply_thenNoExceptions() {
        travelAgentPlugin.apply(mock(Project.class));
    }

    @Test
    @DisplayName("Given valid travel agent extension, when apply, then no exceptions")
    void givenValidTravelAgentExtension_whenApply_thenNoExceptions() {
        travelAgentPlugin.apply(mock(Project.class));
    }

    @Test
    @DisplayName("Given null project, when apply, then no exceptions")
    void givenNullProject_whenApply_thenNoExceptions() {
        travelAgentPlugin.apply(null);
    }

    @Test
    @DisplayName("Given null project, when apply, then no exceptions")
    void givenNullProject_whenApply_thenNoExceptions() {
        travelAgentPlugin.apply(null);
    }

    @Test
    @DisplayName("Given null project, when apply, then no exceptions")
    void givenNullProject_whenApply