package org.redaksi.ui

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan(private val typeface: Typeface?) : MetricAffectingSpan() {
    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
        paint.letterSpacing = 1.2f
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
        paint.letterSpacing = 1.2f
    }
}
