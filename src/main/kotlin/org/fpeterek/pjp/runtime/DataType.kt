package org.fpeterek.pjp.runtime

enum class DataType(value: kotlin.String) {
    Int("I"),
    Float("F"),
    String("S"),
    Bool("B");

    companion object {
        fun fromString(str: kotlin.String) = when (str) {
            "I" -> Int
            "F" -> Float
            "S" -> String
            "B" -> Bool
            else -> throw Exception("Invalid type '$str'")
        }
    }

}