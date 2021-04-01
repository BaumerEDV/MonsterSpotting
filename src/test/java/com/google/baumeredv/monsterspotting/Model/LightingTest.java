package com.google.baumeredv.monsterspotting.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.baumeredv.monsterspotting.App;
import com.google.baumeredv.monsterspotting.model.MonsterSpottingModel;
import com.google.baumeredv.monsterspotting.model.entity.Lighting;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateLightingException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchLightingException;
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
public class LightingTest {

  private MonsterSpottingModel model;

  @BeforeEach
  public void createModel(){
    ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    this.model = context.getBean(MonsterSpottingModel.class);
  }

  @Nested
  class WhenAddingLighting{
    private final String LIGHTING_DESCRIPTION = "lighting description";

    @Nested
    class AnAddedLighting{

      private Lighting addedLighting;

      @BeforeEach
      public void createLighting() throws DuplicateLightingException {
        addedLighting = model.addLighting(new Lighting(LIGHTING_DESCRIPTION));
      }

      @Test
      public void isAddedToTheModel(){
        assertTrue(isLightingInModel(addedLighting));
      }

      @Test
      public void hasTheDescriptionItWasGiven(){
        assertEquals(LIGHTING_DESCRIPTION, addedLighting.description());
      }

      @Test
      public void canBeAddedAlongsideASecondOne() throws DuplicateLightingException {
        final String SECOND_LIGHTING_DESCRIPTION = LIGHTING_DESCRIPTION + " 2";
        Lighting secondLighting = model.addLighting(new Lighting(SECOND_LIGHTING_DESCRIPTION));

        assertTrue(isLightingInModel(addedLighting));
        assertTrue(isLightingInModel(secondLighting));
      }
    }

    @Nested
    class ALightingCannotBeAdded{

      @Test
      public void ifItsDecriptionIsEmpty(){
        assertThrows(IllegalArgumentException.class,
            () -> model.addLighting(new Lighting("")));
        assertThrows(IllegalArgumentException.class,
            () -> model.addLighting(new Lighting(null)));
      }

      @Test
      public void ifItIsNull(){
        assertThrows(IllegalArgumentException.class,
            () -> model.addLighting(null));
      }

      @Test
      public void ifItIsIdenticalToAnExistingLighting() throws DuplicateLightingException {
        Lighting lighting = model.addLighting(new Lighting(LIGHTING_DESCRIPTION));
        assertThrows(DuplicateLightingException.class, () -> model.addLighting(lighting));
      }
    }
  }

  @Nested
  class WhenDeletingLightings{

    private final String LIGHTING_TO_BE_DELETED_DESCRIPTION = "lighting to be deleted";

    @Test
    public void deletingNullThrows(){
      assertThrows(IllegalArgumentException.class,
          () -> model.deleteLighting(null));
    }

    @Test
    public void deletingANonexistentLightingThrows(){
      assertThrows(ThereIsNoSuchLightingException.class,
          () -> model.deleteLighting(new Lighting(LIGHTING_TO_BE_DELETED_DESCRIPTION)));
    }

    @Nested
    class AfterALightingWasAdded{

      private Lighting lighting;

      @BeforeEach
      public void createLighting() throws DuplicateLightingException {
        lighting = model.addLighting(new Lighting(LIGHTING_TO_BE_DELETED_DESCRIPTION));
      }

      @Test
      public void deletingTheLightingRemovesItFromTheModel() throws ThereIsNoSuchLightingException{
        model.deleteLighting(new Lighting(LIGHTING_TO_BE_DELETED_DESCRIPTION));
        assertFalse(isLightingInModel(lighting));
      }
    }
  }

  @Nested
  class LightingEquals{
    final String LIGHTING_DESCRIPTION = "lighting description";
    Lighting lighting;

    @BeforeEach
    public void createLighting(){
      lighting = new Lighting(LIGHTING_DESCRIPTION);
    }

    @Nested
    class LightingDoesNotEqual{

      @Test
      public void somethingThatIsNotALighting(){
        assertFalse(lighting.equals(1));
        assertFalse(lighting.equals("something"));
        assertFalse(lighting.equals(LIGHTING_DESCRIPTION));
      }

      @Test
      public void somethingThatIsNull(){
        assertFalse(lighting.equals(null));
      }

      @Test
      public void aDifferentLighting(){
        Lighting otherLighting = new Lighting(LIGHTING_DESCRIPTION + " 2");
        assertFalse(lighting.equals(otherLighting));
        assertFalse(otherLighting.equals(lighting));
      }
    }

    @Nested
    class LightingDoesEqual{

      @Test
      public void itself(){
        assertTrue(lighting.equals(lighting));
      }

      @Test
      public void anIdenticalCopyOfItself(){
        Lighting identicalCopy = new Lighting(LIGHTING_DESCRIPTION);
        assertTrue(lighting.equals(identicalCopy));
        assertTrue(identicalCopy.equals(lighting));
      }
    }
  }

  private boolean isLightingInModel(Lighting lightingInQuestion){
    Iterable<Lighting> allLightings = model.allLightings();
    for (Lighting lighting : allLightings){
      if (lighting.equals(lightingInQuestion)) {
        return true;
      }
    }
    return false;
  }

}
