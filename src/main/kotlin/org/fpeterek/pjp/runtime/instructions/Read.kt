package org.fpeterek.pjp.runtime.instructions

import org.fpeterek.pjp.runtime.DataType

data class Read(val type: DataType) : Instruction()