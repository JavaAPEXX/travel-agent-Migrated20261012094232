package org.trypticon.gradle.plugins.travelagent;

import com.google.common.collect.ImmutableList;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.specs.Spec;
import org.gradle.api.specs.Specs;
import org.gradle.api.tasks.Input;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import java.security.SecureRandom;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TravelAgentTaskExtensionTest {

    @Mock
    private TravelAgentExtension globalExtension;

    @InjectMocks
    private TravelAgentTaskExtension travelAgentTaskExtension;

    @Mock
    private ListProperty<Trip> availableTrips;

    @Mock
    private Property<Boolean> enabled;

    @Mock
    private Provider<List<Trip>> acceptableTrips;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(globalExtension.getEnabled()).thenReturn(true);
        when(globalExtension.getAvailableTrips()).thenReturn(ImmutableList.of(
                new Trip("Trip 1", "Destination 1"),
                new Trip("Trip 2", "Destination 2"),
                new Trip("Trip 3", "Destination 3")
        ));
        when(globalExtension.getFilter()).thenReturn(Specs.SATISFIES_ALL);
    }

    @Test
    @DisplayName("Given enabled is true, when getEnabled is called, then enabled is true")
    public void givenEnabledTrue_whenGetEnabled_thenEnabledIsTrue() {
        assertTrue(travelAgentTaskExtension.getEnabled().get());
    }

    @Test
    @DisplayName("Given availableTrips is not empty, when getAvailableTrips is called, then availableTrips is not empty")
    public void givenAvailableTripsNotEmpty_whenGetAvailableTrips_thenAvailableTripsIsNotEmpty() {
        assertFalse(travelAgentTaskExtension.getAvailableTrips().get().isEmpty());
    }

    @Test
    @DisplayName("Given filter is satisfied by all trips, when getAcceptableTrips is called, then acceptableTrips is not empty")
    public void givenFilterSatisfiedByAllTrips_whenGetAcceptableTrips_thenAcceptableTripsIsNotEmpty() {
        assertTrue(travelAgentTaskExtension.getAcceptableTrips().get().size() > 0);
    }

    @Test
    @DisplayName("Given knownFailing is called with a spec that matches a trip, when getAcceptableTrips is called, then acceptableTrips is empty")
    public void givenKnownFailingWithMatchingTrip_whenGetAcceptableTrips_thenAcceptableTripsIsEmpty() {
        travelAgentTaskExtension.knownFailing(trip -> trip.getName().equals("Trip 1"));
        assertTrue(travelAgentTaskExtension.getAcceptableTrips().get().isEmpty());
    }

    @Test
    @DisplayName("Given suggestTrip is called, then a trip is selected randomly from acceptableTrips")
    public void givenSuggestTripIsCalled_thenATripIsSelectedRandomly() {
        List<Trip> acceptableTrips = travelAgentTaskExtension.getAcceptableTrips().get();
        int randomIndex = new SecureRandom().nextInt(acceptableTrips.size());
        Trip selectedTrip = travelAgentTaskExtension.suggestTrip();
        assertTrue(acceptableTrips.contains(selectedTrip));
    }
}