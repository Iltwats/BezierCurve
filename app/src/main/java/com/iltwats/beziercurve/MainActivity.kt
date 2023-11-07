package com.iltwats.beziercurve

import android.content.Context
import android.graphics.Matrix
import android.graphics.Point
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.iltwats.beziercurve.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

//        activityMainBinding.curve.setCurveBorderColor(R.color.blue)
//        activityMainBinding.curve.addDataPoints(generateDataPoint())
      //  activityMainBinding.bezierCurveView2.setPoints(Point(100,250),Point(400,250), Point(200,600))
        setUpSeekbar(this)

    }

    private fun setUpSeekbar(context: Context) {
        //activityMainBinding.p1.animate().x(1100f).y(1050f)
        //activityMainBinding.p1.translationY = 1050F
        //activityMainBinding.p1.marginLeft = 1100
        activityMainBinding.p1.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                // x,y coordinate relative to thumb position
                val location = IntArray(2)
                p0.getLocationInWindow(location)
//                var thumbX: Int = location[0] + p0.thumb.bounds.centerX()
//                var thumbY: Int = location[1] + p0.thumb.bounds.centerY()
//
//                // z-axis transformation factor
//                val zScale = p0.scaleX
//                thumbX = (thumbX/zScale).toInt()
//                thumbY = (thumbY/zScale).toInt()
                var thumbX: Int = location[0] - p0.left + p0.thumb.bounds.centerX()
                var thumbY: Int = location[1] - p0.top + p0.thumb.bounds.centerY()

                // Get the transformation matrix applied to the layout

                // Get the transformation matrix applied to the layout
                val matrix: Matrix = p0.matrix

                // Apply the inverse transformation to the coordinates

                // Apply the inverse transformation to the coordinates
                val point = floatArrayOf(thumbX.toFloat(), thumbY.toFloat())
                matrix.invert(matrix)
                matrix.mapPoints(point)

                thumbX = point[0].toInt()
                thumbY = point[1].toInt()
                activityMainBinding.bezierCurveView.setP1(Point(thumbX,thumbY))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })



    }





    private fun generateDataPoint(): List<DataPoint> {
        val list = mutableListOf<DataPoint>()
        val range = (1..2)

        range.forEach { it ->
            when(it){
                1->list.add(DataPoint(1f))
                2->list.add(DataPoint(80f))
                3->list.add(DataPoint(3f))
                else -> list.add(DataPoint(range.random()*100f))
            }
            //list.add(DataPoint(range.random()*100f))
        }
        return list
    }
}

