package it.unibo.tuprolog.solve.solver.statemachine.state

import it.unibo.tuprolog.solve.ExecutionContext
import it.unibo.tuprolog.solve.solver.statemachine.TimeDuration
import kotlinx.coroutines.CoroutineScope

/**
 * Initial state that should Initialize the state-machine
 *
 * @author Enrico
 */
internal class StateInit(
        override val context: ExecutionContext,
        override val executionScope: CoroutineScope,
        override val executionTimeout: TimeDuration
) : AbstractTimedState(context, executionScope, executionTimeout) {

    override fun behaveTimed(): Sequence<State> = sequence {
        val initializedContext = initializationWork(context)

        yield(StateGoalSelection(initializedContext, executionScope, executionTimeout))
    }

    /** Any state machine initialization should be done here */
    private fun initializationWork(context: ExecutionContext): ExecutionContext {
        // TODO initialization?
        return context
    }
}
