package it.unibo.tuprolog.libraries.stdlib.function

import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Numeric
import it.unibo.tuprolog.libraries.stdlib.function.testutils.FunctionUtils.computeOf
import it.unibo.tuprolog.primitive.Signature
import kotlin.math.E
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test class for [Exponential]
 *
 * @author Enrico
 */
internal class ExponentialTest {

    @Test
    fun functorNameCorrect() {
        assertEquals(Signature("exp", 1), Exponential.signature)
    }

    @Test
    fun computationCorrect() {
        assertEquals(
            Numeric.of(E),
            Exponential.computeOf(Integer.of(1))
        )
    }

}
