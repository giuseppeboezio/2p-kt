package it.unibo.tuprolog.solve.systemtest

import it.unibo.tuprolog.solve.ClassicSolverFactory
import it.unibo.tuprolog.solve.SolverFactory
import it.unibo.tuprolog.solve.TestBagOf
import kotlin.test.Ignore
import kotlin.test.Test

class TestClassicBagOf : TestBagOf, SolverFactory by ClassicSolverFactory {
    private val prototype = TestBagOf.prototype(this)

    @Test
    override fun testBagXInDifferentValues() {
        prototype.testBagXInDifferentValues()
    }

    @Test
    override fun testBagOfFindX() {
        prototype.testBagOfFindX()
    }

    @Test
    override fun testBagOfYXZ() {
        prototype.testBagOfYXZ()
    }

    @Test
    override fun testBagOfFail() {
        prototype.testBagOfFail()
    }

    @Test
    @Ignore
    override fun testBagOfSameAsFindall() {
        prototype.testBagOfSameAsFindall()
    }

    @Test
    override fun testBagOfInstanceError() {
        prototype.testBagOfInstanceError()
    }

    @Test
    override fun testBagOfTypeError() {
        prototype.testBagOfTypeError()
    }
}
