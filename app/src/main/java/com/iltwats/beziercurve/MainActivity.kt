package com.iltwats.beziercurve

import android.content.Context
import android.graphics.Matrix
import android.graphics.Point
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
        activityMainBinding.p1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                val result = setSeek(p0)
                activityMainBinding.bezierCurveView.setP1(Point(result.first, result.second))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        activityMainBinding.p2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                val result = setSeek(p0)
                activityMainBinding.bezierCurveView.setP2(Point(result.first, result.second))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        activityMainBinding.p3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                val result = setSeek(p0)
                activityMainBinding.bezierCurveView.setP3(Point(result.first, result.second))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })


    }

    private fun setSeek(seekBar: SeekBar): Pair<Int, Int> {
        val thumb = seekBar.thumb
        val thumbWidth = thumb.intrinsicWidth
        val thumbHeight = thumb.intrinsicHeight
        val seekBarWidth = seekBar.width
        val seekBarHeight = seekBar.height

        // Calculate the absolute position of the thumb within the SeekBar
//        val thumbX = (seekBarWidth - thumbWidth) * seekBar.progress / seekBar.max
//        val thumbY = (seekBarHeight - thumbHeight) / 2
        val location = IntArray(2)
        seekBar.getLocationInWindow(location)

        val seekBarX = location[0]
        val seekBarY = location[1]

// Calculate the thumb's relative position within the SeekBar
        val thumbBounds = seekBar.thumb.bounds
        val thumbCenterX = thumbBounds.exactCenterX()
        val thumbCenterY = thumbBounds.exactCenterY()

// Calculate the position of the thumb relative to the window
        val thumbX = seekBarX + thumbCenterX
        val thumbY = seekBarY + thumbCenterY
        // Consider the rotation of the SeekBar
        val pivotX = seekBar.pivotX
        val pivotY = seekBar.pivotY
        val matrix = seekBar.matrix
        matrix.reset()
        matrix.postRotate(seekBar.rotation, pivotX, pivotY)

        val points = floatArrayOf(thumbX.toFloat(), thumbY.toFloat())
        matrix.mapPoints(points)

        val rotatedThumbX = points[0]
        val rotatedThumbY = points[1]

        // Consider margins within the ConstraintLayout
        val layoutParams = seekBar.layoutParams as ConstraintLayout.LayoutParams
        val topMargin = layoutParams.topMargin
        val endMargin = layoutParams.marginEnd

        // Calculate the absolute position of the thumb including margins
        val absoluteThumbX = rotatedThumbX + endMargin
        val absoluteThumbY = rotatedThumbY + topMargin
        return Pair(absoluteThumbX.toInt(), absoluteThumbY.toInt())
    }


    private fun generateDataPoint(): List<DataPoint> {
        val list = mutableListOf<DataPoint>()
        val range = (1..2)

        range.forEach { it ->
            when (it) {
                1 -> list.add(DataPoint(1f))
                2 -> list.add(DataPoint(80f))
                3 -> list.add(DataPoint(3f))
                else -> list.add(DataPoint(range.random() * 100f))
            }
            //list.add(DataPoint(range.random()*100f))
        }
        return list
    }
}

