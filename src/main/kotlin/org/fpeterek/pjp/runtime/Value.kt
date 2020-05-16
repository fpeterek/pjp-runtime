package org.fpeterek.pjp.runtime

class Value(val value: Any, val type: DataType) {

    val int: Int
        get() = value as Int

    val float: Float
        get() = if (type == DataType.Float) {
            value as Float
        } else {
            int.toFloat()
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

    operator fun compareTo(other: Value) = 0

}
