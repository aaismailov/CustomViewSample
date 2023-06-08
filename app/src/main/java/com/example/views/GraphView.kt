package com.example.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import androidx.appcompat.widget.AppCompatImageView

class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var values = IntArray(0)
    private var graphStyle = 0
    private var graphColor = 0

    private var graphPaint = Paint()
    private var linePaint = Paint()

    init {
        attrs?.let { initAttrs(it, defStyleAttr) }
    }

    private fun initAttrs(attrs: AttributeSet, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GraphView,
            defStyleAttr,
            0
        ).apply {
            try {
                graphStyle = getInt(R.styleable.GraphView_custom_style, 0)
                graphColor = getColor(R.styleable.GraphView_custom_color_graph, Color.BLACK)

                graphPaint.color = graphColor
                graphPaint.style = if (graphStyle == 0) Paint.Style.FILL else Paint.Style.STROKE
                graphPaint.strokeWidth = 10F
                linePaint.color = getColor(R.styleable.GraphView_custom_color_line, Color.CYAN)
                linePaint.strokeWidth = 5F
            } finally {
                recycle()
            }
        }
    }

    fun setValues(newValues: IntArray) {
        values = newValues
        invalidate()
    }

    fun setGraphColor(newColor: Int) {
        graphColor = newColor
        graphPaint.color = graphColor
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val barWidth = (width / values.size).toFloat()
        val barHeight = height.toFloat()
        val heightToPercent = height / 100

        for (i in values.indices) {
            canvas.drawRect(
                i * barWidth,
                barHeight,
                i * barWidth + barWidth,
                barHeight - values[i] * heightToPercent,
                graphPaint
            )

            canvas.drawLine(
                i * barWidth + barWidth,
                barHeight,
                i * barWidth + barWidth,
                barHeight - (values[i] * heightToPercent),
                linePaint
            )
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val state = super.onSaveInstanceState()
        return SavedState(values, graphColor, state)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        state as SavedState
        super.onRestoreInstanceState(state.superState)
        setValues(state.values)
        setGraphColor(state.color)
    }

    @Parcelize
    class SavedState(
        val values: IntArray,
        val color: Int,
        @IgnoredOnParcel val source: Parcelable? = null
    ) : BaseSavedState(source)
}