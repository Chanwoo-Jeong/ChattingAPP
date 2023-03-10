package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.SocketMainBinding
import java.net.URISyntaxException

import io.socket.client.Socket;

//
//fun WhatTime(): String? {
//    val dateTime = LocalDateTime.now()
//        .format(DateTimeFormatter.ofPattern("hh:mm"))
//    return dateTime
//}

class SocketActivity : AppCompatActivity() {
    private lateinit var viewbinding: SocketMainBinding

    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = SocketMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)

        try {
            //IO.socket 메소드는 은 저 URL 을 토대로 클라이언트 객체를 Return 합니다.
            mSocket = io.socket.client.IO.socket("http://10.0.2.2:3000")
        } catch (e: URISyntaxException) {
            Log.e("MainActivity", e.reason)
        }
        mSocket.connect()

        val items: ArrayList<ChatData> = arrayListOf()

        //리사이클러뷰 어댑터 연결
        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        val rvAdapter = SocketRVAdapter(items , this)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)

        viewbinding.msgBtn.setOnClickListener {
            val time:String = WhatTime().toString()
            val msg:String = viewbinding.textmsg.text.toString()
            if(msg.isNotEmpty()) {
                Log.d("msg",msg)
                items.apply {
                    add(ChatData(msg, time))
                }
                rvAdapter.notifyDataSetChanged()
                //스크롤 포지션
                rv.scrollToPosition(items.size - 1)
                viewbinding.textmsg.setText("")

                val chat = "I'm kotlin"
                mSocket.emit("say", chat)
            }
        }
    }
}