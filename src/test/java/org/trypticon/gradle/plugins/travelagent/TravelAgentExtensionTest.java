package org.trypticon.gradle.plugins.travelagent;

import com.google.common.collect.ImmutableList;
import groovy.json.JsonSlurper;
import org.gradle.api.Transformer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.specs.Spec;
import org.gradle.api.specs.Specs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.io.TempDir;
import org.trypticon.gradle.plugins.travelagent.model.Trip;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TempDir Path tempDir
public class TravelAgentExtensionTest {

    @InjectMocks
    private TravelAgentExtension travelAgentExtension;

    @Mock
    private ObjectFactory objectFactory;

    @Mock
    private ProviderFactory providerFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        travelAgentExtension = new TravelAgentExtension(objectFactory, providerFactory);
    }

    @Test
    @DisplayName("Given enabled is true, when getEnabled is called, then enabled is true")
    public void givenEnabledTrue_whenGetEnabled_thenEnabledIsTrue() {
        assertTrue(travelAgentExtension.getEnabled().get());
    }

    @Test
    @DisplayName("Given enabled is false, when getEnabled is called, then enabled is false")
    public void givenEnabledFalse_whenGetEnabled_thenEnabledIsFalse() {
        travelAgentExtension.getEnabled().set(false);
        assertFalse(travelAgentExtension.getEnabled().get());
    }

    @Test
    @DisplayName("Given availableTrips is empty, when getAvailableTrips is called, then returns empty list")
    public void givenAvailableTripsEmpty_whenGetAvailableTrips_thenReturnsEmptyList() {
        travelAgentExtension.getAvailableTrips().set(ImmutableList.of());
        assertTrue(travelAgentExtension.getAvailableTrips().isEmpty());
    }

    @Test
    @DisplayName("Given availableTrips is not empty, when getAvailableTrips is called, then returns non-empty list")
    public void givenAvailableTripsNotEmpty_whenGetAvailableTrips_thenReturnsNonEmptyList() {
        travelAgentExtension.getAvailableTrips().set(ImmutableList.of(new Trip("en", "US", "UTC")));
        assertFalse(travelAgentExtension.getAvailableTrips().isEmpty());
    }

    @Test
    @DisplayName("Given knownFailing spec matches a trip, when getFilter is called, then filter includes the trip")
    public void givenKnownFailingSpecMatchesTrip_whenGetFilter_thenFilterIncludesTrip() {
        travelAgentExtension.knownFailing(trip -> trip.getLanguage().equals("en"));
        assertTrue(travelAgentExtension.getFilter().isSatisfiedBy(new Trip("en", "US", "UTC")));
    }

    @Test
    @DisplayName("Given knownFailing spec does not match a trip, when getFilter is called, then filter excludes the trip")
    public void givenKnownFailingSpecDoesNotMatchTrip_whenGetFilter_thenFilterExcludesTrip() {
        travelAgentExtension.knownFailing(trip -> trip.getLanguage().equals("fr"));
        assertFalse(travelAgentExtension.getFilter().isSatisfiedBy(new Trip("en", "US", "UTC")));
    }

    @Test
    @DisplayName("Given prefer method sets language, when getFilter is called, then filter includes trips with the language")
    public void givenPreferMethod