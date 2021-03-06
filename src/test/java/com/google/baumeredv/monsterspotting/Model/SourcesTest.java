package com.google.baumeredv.monsterspotting.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.baumeredv.monsterspotting.App;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateSourceException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchSourceException;
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
  public void createModel() {
    ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    this.model = context.getBean(MonsterSpottingModel.class);
  }

  @Nested
  class WhenAddingSources {

    private final String SOURCE_NAME = "a new source";

    @Nested
    class AnAddedSource {

      private Source addedSource;

      @BeforeEach
      public void createSource() throws DuplicateSourceException {
        addedSource = model.addSource(new Source(SOURCE_NAME));
      }

      @Test
      public void isAddedToTheModel() {
        assertTrue(isSourceInModel(addedSource));
      }

      @Test
      public void hasTheNameItWasGiven() {
        assertEquals(SOURCE_NAME, addedSource.name());
      }

      @Test
      public void canBeAddedAlongsideASecondOne() throws DuplicateSourceException {
        final String SECOND_SOURCE_NAME = SOURCE_NAME + " 2";
        Source secondSource = model.addSource(new Source(SECOND_SOURCE_NAME));

        assertTrue(isSourceInModel(addedSource));
        assertTrue(isSourceInModel(secondSource));
      }
    }

    @Nested
    class ASourceCannotBeAdded {

      @Test
      public void ifItsNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> model.addSource(new Source("")));
        assertThrows(IllegalArgumentException.class, () -> model.addSource(new Source(null)));
      }

      @Test
      public void ifItIsNull() {
        assertThrows(IllegalArgumentException.class, () -> model.addSource(null));
      }

      @Test
      public void ifItIsIdenticalToAnExistingSource() throws DuplicateSourceException {
        Source source = model.addSource(new Source(SOURCE_NAME));
        assertThrows(DuplicateSourceException.class, () -> model.addSource(source));
      }
    }
  }

  @Nested
  class WhenDeletingSources {

    private final String SOURCE_TO_BE_DELETED_NAME = "source to be deleted";

    @Test
    public void deletingNullThrows() {
      assertThrows(IllegalArgumentException.class,
          () -> model.deleteSource(null));
    }

    @Test
    public void deletingANonexistentSourceThrows() {
      assertThrows(ThereIsNoSuchSourceException.class,
          () -> model.deleteSource(new Source(SOURCE_TO_BE_DELETED_NAME)));
    }

    @Nested
    class AfterASourceWasAdded {

      private Source source;

      @BeforeEach
      public void createSource() throws DuplicateSourceException {
        source = model.addSource(new Source(SOURCE_TO_BE_DELETED_NAME));
      }

      @Test
      public void deletingTheSourceRemovesItFromTheModel() throws ThereIsNoSuchSourceException {
        model.deleteSource(new Source(SOURCE_TO_BE_DELETED_NAME));
        assertFalse(isSourceInModel(source));
      }
    }


  }


  @Nested
  class SourceEquals {

    final String SOURCE_NAME = "source name";
    Source source;

    @BeforeEach
    public void createSource() {
      source = new Source(SOURCE_NAME);
    }

    @Nested
    class SourceDoesNotEqual {

      @Test
      public void somethingThatIsNotASource() {
        assertFalse(source.equals(1));
        assertFalse(source.equals("something"));
        assertFalse(source.equals(SOURCE_NAME));
      }

      @Test
      public void somethingThatIsNull() {
        assertFalse(source.equals(null));
      }

      @Test
      public void aDifferentSource() {
        Source otherSource = new Source(SOURCE_NAME + " 2");
        assertFalse(source.equals(otherSource));
        assertFalse(otherSource.equals(source));
      }
    }

    @Nested
    class SourceDoesEqual {

      @Test
      public void itself() {
        assertTrue(source.equals(source));
      }

      @Test
      public void anIdenticalCopyOfItself() {
        Source identicalCopy = new Source(SOURCE_NAME);
        assertTrue(source.equals(identicalCopy));
        assertTrue(identicalCopy.equals(source));
      }
    }
  }


  private boolean isSourceInModel(Source sourceInQuestion) {
    Iterable<Source> allSources = model.allSources();
    for (Source source : allSources) {
      if (source.equals(sourceInQuestion)) {
        return true;
      }
    }
    return false;
  }
}
