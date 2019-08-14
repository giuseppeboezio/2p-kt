package it.unibo.tuprolog.solve.solver.statemachine.state

import it.unibo.tuprolog.solve.ExecutionContext

/**
 * Represents a State of Prolog solver state-machine
 *
 * @author Enrico
 */
interface State {

    /** The execution context in which the State should behave */
    val context: ExecutionContext

    /** Makes the state behave and lazily returns next states */
    fun behave(): Sequence<State>

}
