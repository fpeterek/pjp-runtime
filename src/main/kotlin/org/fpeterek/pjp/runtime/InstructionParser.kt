package org.fpeterek.pjp.runtime

import org.fpeterek.pjp.runtime.instructions.*

object InstructionParser {


    private fun parseSplit(instruction: List<String>) = when (instruction.firstOrNull()) {
        null, "" -> null
        "add"    -> Add()
        "sub"    -> Sub()
        "mul"    -> Mul()
        "div"    -> Div()
        "mod"    -> Mod()
        "uminus" -> UMinus()
        "concat" -> Cat()
        "and"    -> And()
        "or"     -> Or()
        "gt"     -> Gt()
        "lt"     -> Lt()
        "eq"     -> Eq()
        "not"    -> Not()
        "pop"    -> Pop()

        "push"   -> Push(instruction.last())
        "load"   -> Load(instruction.last())
        "save"   -> Save(instruction.last())
        "jmp"    -> Jmp(instruction.last().toInt())
        "fjmp"   -> FJmp(instruction.last().toInt())
        "print"  -> Print(instruction.last().toInt())
        "read"   -> Read(DataType.fromString(instruction.last()))

        else -> throw Exception("Invalid instruction '${instruction.first()}'")
    }

    private fun parse(str: String) = parseSplit(str.split(" "))

    fun parse(instructions: List<String>) = instructions.mapNotNull { parse(it) }

}