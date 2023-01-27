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
        var id : String = "three"
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            viewBinding = FragmentSecondBinding.inflate(layoutInflater)

//            val bundle = arguments
            val noteitems: ArrayList<NoteData> = arrayListOf()

            var rv = viewBinding.recyclerNote
            val rvAdapter = Notedapter(noteitems,requireActivity())
            //리사이클러뷰 어댑터 연결
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(requireActivity())
//            if(bundle != null) {

                // Write a message to the database
                val database = Firebase.database
                val myRef = database.getReference("Note")

                myRef.addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                        var Note =  dataSnapshot.getValue(NoteData::class.java)
                        var from = Note?.from
                        var to = Note?.to

                        if(from != id && to == id) {
                            noteitems.apply {
                                add(NoteData(from.toString(), to.toString()))
                            }
                            rvAdapter.notifyDataSetChanged()
                        }
                    }
                    override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                    override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                    override fun onCancelled(databaseError: DatabaseError) {}
                })



            return  viewBinding.root
        }

}