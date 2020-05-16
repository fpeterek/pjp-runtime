package org.fpeterek.pjp.runtime

import org.fpeterek.pjp.runtime.instructions.*
import java.util.*
import kotlin.Exception

class Runtime {

    private val stack = Stack<Value>()
    private val variables = mutableMapOf<String, Value>()
    private var pointer = 0

    private val input = Scanner(System.`in`)

    private fun emplace(value: Value) = stack.push(value)

    private fun pop() = stack.pop()

    private fun loadVar(varName: String) {
        emplace(variables[varName]!!)
    }

    private fun saveToVar(varName: String) {
        variables[varName] = pop()
    }

    private fun jump(distance: Int) {
        pointer += distance
    }

    private fun condJump(distance: Int) {
        if (!pop().bool) {
            jump(distance)
        }
    }

    private fun printValue() {
        print(pop())
    }

    private fun read(type: DataType) = emplace(
            Value(
                when (type) {
                    DataType.Int    -> input.nextInt()
                    DataType.Float  -> input.nextDouble()
                    DataType.String -> input.next()
                    DataType.Bool   -> input.nextBoolean()
                },
                type
            )
        )


    private fun execute(instruction: Instruction) {
        when (instruction) {
            is Add    -> emplace(pop() + pop())
            is Sub    -> emplace(pop() - pop())
            is Mul    -> emplace(pop() * pop())
            is Div    -> emplace(pop() / pop())
            is Mod    -> emplace(pop() % pop())
            is UMinus -> emplace(-pop())
            is Cat    -> emplace(pop() + pop())
            is And    -> emplace(pop() and pop())
            is Or     -> emplace(pop() or pop())
            is Gt     -> emplace(pop() gt pop())
            is Lt     -> emplace(pop() lt pop())
            is Eq     -> emplace(pop() eq pop())
            is Not    -> emplace(!pop())
            is Pop    -> pop()

            is Push   -> emplace(instruction.value)
            is Load   -> loadVar(instruction.variable)
            is Save   -> saveToVar(instruction.variable)
            is Jmp    -> jump(instruction.distance)
            is FJmp   -> condJump(instruction.distance)
            is Print  -> (1..instruction.values).forEach { _ -> printValue() }
            is Read   -> read(instruction.type)
            else      -> throw Exception("Invalid instruction")
        }
    }

    fun execute(instructions: List<Instruction>) {
        while (pointer < instructions.size) {

            val instruction = instructions[pointer]
            // val isJmp = instruction is Jmp || instruction is FJmp
            //println(instruction.javaClass)
            execute(instruction)
            ++pointer

        }
    }

}