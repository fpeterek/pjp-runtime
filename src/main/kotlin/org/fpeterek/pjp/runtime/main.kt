package org.fpeterek.pjp.runtime

import java.io.File

fun load(filename: String) = File(filename).readLines()

fun runProgram(filename: String) =
    Runtime().execute(InstructionParser.parse(load(filename)))

fun main(args: Array<String>) = args.forEach { runProgram(it) }

