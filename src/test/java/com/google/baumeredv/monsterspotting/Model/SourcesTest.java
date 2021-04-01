package com.google.baumeredv.monsterspotting.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.baumeredv.monsterspotting.App;
import com.google.baumeredv.monsterspotting.model.MonsterSpottingModel;
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
public class SourcesTest {

  private MonsterSpottingModel model;

  @BeforeEach
  public void createModel(){
    ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    this.model = context.getBean(MonsterSpottingModel.class);
  }

  @Nested
  class WhenAddingSources{

    @Nested
    class AnAddedSource{

      private final String SOURCE_NAME = "a new source";
      private Source addedSource;

      @BeforeEach
      public void createSource(){
        addedSource = model.addSource(new Source(SOURCE_NAME));
      }

      @Test
      public void isAddedToTheModel(){
        assertTrue(isSourceInModel(addedSource));
      }

      @Test
      public void hasTheNameItWasGiven(){
        assertEquals(SOURCE_NAME, addedSource.name());
      }

      @Test
      public void canBeAddedAlongsideASecondOne(){
        final String SECOND_SOURCE_NAME = SOURCE_NAME + " 2";
        Source secondSource = model.addSource(new Source(SECOND_SOURCE_NAME));

        assertTrue(isSourceInModel(addedSource));
        assertTrue(isSourceInModel(secondSource));
      }
    }

    @Nested
    class ASourceCannotBeAdded{

      @Test
      public void ifItsNameIsEmpty(){
        assertThrows(IllegalArgumentException.class, () -> model.addSource(new Source("")));
        assertThrows(IllegalArgumentException.class, () -> model.addSource(new Source(null)));
      }

      @Test
      public void ifItIsNull(){
        assertThrows(IllegalArgumentException.class, () -> model.addSource(null));
      }
    }
  }




  private boolean isSourceInModel(Source sourceInQuestion) {
    Iterable<Source> allSources = model.AllSources();
    for(Source source : allSources){
      if(source.equals(sourceInQuestion)){
        return true;
      }
    }
    return false;
  }
}
