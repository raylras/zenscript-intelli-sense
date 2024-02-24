package raylras.zen.model.diagnose

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.misc.IntSet
import org.antlr.v4.runtime.misc.IntervalSet
import raylras.zen.model.Visitor

class ExpectedTokenVisitor(

): Visitor<Unit>() {
    val result: IntervalSet = IntervalSet()


}