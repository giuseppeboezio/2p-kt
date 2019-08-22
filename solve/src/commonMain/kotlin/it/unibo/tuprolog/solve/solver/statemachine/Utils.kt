package it.unibo.tuprolog.solve.solver.statemachine

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.primitive.Signature
import it.unibo.tuprolog.solve.ExecutionContext
import it.unibo.tuprolog.solve.Solve

/**
 * Utilities object for implementing resolution behaviour
 *
 * @author Enrico
 */
internal object Utils {

    /** Computes the ordered selection of elements, lazily, according to provided selection strategy */
    fun <E> orderedWithStrategy(
            clauses: Sequence<E>,
            context: ExecutionContext,
            selectionStrategy: (Sequence<E>, ExecutionContext) -> E
    ): Sequence<E> = sequence {
        val alreadySelected = mutableListOf<E>()

        clauses.forEach { _ ->
            alreadySelected += selectionStrategy(clauses - alreadySelected, context)
                    .also { yield(it) }
        }
    }

    /** Check whether the provided term is a well-formed predication */
    fun <T : Term> isWellFormed(goal: T): Boolean = goal.accept(Clause.bodyWellFormedVisitor)

    /**
     * Prepares the provided Goal for execution
     *
     * For example, the goal `A` is transformed, after preparation for execution, as the Term: `call(A)`
     */
    fun prepareForExecution(goal: Term): Struct =
            // exploits "Clause" implementation of prepareForExecution() to do that
            Directive.of(goal).prepareForExecution().args.single().castTo()


    /** A method to create [Solve.Request] relative to specific [newGoal], based on [oldSolveRequest] */
    fun createNewGoalSolveRequest(
            oldSolveRequest: Solve.Request,
            newGoal: Struct,
            currentTime: TimeInstant,
            toAddSubstitutions: Substitution = Substitution.empty()
    ): Solve.Request =
            Solve.Request(
                    Signature.fromIndicator(newGoal.indicator)!!,
                    newGoal.argsList,
                    with(oldSolveRequest.context) {
                        copy(
                                actualSubstitution = Substitution.of(actualSubstitution, toAddSubstitutions),
                                parents = parents + this
                        )
                    },
                    adjustExecutionTimeout(oldSolveRequest, currentTime)
            )

    /** Re-computes the execution timeout, leaving it Long.MAX_VALUE if it was it, or decreasing it with elapsed time */
    private fun adjustExecutionTimeout(
            oldSolveRequest: Solve.Request,
            currentTime: TimeInstant
    ): TimeDuration =
            when (oldSolveRequest.executionTimeout) {
                Long.MAX_VALUE -> Long.MAX_VALUE
                else -> with(oldSolveRequest) { executionTimeout - (currentTime - context.computationStartTime) }
                        .takeIf { it >= 0 }
                        ?: 0
            }

    //    /** A method to prepare sub-goals to be executed creating the relative initial states */
//    fun <G : Term> prepareSubGoalInitStates(subGoalsToBeSolved: Sequence<G>): Sequence<State> =
//            subGoalsToBeSolved
//                    .map { prepareForExecution(it) }
//                    .map {
//                        it.unibo.tuprolog.solve.solver.statemachine.state.StateInit(
//                                createNewGoalSolveRequest(it, solveRequest.context, solveRequest.executionTimeout, currentTime()),
//                                executionStrategy,
//                                solverStrategies
//                        )
//                    }
}
