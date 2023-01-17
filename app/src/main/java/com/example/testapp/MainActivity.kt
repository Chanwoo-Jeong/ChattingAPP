package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ActivityMainBinding
import kotlin.math.log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WhatTime(): String? {
    val dateTime = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("hh:mm"))
    return dateTime
}

class MainActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)

        val items: ArrayList<ChatData> = arrayListOf()

        //리사이클러뷰 어댑터 연결
        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        val rvAdapter = RVAdapter(items , this)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)

        viewbinding.msgBtn.setOnClickListener {
            val time:String = WhatTime().toString()
            items.apply {
                add(ChatData(viewbinding.textmsg.text.toString(),time))
            }
            rvAdapter.notifyDataSetChanged()
            //스크롤 포지션
            rv.scrollToPosition(items.size-1)
            viewbinding.textmsg.setText("")

        }
    }
}