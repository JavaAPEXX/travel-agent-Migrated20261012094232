package org.trypticon.gradle.plugins.travelagent;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripTest {

    @InjectMocks
    private Trip trip;

    @Mock
    private Serializable mockSerializable;

    @BeforeEach
    public void setUp() {
        trip = new Trip("en", "US", "America/New_York");
    }

    @Test
    @DisplayName("Given language is 'en', when getLanguage, then return 'en'")
    public void givenLanguageIsEN_whenGetLanguage_thenReturnEN() {
        assertEquals("en", trip.getLanguage());
    }

    @Test
    @DisplayName("Given country is 'US', when getCountry, then return 'US'")
    public void givenCountryIsUS_whenGetCountry_thenReturnUS() {
        assertEquals("US", trip.getCountry());
    }

    @Test
    @DisplayName("Given timeZone is 'America/New_York', when getTimeZone, then return 'America/New_York'")
    public void givenTimeZoneIsAmericaNewYork_whenGetTimeZone_thenReturnAmericaNewYork() {
        assertEquals("America/New_York", trip.getTimeZone());
    }

    @Test
    @DisplayName("Given language is null, when getLanguage, then throw IllegalArgumentException")
    public void givenLanguageIsNull_whenGetLanguage_thenThrowIllegalArgumentException() {
        trip = new Trip(null, "US", "America/New_York");
        assertThrows(IllegalArgumentException.class, trip::getLanguage);
    }

    @Test
    @DisplayName("Given country is null, when getCountry, then throw IllegalArgumentException")
    public void givenCountryIsNull_whenGetCountry_thenThrowIllegalArgumentException() {
        trip = new Trip("en", null, "America/New_York");
        assertThrows(IllegalArgumentException.class, trip::getCountry);
    }

    @Test
    @DisplayName("Given timeZone is null, when getTimeZone, then throw IllegalArgumentException")
    public void givenTimeZoneIsNull_whenGetTimeZone_thenThrowIllegalArgumentException() {
        trip = new Trip("en", "US", null);
        assertThrows(IllegalArgumentException.class, trip::getTimeZone);
    }

    @Test
    @DisplayName("Given language is null, country is null, timeZone is null, when getLanguage, then throw IllegalArgumentException")
    public void givenLanguageIsNullCountryIsNullTimeZoneIsNull_whenGetLanguage_thenThrowIllegalArgumentException() {
        trip = new Trip(null, null, null);
        assertThrows(IllegalArgumentException.class, trip::getLanguage);
    }

    @Test
    @DisplayName("Given language is empty, when getLanguage, then throw IllegalArgumentException")
    public void givenLanguageIsEmpty_whenGetLanguage_thenThrowIllegalArgumentException() {
        trip = new Trip("", "US", "America/New_York");
        assertThrows(IllegalArgumentException.class, trip::getLanguage);
    }

    @Test
    @DisplayName("Given country is empty, when getCountry, then throw IllegalArgumentException")
    public void givenCountryIsEmpty_whenGetCountry_thenThrowIllegalArgumentException() {
        trip = new Trip("en", "", "America/New_York");
        assertThrows(IllegalArgumentException.class, trip::getCountry);
    }

    @Test
    @DisplayName("Given timeZone is empty, when getTimeZone, then throw IllegalArgumentException")
    public void givenTimeZoneIsEmpty_whenGetTimeZone_thenThrowIllegalArgumentException() {
        trip = new Trip("en", "US", "");
        assertThrows(IllegalArgumentException.class, trip::getTimeZone);
    }

    @Test
    @DisplayName("Given language is empty, country is empty, timeZone is empty, when