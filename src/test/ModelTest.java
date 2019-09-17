package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import controller.Plankton;
import model.RefModels;
import model.SpongebobGame;
import model.SpongebobGameImpl;

class ModelTest {

    @Test
    void test() {
        final SpongebobGame model = new SpongebobGameImpl();
        // test della enum map contenente i plankton
        final Plankton p = null;
        model.addToMap(RefModels.CIRCLE, p);
        assertFalse(model.getMap().isEmpty());
        assertTrue(model.canRemove(RefModels.CIRCLE));
        assertFalse(model.canRemove(RefModels.TRIANGLE));
        assertEquals(model.getMap().get(RefModels.DOUBLEW), new LinkedList<Plankton>());
        // test feature tempo caduta plankton
        final int time = model.getPlanktonTime();
        final long rate = model.getPlanktonRate();
        IntStream.range(0, 10).forEach(i -> model.incrementScore());
        assertEquals(model.getPlanktonTime(), time);
        assertNotEquals(model.getPlanktonRate(), rate);
        // test score bonus
        int score = model.getScore();
        model.incrementScore();
        assertEquals(model.getScore(), score + 1);
        score = model.getScore();
        model.scoreBonusOn();
        model.scoreBonusOn();
        model.incrementScore();
        assertNotEquals(model.getScore(), score + 1);
        assertEquals(model.getScore(), score + 5);
        model.scoreBonusOff();
        score = model.getScore();
        model.incrementScore();
        assertEquals(model.getScore(), score + 1);
        assertNotEquals(model.getScore(), score + 5);
        // test delay bonus
        long pRate = model.getPlanktonRate();
        assertTrue(model.onDelayBonus());
        assertNotEquals(model.getPlanktonRate(), pRate);
        model.onDelayBonus();
        model.onDelayBonus();
        assertNotEquals(model.getPlanktonRate(), pRate);
        model.offDelayBonus((int) pRate);
        assertEquals(model.getPlanktonRate(), pRate);
        
    }

}
