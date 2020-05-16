package org.fpeterek.pjp.runtime

import org.fpeterek.pjp.runtime.instructions.*
import java.util.*
import kotlin.Exception

class Runtime {

    private val stack = Stack<Value>()
    private val variables = mapOf<String, Value>()
    private var pointer = 0

    private fun emplace(value: Value) = stack.push(value)

    private fun pop() {
        stack.pop()
    }

    private fun execute(instruction: Instruction) {
        when (instruction) {
            is Pop -> pop()
            is Add -> emplace(stack.pop() + stack.pop())
            is Sub -> emplace(stack.pop() - stack.pop())
            is Mul -> emplace(stack.pop() * stack.pop())
            is Div -> emplace(stack.pop() / stack.pop())
            is Mod -> emplace(stack.pop() % stack.pop())
            else -> throw Exception("Invalid instruction")
        }
    }

    fun execute(instructions: List<Instruction>) {
        while (pointer < instructions.size) {
            execute(instructions[pointer])
        }
    }

}