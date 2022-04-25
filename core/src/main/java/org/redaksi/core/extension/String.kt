package org.redaksi.core.extension

fun String.capitalizeEveryWord() = this.lowercase().split(" ").map { it.replaceFirstChar { it.titlecase() } }.joinToString()
