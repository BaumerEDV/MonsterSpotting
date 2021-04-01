package com.google.baumeredv.monsterspotting.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.baumeredv.monsterspotting.App;
import com.google.baumeredv.monsterspotting.model.MonsterSpottingModel;
import com.google.baumeredv.monsterspotting.model.entity.Monster;
import com.google.baumeredv.monsterspotting.model.entity.Source;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateMonsterException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchMonsterException;
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
public class MonsterTest {

  private MonsterSpottingModel model;

  @BeforeEach
  public void createModel() {
    ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    this.model = context.getBean(MonsterSpottingModel.class);
  }

  @Nested
  class WhenAddingMonsters {

    final String MONSTER_NAME = "Goblin";
    final Source SOURCE = new Source("Basic Rules");
    final int SOURCE_PAGE = 138;
    private Monster addedMonster;

    @Nested
    class AnAddedMonster {

      @BeforeEach
      public void createMonster() throws DuplicateMonsterException {
        addedMonster = model.addMonster(new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE));
      }

      @Test
      public void isAddedToTheModel() {
        assertTrue(isMonsterInModel(addedMonster));
      }

      @Test
      public void hasTheNameItWasGiven() {
        assertEquals(MONSTER_NAME, addedMonster.name());
      }

      @Test
      public void hasTheSourceItWasGiven() {
        assertEquals(SOURCE, addedMonster.source());
      }

      @Test
      public void hasTheSourcePageItWasGiven() {
        assertEquals(SOURCE_PAGE, addedMonster.sourcePage());
      }

      @Test
      public void canBeAddedAlongsideASecondOne() throws DuplicateMonsterException {
        final String SECOND_MONSTER_NAME = MONSTER_NAME + " 2";
        Monster secondMonster = model
            .addMonster(new Monster(SECOND_MONSTER_NAME, SOURCE, SOURCE_PAGE));

        assertTrue(isMonsterInModel(addedMonster));
        assertTrue(isMonsterInModel(secondMonster));
      }
    }

    @Nested
    class AMonsterCannotBeAdded {

      @Test
      public void ifItsNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addMonster(new Monster("", SOURCE, SOURCE_PAGE)));
        assertThrows(IllegalArgumentException.class,
            () -> model.addMonster(new Monster(null, SOURCE, SOURCE_PAGE)));
      }

      @Test
      public void ifItsSourceNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addMonster(new Monster(MONSTER_NAME, new Source(""), SOURCE_PAGE)));
        assertThrows(IllegalArgumentException.class,
            () -> model.addMonster(new Monster(MONSTER_NAME, new Source(null), SOURCE_PAGE)));
      }

      @Test
      public void ifItsSourcePageIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addMonster(new Monster(MONSTER_NAME, SOURCE, 0)));
        assertThrows(IllegalArgumentException.class,
            () -> model.addMonster(new Monster(MONSTER_NAME, SOURCE, -5)));
      }

      @Test
      public void ifItIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addMonster(null));
      }

      @Test
      public void ifItsSourceIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> model.addMonster(new Monster(MONSTER_NAME, null, SOURCE_PAGE)));
      }

      @Test
      public void ifItIsIdenticalToAnExistingMonster() throws DuplicateMonsterException {
        Monster monster = model.addMonster(new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE));
        assertThrows(DuplicateMonsterException.class, () -> model.addMonster(monster));
      }


    }
  }

  @Nested
  class WhenDeletingMonsters {
    final String MONSTER_NAME = "Goblin";
    final Source SOURCE = new Source("Basic Rules");
    final int SOURCE_PAGE = 138;

    @Test
    public void deletingNullThrows(){
      assertThrows(IllegalArgumentException.class,
          () -> model.deleteMonster(null));
    }

    @Test
    public void deletingANonexistentMonsterThrows(){
      assertThrows(ThereIsNoSuchMonsterException.class,
          () -> model.deleteMonster(new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE)));
    }

    @Nested
    class AfterAMonsterWasAdded {
      private Monster monster;

      @BeforeEach
      public void createMonster() throws DuplicateMonsterException {
        monster = model.addMonster(new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE));
      }

      @Test
      public void deletingTheMonsterRemovesItFromTheModel() throws ThereIsNoSuchMonsterException {
        model.deleteMonster(monster);
        assertFalse(isMonsterInModel(monster));
      }
    }

  }


  @Nested
  class MonsterEquals {

    final String MONSTER_NAME = "Goblin";
    final Source SOURCE = new Source("Basic Rules");
    final int SOURCE_PAGE = 138;
    Monster monster;

    @BeforeEach
    public void createMonster() {
      monster = new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE);
    }

    @Nested
    class MonsterDoesNotEqual {

      @Test
      public void somethingThatIsNotAMonster() {
        assertFalse(monster.equals(1));
        assertFalse(monster.equals("something"));
        assertFalse(monster.equals(new Object()));
      }

      @Test
      public void somethingThatIsNull() {
        assertFalse(monster.equals(null));
      }

      @Test
      public void aDifferentMonster() {
        Monster[] otherMonsters = new Monster[3];
        otherMonsters[0] = new Monster(MONSTER_NAME + "2", SOURCE, SOURCE_PAGE);
        otherMonsters[1] = new Monster(MONSTER_NAME, new Source(SOURCE.name() + "2"), SOURCE_PAGE);
        otherMonsters[2] = new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE + 1);
        for (Monster otherMonster : otherMonsters) {
          assertFalse(monster.equals(otherMonster));
          assertFalse(otherMonster.equals(monster));
        }
      }

      @Nested
      class MonsterDoesEqual {

        @Test
        public void itself() {
          assertTrue(monster.equals(monster));
        }

        @Test
        public void anIdenticalCopyOfItself() {
          Monster identicalCopy = new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE);
          assertTrue(monster.equals(identicalCopy));
          assertTrue(identicalCopy.equals(monster));
        }
      }

    }

  }

  private boolean isMonsterInModel(Monster monsterInQuestion) {
    Iterable<Monster> monstersInModel = model.allMonsters();
    for (Monster monster : monstersInModel) {
      if (monster.equals(monsterInQuestion)) {
        return true;
      }
    }
    return false;
  }

}
