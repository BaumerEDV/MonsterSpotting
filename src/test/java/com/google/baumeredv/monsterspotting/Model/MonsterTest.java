package com.google.baumeredv.monsterspotting.Model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.baumeredv.monsterspotting.App;
import com.google.baumeredv.monsterspotting.model.MonsterSpottingModel;
import com.google.baumeredv.monsterspotting.model.entity.Monster;
import com.google.baumeredv.monsterspotting.model.entity.Source;
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
  public void createModel(){
    ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    this.model = context.getBean(MonsterSpottingModel.class);
  }

  @Nested
  class MonsterEquals{
    final String MONSTER_NAME = "Goblin";
    final int SOURCE_PAGE = 138;
    final Source SOURCE = new Source("Basic Rules");
    Monster monster;

    @BeforeEach
    public void createMonster(){
      monster = new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE);
    }

    @Nested
    class MonsterDoesNotEqual{

      @Test
      public void somethingThatIsNotAMonster(){
        assertFalse(monster.equals(1));
        assertFalse(monster.equals("something"));
        assertFalse(monster.equals(new Object()));
      }

      @Test
      public void somethingThatIsNull(){
        assertFalse(monster.equals(null));
      }

      @Test
      public void aDifferentMonster(){
        Monster[] otherMonsters = new Monster[3];
        otherMonsters[0] = new Monster(MONSTER_NAME + "2", SOURCE, SOURCE_PAGE);
        otherMonsters[1] = new Monster(MONSTER_NAME, new Source(SOURCE.name()+"2"), SOURCE_PAGE);
        otherMonsters[2] = new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE+1);
        for(Monster otherMonster : otherMonsters){
          assertFalse(monster.equals(otherMonster));
          assertFalse(otherMonster.equals(monster));
        }
      }

      @Nested
      class MonsterDoesEqual{

        @Test
        public void itself(){
          assertTrue(monster.equals(monster));
        }

        @Test
        public void anIdenticalCopyOfItself(){
          Monster identicalCopy = new Monster(MONSTER_NAME, SOURCE, SOURCE_PAGE);
          assertTrue(monster.equals(identicalCopy));
          assertTrue(identicalCopy.equals(monster));
        }
      }

    }

  }

}
