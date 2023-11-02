package com.iltwats.beziercurve

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class BezierCurveView :View {
    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(set: AttributeSet?) {

    }

    private var p1: Point = Point(1000, 950)
    private var p2: Point = Point(1500, 550)
    private var cp1: Point = Point(1100, 600)
    private var cp2: Point? = null
    fun setPoints(p1: Point, p2: Point, cp1: Point){
        this.p1 = p1
        this.p2 = p2
        this.cp1 = cp1
    }


    private val curvePaint: Paint = Paint().apply {
        strokeWidth = 6f
        color = Color.parseColor("#333333")
        style = Paint.Style.STROKE
    }

    private val controlLinePaint: Paint = Paint().apply {
        strokeWidth = 1f
        color = Color.parseColor("#CC0000")
        style = Paint.Style.STROKE
    }

    private val pointPaint: Paint = Paint().apply {
        strokeWidth = 2f
        color = Color.parseColor("#990000")
        style = Paint.Style.FILL_AND_STROKE
    }

    private val path: Path = Path()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, controlLinePaint)

        path.reset()
        path.moveTo(p1.x.toFloat(), p1.y.toFloat())
        if (cp2 != null) {
            path.cubicTo(cp1.x.toFloat(), cp1.y.toFloat(), cp2!!.x.toFloat(),
                cp2!!.y.toFloat(), p2.x.toFloat(), p2.y.toFloat()
            )
        } else {
            path.quadTo(cp1.x.toFloat(), cp1.y.toFloat(), p2.x.toFloat(), p2.y.toFloat() )
            path.quadTo(cp1.x.toFloat()*2, cp1.y.toFloat(), p2.x.toFloat(), p2.y.toFloat() )
            path.quadTo(cp1.x.toFloat()*3, cp1.y.toFloat(), p2.x.toFloat(), p2.y.toFloat() )

        }
        canvas?.drawPath(path, curvePaint)

        canvas?.drawCircle(p1.x.toFloat(), p1.y.toFloat(), 10f, pointPaint)
        canvas?.drawCircle(p2.x.toFloat(), p2.y.toFloat(), 10f, pointPaint)
        canvas?.drawCircle(cp1.x.toFloat(), cp1.y.toFloat(), 10f, pointPaint)
        cp2?.let { canvas?.drawCircle(it.x.toFloat(), it.y.toFloat(), 10f, pointPaint) }
        invalidate()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x: Int = event?.x?.toInt() ?: 0
        val y: Int = event?.y?.toInt() ?: 0

        when(event?.action){
            MotionEvent.ACTION_DOWN-> Log.d("down","$x,$y")
            MotionEvent.ACTION_MOVE-> cp1 = Point(x,y)
            MotionEvent.ACTION_UP-> Log.d("up","$x,$y")
        }
        return true
    }
}