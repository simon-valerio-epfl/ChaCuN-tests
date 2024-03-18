package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ChickenAttackerInitialGameStateTest {

    @Test
    void testClassFinal() {
        assertTrue(Modifier.isFinal(GameState.class.getModifiers()));
    }

}