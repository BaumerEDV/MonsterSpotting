package com.google.baumeredv.monsterspotting.Model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.baumeredv.monsterspotting.App;
import com.google.baumeredv.monsterspotting.model.MonsterSpottingModel;
import com.google.baumeredv.monsterspotting.model.entity.Encounter;
import com.google.baumeredv.monsterspotting.model.entity.Lighting;
import com.google.baumeredv.monsterspotting.model.entity.Source;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateEncounterException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchEncounterException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EncounterTest {

  private MonsterSpottingModel model;

  @BeforeEach
  public void createModel() {
    ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    this.model = context.getBean(MonsterSpottingModel.class);
  }

  @Nested
  class WhenAddingEncounters {

    final String SOURCE_NAME = "Lost Mine of Phandelver";
    final Source SOURCE = new Source(SOURCE_NAME);
    final int SOURCE_PAGE = 6;
    final String LIGHTING_DESCRIPTION = "Daylight";
    final Lighting LIGHTING = new Lighting(LIGHTING_DESCRIPTION);
    final boolean PARTY_CAN_SURPRISE = false;
    final String NOTES = "some notes";
    final boolean PARTY_CAN_BE_SURPRISED = true;
    final boolean ADVENTURE_ENCOURAGES_SURPRISE = true;
    final int MAXIMUM_FLIGHT_HEIGHT = 999;
    final int ROOM_BOUNDING_BOX_WIDTH = -1;
    final int ROOM_BOUNDING_BOX_LENGTH = -1;
    final int MIN_ENCOUNTER_START_DISTANCE = -1;
    final int MAX_ENCOUNTER_START_DISTANCE = -1;
    final boolean ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL = false;

    @Nested
    class AnAddedEncounter {

      private Encounter addedEncounter;

      @BeforeEach
      public void createEncounter() throws DuplicateEncounterException {
        addedEncounter = model
            .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
                ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL));
      }

      @Test
      public void isAddedToTheModel() {
        assertTrue(isEncounterInModel(addedEncounter));
      }

      @Test
      public void hasTheDataItWasGiven() {
        assertEquals(SOURCE, addedEncounter.source());
        assertEquals(SOURCE_PAGE, addedEncounter.sourcePage());
        assertEquals(LIGHTING, addedEncounter.lighting());
        assertEquals(PARTY_CAN_SURPRISE, addedEncounter.partyCanSurprise());
        assertEquals(NOTES, addedEncounter.notes());
        assertEquals(PARTY_CAN_BE_SURPRISED, addedEncounter.partyCanBeSurprised());
        assertEquals(ADVENTURE_ENCOURAGES_SURPRISE, addedEncounter.adventureEncouragesSurprise());
        assertEquals(MAXIMUM_FLIGHT_HEIGHT, addedEncounter.maximumFlightHeight());
        assertEquals(ROOM_BOUNDING_BOX_WIDTH, addedEncounter.roomBoundingBoxWidth());
        assertEquals(ROOM_BOUNDING_BOX_LENGTH, addedEncounter.roomBoundingBoxLength());
        assertEquals(MIN_ENCOUNTER_START_DISTANCE, addedEncounter.minEncounterStartDistance());
        assertEquals(MAX_ENCOUNTER_START_DISTANCE, addedEncounter.maxEncounterStartDistance());
        assertEquals(ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL,
            addedEncounter.encounterIsResistantToGroundDefaultKill());
      }

      @Test
      public void canBeAddedAlongsideASecondOne() throws DuplicateEncounterException {
        Encounter secondEncounter = model
            .addEncounter(new Encounter(SOURCE, SOURCE_PAGE + 1, LIGHTING, PARTY_CAN_SURPRISE,
                NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
                ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL));

        assertTrue(isEncounterInModel(addedEncounter));
        assertTrue(isEncounterInModel(secondEncounter));
      }


    }

    @Nested
    class AnEncounterCannotBeAdded {

      @Test
      public void ifItsSourcesNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addEncounter(
                new Encounter(new Source(""), SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
        assertThrows(IllegalArgumentException.class,
            () -> model.addEncounter(
                new Encounter(new Source(null), SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsSourceIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addEncounter(new Encounter(null, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
                ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsSourcePageIsNegative() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addEncounter(new Encounter(SOURCE, -1, LIGHTING, PARTY_CAN_SURPRISE,
                NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
                ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsLightningDescriptionIsEmpty() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addEncounter(
                new Encounter(SOURCE, SOURCE_PAGE, new Lighting(""), PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
        assertThrows(IllegalArgumentException.class,
            () -> model.addEncounter(
                new Encounter(SOURCE, SOURCE_PAGE, new Lighting(null), PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsLightningIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addEncounter(new Encounter(SOURCE, SOURCE_PAGE, null, PARTY_CAN_SURPRISE,
                NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
                ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsNotesAreNull() {
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    null, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsMaximumFlightHeightIsANoncodedNegativeValue() {
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, -5,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsBoundingBoxDimensionsAreANoncodedNegativeValue() {
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    -5, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, -5, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsBoundingBoxDimensionsAreZero() {
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    0, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, 0, MIN_ENCOUNTER_START_DISTANCE,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }


      @Test
      public void ifItsEncounterStartingDistancesAreANoncodedNegativeValue() {
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, -5,
                    MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                    -5, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
      }

      @Test
      public void ifItsMinStartingDistanceIsLargerThanItsMaxStartingDistanceAndNeitherIsCoded() {
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, 7,
                    5, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
        assertDoesNotThrow(() -> model
            .addEncounter((new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
                ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, 7,
                -1, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL))));
        assertDoesNotThrow(() -> model
            .addEncounter((new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
                ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, -1,
                5, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL))));
        assertThrows(IllegalArgumentException.class,
            () -> model
                .addEncounter((new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                    NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                    MAXIMUM_FLIGHT_HEIGHT,
                    ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, -2,
                    -3, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL))));
      }

      @Test
      public void ifItIsIdenticalToAnExistingEncounter() throws DuplicateEncounterException {
        Encounter encounter = model
            .addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
                ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL));
        assertThrows(DuplicateEncounterException.class,
            () -> model.addEncounter(encounter));
      }
    }
  }


  @Nested
  class WhenDeletingEncounters {

    final String SOURCE_NAME = "Lost Mine of Phandelver";
    final Source SOURCE = new Source(SOURCE_NAME);
    final int SOURCE_PAGE = 6;
    final String LIGHTING_DESCRIPTION = "Daylight";
    final Lighting LIGHTING = new Lighting(LIGHTING_DESCRIPTION);
    final boolean PARTY_CAN_SURPRISE = false;
    final String NOTES = "some notes";
    final boolean PARTY_CAN_BE_SURPRISED = true;
    final boolean ADVENTURE_ENCOURAGES_SURPRISE = true;
    final int MAXIMUM_FLIGHT_HEIGHT = 999;
    final int ROOM_BOUNDING_BOX_WIDTH = -1;
    final int ROOM_BOUNDING_BOX_LENGTH = -1;
    final int MIN_ENCOUNTER_START_DISTANCE = -1;
    final int MAX_ENCOUNTER_START_DISTANCE = -1;
    final boolean ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL = false;

    @Test
    public void deletingNullThrows() {
      assertThrows(IllegalArgumentException.class,
          () -> model.deleteEncounter(null));
    }

    @Test
    public void deletingANonexistentEncounterThrows() {
      assertThrows(ThereIsNoSuchEncounterException.class,
          () -> model
              .deleteEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
                  NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
                  MAXIMUM_FLIGHT_HEIGHT,
                  ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
                  MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL)));
    }

    @Nested
    class AfterAnEncounterWasAdded {
      private Encounter encounter;

      @BeforeEach
      public void createEncounter() throws DuplicateEncounterException {
        encounter = model.addEncounter(new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
            MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL));
      }

      @Test
      public void deletingTheEncounterRemovesItFromTheModel()
          throws ThereIsNoSuchEncounterException {
        model.deleteEncounter(encounter);
        assertFalse(isEncounterInModel(encounter));
      }
    }
  }

  @Nested
  class EncounterEquals {

    final String SOURCE_NAME = "Lost Mine of Phandelver";
    final Source SOURCE = new Source(SOURCE_NAME);
    final int SOURCE_PAGE = 6;
    final String LIGHTING_DESCRIPTION = "Daylight";
    final Lighting LIGHTING = new Lighting(LIGHTING_DESCRIPTION);
    final boolean PARTY_CAN_SURPRISE = false;
    final String NOTES = "some notes";
    final boolean PARTY_CAN_BE_SURPRISED = true;
    final boolean ADVENTURE_ENCOURAGES_SURPRISE = true;
    final int MAXIMUM_FLIGHT_HEIGHT = 999;
    final int ROOM_BOUNDING_BOX_WIDTH = -1;
    final int ROOM_BOUNDING_BOX_LENGTH = -1;
    final int MIN_ENCOUNTER_START_DISTANCE = -1;
    final int MAX_ENCOUNTER_START_DISTANCE = -1;
    final boolean ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL = false;

    Encounter encounter;

    @BeforeEach
    public void createEncounter() {
      encounter = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
          NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
          ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
          MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
    }

    @Nested
    class EncounterDoesNotEqual {

      @Test
      public void somethingThatIsNotAnEncounter() {
        assertFalse(encounter.equals(1));
        assertFalse(encounter.equals("something"));
        assertFalse(encounter.equals(new Object()));
      }

      @Test
      public void somethingThatIsNull() {
        assertFalse(encounter.equals(null));
      }

      @Test
      public void aDifferentEncounter() {
        Encounter[] encounters = new Encounter[13];
        encounters[0] = new Encounter(new Source(SOURCE_NAME + "2 "), SOURCE_PAGE, LIGHTING,
            PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[1] = new Encounter(SOURCE, SOURCE_PAGE + 2, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[2] = new Encounter(SOURCE, SOURCE_PAGE,
            new Lighting(LIGHTING_DESCRIPTION + " 2"), PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[3] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, !PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[4] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES + " 2", PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
            MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[5] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, !PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[6] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, !ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[7] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE,
            MAXIMUM_FLIGHT_HEIGHT + 10,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[8] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH + 10, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[9] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH + 10, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[10] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE + 10,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[11] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE + 10, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        encounters[12] = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, !ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        for (Encounter otherEncounter : encounters) {
          assertFalse(encounter.equals(otherEncounter));
        }
      }
    }

    @Nested
    class SourceDoesEqual {

      @Test
      public void itself() {
        assertTrue(encounter.equals(encounter));
      }

      @Test
      public void anIdenticalCopyOfItself() {
        Encounter identicalCopy = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
            NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
            ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
            MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);
        assertTrue(encounter.equals(identicalCopy));
        assertTrue(identicalCopy.equals(encounter));
      }
    }
  }


  private boolean isEncounterInModel(Encounter encounterInQuestion) {
    Iterable<Encounter> encounters = model.allEncounters();
    for (Encounter encounter : encounters) {
      if (encounter.equals(encounterInQuestion)) {
        return true;
      }
    }
    return false;
  }


}
