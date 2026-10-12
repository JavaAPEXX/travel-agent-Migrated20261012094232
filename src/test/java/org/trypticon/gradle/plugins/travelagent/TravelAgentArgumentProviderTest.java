package org.trypticon.gradle.plugins.travelagent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;
import org.gradle.api.tasks.Nested;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.process.CommandLineArgumentProvider;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TravelAgentArgumentProviderTest {

    @Mock
    private TravelAgentTaskExtension extension;

    @InjectMocks
    private TravelAgentArgumentProvider travelAgentArgumentProvider;

    @BeforeEach
    void setUp() {
        when(extension.getEnabled()).thenReturn(true);
        when(extension.suggestTrip()).thenReturn(new Trip("en", "US", "America/New_York"));
    }

    @Test
    @DisplayName("Given enabled and suggested trip, when asArguments is called, then returns trip arguments")
    void givenEnabledAndSuggestedTrip_whenAsArgumentsIsCalled_thenReturnsTripArguments() {
        List<String> arguments = travelAgentArgumentProvider.asArguments();
        assertNotNull(arguments);
        assertEquals(4, arguments.size());
        assertEquals("-Ptravelagent.language=en", arguments.get(0));
        assertEquals("-Ptravelagent.country=US", arguments.get(1));
        assertEquals("-Ptravelagent.timezone=America/New_York", arguments.get(2));
        assertEquals("-Ptravelagent.language=en -Ptravelagent.country=US -Ptravelagent.timezone=America/New_York", arguments.get(3));
    }

    @Test
    @DisplayName("Given enabled and suggested trip, when getName is called, then returns 'travelAgent'")
    void givenEnabledAndSuggestedTrip_whenGetNameIsCalled_thenReturnsTravelAgent() {
        String name = travelAgentArgumentProvider.getName();
        assertEquals("travelAgent", name);
    }

    @Test
    @DisplayName("Given disabled, when asArguments is called, then returns empty list")
    void givenDisabled_whenAsArgumentsIsCalled_thenReturnsEmptyList() {
        when(extension.getEnabled()).thenReturn(false);
        List<String> arguments = travelAgentArgumentProvider.asArguments();
        assertTrue(arguments.isEmpty());
    }

    @Test
    @DisplayName("Given suggested trip with language 'en', country 'US', and timeZone 'America/New_York', when asArguments is called, then logs trip details")
    void givenSuggestedTripWithLanguageUSCountryNY_whenAsArgumentsIsCalled_thenLogsTripDetails() {
        verify(extension, times(1)).suggestTrip();
        verifyLogging();
    }

    @Test
    @DisplayName("Given suggested trip with language 'en', country 'US', and timeZone 'America/New_York', when asArguments is called, then logs trip details with correct language and country")
    void givenSuggestedTripWithLanguageUSCountryNY_whenAsArgumentsIsCalled_thenLogsTripDetailsWithCorrectLanguageAndCountry() {
        verifyLogging();
        assertEquals("en", travelAgentArgumentProvider.asArguments().get(0).substring(14, 16));
        assertEquals("US", travelAgentArgumentProvider.asArguments().get(1).substring(14, 16));
    }

    @Test
    @DisplayName("Given suggested trip with language 'en', country 'US', and timeZone 'America/New_York', when asArguments is called, then logs trip details with correct timeZone")
    void givenSuggestedTripWithLanguageUSCountryNY_whenAsArgumentsIsCalled_thenLogsTripDetailsWithCorrectTimeZone() {
        verifyLogging();
        assertEquals("America/New_York", travelAgent