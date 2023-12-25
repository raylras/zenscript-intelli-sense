package raylras.zen.model

import raylras.zen.model.parser.ZenScriptParserBaseVisitor

abstract class Visitor<T> : ZenScriptParserBaseVisitor<T>()
