package org.fpeterek.pjp.runtime

import org.fpeterek.pjp.runtime.instructions.*

object InstructionParser {

    private fun parseValueFromArg(type: Char, value: String) = when (type.toUpperCase()) {
        'I' -> Value(value.toInt(), DataType.Int)
        'B' -> Value(value.toBoolean(), DataType.Bool)
        'S' -> Value(value, DataType.String)
        'F' -> Value(value.toFloat(), DataType.Float)
        else -> throw Exception("Invalid type")
    }

    private fun parseValue(value: String) = parseValueFromArg(value.first(), value.drop(1))

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

        "push"   -> Push(parseValue(instruction.last()))
        "load"   -> Load(instruction.last())
        "save"   -> Save(instruction.last())
        "jmp"    -> Jmp(instruction.last().toInt())
        "fjmp"   -> FJmp(instruction.last().toInt())
        "print"  -> Print(instruction.last().toInt())
        "read"   -> Read(DataType.fromString(instruction.last()))

        else -> throw Exception("Invalid instruction '${instruction.first()}'")
    }

    private fun parse(str: String) = parseSplit(str.split(" ", limit=2))

    fun parse(instructions: List<String>) = instructions.mapNotNull { parse(it) }

}