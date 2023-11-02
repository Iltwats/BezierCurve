package com.iltwats.beziercurve

import android.os.Bundle
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

