package com.example.testapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.FragmentFirstBinding

import com.example.testapp.databinding.FragmentSecondBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

        private lateinit var viewBinding: FragmentSecondBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            viewBinding = FragmentSecondBinding.inflate(layoutInflater)

            val bundle = arguments
            val items: ArrayList<ChatData> = arrayListOf()

            var rv = viewBinding.recyclerNote
            val rvAdapter = Notedapter(items,requireActivity())
            //리사이클러뷰 어댑터 연결
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(requireActivity())
            if(bundle != null) {

                val idname: String? = this.arguments?.getString("id")

                // Write a message to the database
                val database = Firebase.database
                val myRef = database.getReference("message").child(idname.toString())

                myRef.addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                        var chated =  dataSnapshot.getValue(ChatData::class.java)
                        var name = chated?.mynickName
                        var newmsg = chated?.msg
                        var time = chated?.time
                        Log.d("newmsg",newmsg.toString())

                        items.apply {
                            add(ChatData(name.toString(), newmsg.toString(),""))
                        }
                        rvAdapter.notifyDataSetChanged()

                    }
                    override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                    override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                    override fun onCancelled(databaseError: DatabaseError) {}
                })
            } else{
                Log.d("error", "번들이 비었습니다.")
            }



//        val myRef2 = database.getReference("postRoom").child("room")


            return  viewBinding.root
        }

}