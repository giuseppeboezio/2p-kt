package it.unibo.tuprolog.solve

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.libraries.Libraries
import it.unibo.tuprolog.theory.ClauseDatabase
import kotlin.test.Test
import kotlin.test.assertTrue

class TestMutableSolver : AbstractSolverTest() {
    override fun solverOf(libraries: Libraries, flags: Map<Atom, Term>, staticKB: ClauseDatabase, dynamicKB: ClauseDatabase): Solver {
        return MutableSolver(libraries, flags, staticKB, dynamicKB)
    }

    @Test
    override fun testConjunction() {
        super.testConjunction()
    }
}