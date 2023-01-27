package com.example.testapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.databinding.ActivityMainUchatBinding
import com.example.testapp.databinding.FragmentSecondBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun WhatTime(): String? {
    val dateTime = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("hh:mm"))
    return dateTime
}

class ChatActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityMainUchatBinding
    private lateinit var aviewbinding : ActivityMainBinding
    var nick : String = "three"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val id = intent.getStringExtra("id")
        val from = intent.getStringExtra("from")

        viewbinding = ActivityMainUchatBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        // Write a message to the database
        val database = Firebase.database

        if (from != null) {
            if(from.isNotEmpty()){
                Log.d("from",from)
                val database = Firebase.database
                val myRef = database.getReference("message").child(id.toString()+from.toString())
                val NmyRef = database.getReference("Note")

                val items: ArrayList<ChatData> = arrayListOf()
                val noteitems: ArrayList<NoteData> = arrayListOf()

                //리사이클러뷰 어댑터 연결
                val rv = findViewById<RecyclerView>(R.id.recycler_view)
                val rvAdapter = ChatAdapter(items , this, nick)
                val Notedapter = Notedapter(noteitems , this)

                rv.adapter = rvAdapter
                rv.layoutManager = LinearLayoutManager(this)

                myRef.addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                        var Chatd =  dataSnapshot.getValue(ChatData::class.java)
                        var name = Chatd?.mynickName
                        var msg = Chatd?.msg
                        var stime = Chatd?.time
                        items.apply {
                            add(ChatData(name.toString(),msg.toString(),stime.toString()))
                        }
                        rvAdapter.notifyDataSetChanged()

                    }
                    override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                    override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

                viewbinding.umsgbtn.setOnClickListener {
                    val time:String = WhatTime().toString()
                    val msg:String = viewbinding.utextmsg.text.toString()
                    if(msg.isNotEmpty()) {
                        var chatnew = ChatData(id.toString(), msg, time)
                        myRef.push().setValue(chatnew)
                        rvAdapter.notifyDataSetChanged()

                        var note = NoteData(id.toString(),from)
                        NmyRef.push().setValue(note)
                        Notedapter.notifyDataSetChanged()
                        //스크롤 포지션
                        rv.scrollToPosition(items.size - 1)
                        viewbinding.utextmsg.setText("")
                    }
                }
            }
        } else{
            val database = Firebase.database
            val myRef = database.getReference("message").child(id.toString()+nick)
            val NmyRef = database.getReference("Note")

            val items: ArrayList<ChatData> = arrayListOf()
            val noteitems: ArrayList<NoteData> = arrayListOf()

            //리사이클러뷰 어댑터 연결
            val rv = findViewById<RecyclerView>(R.id.recycler_view)
            val rvAdapter = ChatAdapter(items , this, nick)
            val Notedapter = Notedapter(noteitems , this)

            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(this)

            myRef.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    var Chatd =  dataSnapshot.getValue(ChatData::class.java)
                    var name = Chatd?.mynickName
                    var msg = Chatd?.msg
                    var stime = Chatd?.time
                    items.apply {
                        add(ChatData(name.toString(),msg.toString(),stime.toString()))
                    }
                    rvAdapter.notifyDataSetChanged()

                }
                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onCancelled(databaseError: DatabaseError) {}
            })

            viewbinding.umsgbtn.setOnClickListener {
                val time:String = WhatTime().toString()
                val msg:String = viewbinding.utextmsg.text.toString()
                if(msg.isNotEmpty()) {
                    var chatnew = ChatData(nick, msg, time)
                    myRef.push().setValue(chatnew)
                    rvAdapter.notifyDataSetChanged()

                    var note = NoteData(nick,id.toString())
                    NmyRef.push().setValue(note)
                    Notedapter.notifyDataSetChanged()
                    //스크롤 포지션
                    rv.scrollToPosition(items.size - 1)
                    viewbinding.utextmsg.setText("")
                }
            }
        }



    }
}