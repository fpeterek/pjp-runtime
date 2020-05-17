package org.fpeterek.pjp.runtime

class Value(val value: Any, val type: DataType) {

    val int: Int
        get() = value as Int

    val float: Double
        get() = if (type == DataType.Float) {
            value as Double
        } else {
            int.toDouble()
        }

    val string: String
        get() = value as String
    val bool: Boolean
        get() = value as Boolean

    private fun catStrings(other: Value) = Value("$string${other.string}", DataType.String)

    private fun addFloat(other: Value) = Value(float+other.float, DataType.Float)
    private fun addInt(other: Value) = Value(int+other.int, DataType.Int)

    operator fun plus(other: Value) = when {
        type == DataType.String -> catStrings(other)
        type == DataType.Float || other.type == DataType.Float -> addFloat(other)
        else -> addInt(other)
    }

    private fun subFloat(other: Value) = Value(float-other.float, DataType.Float)
    private fun subInt(other: Value) = Value(int-other.int, DataType.Int)

    operator fun minus(other: Value) = when {
        type == DataType.Float || other.type == DataType.Float -> subFloat(other)
        else -> subInt(other)
    }

    private fun multFloat(other: Value) = Value(float*other.float, DataType.Float)
    private fun multInt(other: Value) = Value(int*other.int, DataType.Int)

    operator fun times(other: Value) = when {
        type == DataType.Float || other.type == DataType.Float -> multFloat(other)
        else -> multInt(other)
    }

    private fun divFloat(other: Value) = Value(float/other.float, DataType.Float)
    private fun divInt(other: Value) = Value(int/other.int, DataType.Int)

    operator fun div(other: Value) = when {
        type == DataType.Float || other.type == DataType.Float -> divFloat(other)
        else -> divInt(other)
    }

    operator fun rem(other: Value) = Value(int%other.int, DataType.Int)

    operator fun unaryMinus() = when (type) {
        DataType.Int -> Value(-int, DataType.Int)
        else -> Value(-float, DataType.Float)
    }

    infix fun and(other: Value) = Value(bool && other.bool, DataType.Bool)
    infix fun or(other: Value) = Value(bool || other.bool, DataType.Bool)

    operator fun compareTo(other: Value) = when {
        type == DataType.Int && other.type == DataType.Int -> int.compareTo(other.int)
        type == DataType.String -> string.compareTo(other.string)
        type == DataType.Bool -> bool.compareTo(other.bool)
        else -> float.compareTo(other.float)
    }

    infix fun gt(other: Value) = Value(this > other, DataType.Bool)
    infix fun lt(other: Value) = Value(this < other, DataType.Bool)
    infix fun eq(other: Value) = Value(this == other, DataType.Bool)

    operator fun not() = Value(!bool, DataType.Bool)

    override fun toString() = when (type) {
        DataType.Bool   -> bool.toString()
        DataType.String -> string
        DataType.Int    -> int.toString()
        DataType.Float  -> float.toString()
    }

}
