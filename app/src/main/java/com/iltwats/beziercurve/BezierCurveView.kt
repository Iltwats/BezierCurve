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

    private var p1: Point = Point(1100, 1050)
    private var p2: Point = Point(1500, 550)
    private var p3: Point = Point(1800,850)
    private var cp1: Point = Point(830, 780)
    private var cp2: Point? = null
    private var cp3: Point = Point(2000,500)
    private var cp4: Point = Point(1400,1250)
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
    private val centerPaint: Paint = Paint().apply {
        strokeWidth = 2f
        color = Color.parseColor("#F9D342")
        style = Paint.Style.FILL_AND_STROKE
    }

    private val path: Path = Path()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, controlLinePaint)

        path.reset()
        drawBezier(canvas, listOf(p1,p2,p3,p1), listOf(cp1,cp3,cp4))
        val xCenter = (p1.x+p2.x+p3.x)/3;
        val yCenter = (p1.y+p2.y+p3.y)/3;
        canvas?.drawCircle(xCenter.toFloat(), yCenter.toFloat(), 60f, centerPaint)
        invalidate()

    }

    private fun drawBezier(canvas: Canvas?, p: List<Point>, cp: List<Point>) {
        for(i in 0..2){
            path.moveTo(p[i].x.toFloat(), p[i].y.toFloat())
            if (cp2 != null) {
                path.cubicTo(cp1.x.toFloat(), cp1.y.toFloat(), cp2!!.x.toFloat(),
                    cp2!!.y.toFloat(), this.p2.x.toFloat(), this.p2.y.toFloat()
                )
            } else {
                path.quadTo(cp[i].x.toFloat(), cp[i].y.toFloat(), p[i+1].x.toFloat(), p[i+1].y.toFloat() )
            }
            canvas?.drawPath(path, curvePaint)

            canvas?.drawCircle(p[i].x.toFloat(), p[i].y.toFloat(), 10f, pointPaint)
            canvas?.drawCircle(p[i+1].x.toFloat(), p[i+1].y.toFloat(), 10f, pointPaint)
            canvas?.drawCircle(cp[i].x.toFloat(), cp[i].y.toFloat(), 10f, pointPaint)
            cp2?.let { canvas?.drawCircle(it.x.toFloat(), it.y.toFloat(), 10f, pointPaint) }

        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x: Int = event?.x?.toInt() ?: 0
        val y: Int = event?.y?.toInt() ?: 0

        when(event?.action){
            MotionEvent.ACTION_DOWN-> Log.d("down","$x,$y")
            MotionEvent.ACTION_MOVE-> {
                if(Point(x,y) == cp1){
                    cp1 = Point(x,y)
                    Log.d("cp1","$x,$y")
                }else if(Point(x,y) == cp3){
                    cp3 = Point(x,y)
                    Log.d("cp3","$x,$y")
                }else if(Point(x,y) == cp4){
                    cp4 = Point(x,y)
                    Log.d("cp4","$x,$y")
                }
            }
            MotionEvent.ACTION_UP-> Log.d("up","$x,$y")
        }
        return true
    }

    override fun setOnDragListener(l: OnDragListener?) {
        l.onDrag()
        super.setOnDragListener(l)
    }
}