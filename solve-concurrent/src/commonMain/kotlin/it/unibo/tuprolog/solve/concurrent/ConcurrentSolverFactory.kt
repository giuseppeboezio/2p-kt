package it.unibo.tuprolog.solve.concurrent

import it.unibo.tuprolog.solve.MutableSolver
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.solve.SolverFactory
import it.unibo.tuprolog.solve.channel.InputChannel
import it.unibo.tuprolog.solve.channel.InputStore
import it.unibo.tuprolog.solve.channel.OutputChannel
import it.unibo.tuprolog.solve.channel.OutputStore
import it.unibo.tuprolog.solve.concurrent.stdlib.DefaultBuiltins
import it.unibo.tuprolog.solve.exception.Warning
import it.unibo.tuprolog.solve.flags.FlagStore
import it.unibo.tuprolog.solve.library.Library
import it.unibo.tuprolog.solve.library.Runtime
import it.unibo.tuprolog.theory.Theory

object ConcurrentSolverFactory : SolverFactory {
    override val defaultBuiltins: Library
        get() = DefaultBuiltins

    override fun solverOf(
        libraries: Runtime,
        flags: FlagStore,
        staticKb: Theory,
        dynamicKb: Theory,
        inputs: InputStore,
        outputs: OutputStore
    ): Solver = ConcurrentSolverImpl(libraries, flags, staticKb, dynamicKb, inputs, outputs)

    override fun solverOf(
        libraries: Runtime,
        flags: FlagStore,
        staticKb: Theory,
        dynamicKb: Theory,
        stdIn: InputChannel<String>,
        stdOut: OutputChannel<String>,
        stdErr: OutputChannel<String>,
        warnings: OutputChannel<Warning>
    ): Solver = ConcurrentSolverImpl(libraries, flags, staticKb, dynamicKb, stdIn, stdOut, stdErr, warnings)

    override fun mutableSolverOf(
        libraries: Runtime,
        flags: FlagStore,
        staticKb: Theory,
        dynamicKb: Theory,
        stdIn: InputChannel<String>,
        stdOut: OutputChannel<String>,
        stdErr: OutputChannel<String>,
        warnings: OutputChannel<Warning>
    ): MutableSolver = MutableConcurrentSolver(libraries, flags, staticKb, dynamicKb, stdIn, stdOut, stdErr, warnings)

    override fun mutableSolverOf(
        libraries: Runtime,
        flags: FlagStore,
        staticKb: Theory,
        dynamicKb: Theory,
        inputs: InputStore,
        outputs: OutputStore
    ): MutableSolver = MutableConcurrentSolver(libraries, flags, staticKb, dynamicKb, inputs, outputs)
}
