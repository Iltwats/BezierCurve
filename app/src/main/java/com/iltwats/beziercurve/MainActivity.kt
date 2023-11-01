package com.iltwats.beziercurve

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iltwats.beziercurve.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.curve.setCurveBorderColor(R.color.blue)
        activityMainBinding.curve.addDataPoints(generateDataPoint())

    }

    private fun generateDataPoint(): List<DataPoint> {
        val list = mutableListOf<DataPoint>()
        val range = (0..10)

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

