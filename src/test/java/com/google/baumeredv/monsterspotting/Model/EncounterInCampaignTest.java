package com.google.baumeredv.monsterspotting.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.baumeredv.monsterspotting.App;
import com.google.baumeredv.monsterspotting.model.MonsterSpottingModel;
import com.google.baumeredv.monsterspotting.model.entity.Encounter;
import com.google.baumeredv.monsterspotting.model.entity.EncounterInCampaign;
import com.google.baumeredv.monsterspotting.model.entity.Lighting;
import com.google.baumeredv.monsterspotting.model.entity.Source;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateEncounterException;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateEncounterInCampaignException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchEncounterException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchEncounterInCampaignException;
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
public class EncounterInCampaignTest {

  final int PARTY_LEVEL = 1;
  final float EV_TO_HAPPEN_AT_THIS_LEVEL = 1f;
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
  final Encounter ENCOUNTER = new Encounter(SOURCE, SOURCE_PAGE, LIGHTING, PARTY_CAN_SURPRISE,
      NOTES, PARTY_CAN_BE_SURPRISED, ADVENTURE_ENCOURAGES_SURPRISE, MAXIMUM_FLIGHT_HEIGHT,
      ROOM_BOUNDING_BOX_WIDTH, ROOM_BOUNDING_BOX_LENGTH, MIN_ENCOUNTER_START_DISTANCE,
      MAX_ENCOUNTER_START_DISTANCE, ENCOUNTER_IS_RESISTANT_TO_GROUND_DEFAULT_KILL);

  private MonsterSpottingModel model;

  @BeforeEach
  public void createModel() {
    ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    this.model = context.getBean(MonsterSpottingModel.class);
  }

  @Nested
  class WhenAddingEncounterInCampaigns {

    @Nested
    class AnAddedEncounterInCampaign {

      private EncounterInCampaign encounterInCampaign;

      @BeforeEach
      public void createEncounterInCampaign()
          throws DuplicateEncounterException, ThereIsNoSuchEncounterException, DuplicateEncounterInCampaignException {
        model.addEncounter(ENCOUNTER);
        encounterInCampaign = model.addEncounterInCampaign(
            new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL));
      }

      @Test
      public void isAddedToTheModel() {
        assertTrue(isEncounterInCampaignInModel(encounterInCampaign));
      }

      @Test
      public void hasTheDataItWasGiven() {
        assertEquals(ENCOUNTER, encounterInCampaign.encounter());
        assertEquals(EV_TO_HAPPEN_AT_THIS_LEVEL, encounterInCampaign.evToHappenAtThisLevel());
        assertEquals(PARTY_LEVEL, encounterInCampaign.partyLevel());
      }

      @Test
      public void canBeAddedAlongsideASecondOne()
          throws ThereIsNoSuchEncounterException, DuplicateEncounterInCampaignException {
        EncounterInCampaign secondEncounterInCampaign = model.addEncounterInCampaign(
            new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL + 2));
        assertTrue(isEncounterInCampaignInModel(encounterInCampaign));
        assertTrue(isEncounterInCampaignInModel(secondEncounterInCampaign));
      }
    }

    @Nested
    class AnEncounterInCampaignCannotBeAdded {

      @Test
      public void ifItsEncounterIsNotInTheModel() {
        assertThrows(ThereIsNoSuchEncounterException.class,
            () -> model.addEncounterInCampaign(
                new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL)));
      }

      @Test
      public void ifItIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addEncounterInCampaign(null));
      }

      @Test
      public void ifThatEncounterAlreadyExistsAtThatLevel()
          throws DuplicateEncounterException, ThereIsNoSuchEncounterException, DuplicateEncounterInCampaignException {
        model.addEncounter(ENCOUNTER);
        model.addEncounterInCampaign(
            new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL));
        assertThrows(DuplicateEncounterInCampaignException.class,
            () -> model.addEncounterInCampaign(
                new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL + 1, PARTY_LEVEL)));
      }
    }
  }

  @Nested
  class WhenDeletingEncounterInCampaigns {

    @Test
    public void deletingNullThrows() {
      assertThrows(IllegalArgumentException.class,
          () -> model.deleteEncounterInCampaign(null));
    }

    @Test
    public void deletingANonexistentEncounterInCampaignThrows() {
      assertThrows(ThereIsNoSuchEncounterInCampaignException.class,
          () -> model.deleteEncounterInCampaign(
              new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL)
          ));
    }

    @Nested
    class AfternAnEncounterInCampaignWasAdded {
      private EncounterInCampaign encounterInCampaign;

      @BeforeEach
      public void createEncounterInCampaign()
          throws DuplicateEncounterException, DuplicateEncounterInCampaignException, ThereIsNoSuchEncounterException {
        Encounter encounter = model.addEncounter(ENCOUNTER);
        encounterInCampaign = model.addEncounterInCampaign(
            new EncounterInCampaign(encounter, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL)
        );
      }

      @Test
      public void deletingTheEncounterInCampaignRemovesItFromTheModel()
          throws ThereIsNoSuchEncounterInCampaignException {
        model.deleteEncounterInCampaign(encounterInCampaign);
        assertFalse(isEncounterInCampaignInModel(encounterInCampaign));
      }
    }
  }


  @Nested
  class EncounterInCampaignEquals {

    EncounterInCampaign encounterInCampaign;

    @BeforeEach
    public void createEncounterInCampaign() {
      encounterInCampaign =
          new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL);
    }

    @Nested
    class EncounterInCampaignDoesNotEqual {

      @Test
      public void somethingThatIsNotAnEncounterInCampaign() {
        assertFalse(encounterInCampaign.equals(1));
        assertFalse(encounterInCampaign.equals("something"));
        assertFalse(encounterInCampaign.equals(new Object()));
      }

      @Test
      public void somethingThatIsNull() {
        assertFalse(encounterInCampaign.equals(null));
      }

      @Test
      public void aDifferentEncounterInCampaign() {
        EncounterInCampaign otherEncounterInCampaign =
            new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL + 1);
        assertFalse(encounterInCampaign.equals(otherEncounterInCampaign));
        assertFalse(otherEncounterInCampaign.equals(encounterInCampaign));
      }
    }

    @Nested
    class EncounterInCampaignDoesEqual {

      @Test
      public void itself() {
        assertTrue(encounterInCampaign.equals(encounterInCampaign));
      }

      @Test
      public void anIdenticalCopyOfItself() {
        EncounterInCampaign identicalCopy =
            new EncounterInCampaign(ENCOUNTER, EV_TO_HAPPEN_AT_THIS_LEVEL, PARTY_LEVEL);
        assertTrue(identicalCopy.equals(encounterInCampaign));
        assertTrue(encounterInCampaign.equals(identicalCopy));
      }
    }


  }

  private boolean isEncounterInCampaignInModel(EncounterInCampaign encounterInCampaignInQuestion) {
    Iterable<EncounterInCampaign> encounterInCampaigns = model.allEncounterInCampaigns();
    for (EncounterInCampaign encounterInCampaign : encounterInCampaigns) {
      if (encounterInCampaign.equals(encounterInCampaignInQuestion)) {
        return true;
      }
    }
    return false;
  }
}
