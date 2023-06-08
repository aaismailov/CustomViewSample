package com.example.views

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var graphView: GraphView
    private lateinit var refreshBtn: Button

    private val ratings = IntArray(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ratings[0] = 40
        ratings[1] = 80
        ratings[2] = 50
        ratings[3] = 90

        setContentView(R.layout.activity_main)
        graphView = findViewById(R.id.graphView)
        refreshBtn = findViewById(R.id.refreshBtn)

        graphView.setValues(ratings)

        refreshBtn.setOnClickListener {

            val random = Random()

            val arraySize = abs(random.nextInt()) % 8 + 1
            val values = IntArray(arraySize) { abs(random.nextInt()) % 101 }

            graphView.setValues(values)
            graphView.setGraphColor(Color.RED)
        }
    }
}