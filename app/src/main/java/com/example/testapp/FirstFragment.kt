package com.example.testapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.FragmentFirstBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var viewBinding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentFirstBinding.inflate(layoutInflater)
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("postRoom")

        val bundle = arguments
        if(bundle != null) {
            val id: String? = this.arguments?.getString("id")
            val name: String? = this.arguments?.getString("name")
            val postContent: String? = this.arguments?.getString("postContent")
            var postRoom = PostData(id.toString(),name.toString(),postContent.toString())
            myRef.push().setValue(postRoom)
        } else{
            Log.d("error", "번들이 비었습니다.")
        }

        val items: ArrayList<PostData> = arrayListOf()
        //리사이클러뷰 어댑터 연결
        var rv = viewBinding.recyclerPost
        val rvAdapter = Postdapter(items,requireActivity())
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireActivity())

//        val myRef2 = database.getReference("postRoom").child("room")

        myRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                var postRoom =  dataSnapshot.getValue(PostData::class.java)
                var id = postRoom?.id
                var name = postRoom?.name
                var postContent = postRoom?.postContent

                items.apply {
                    add(PostData(id.toString(),name.toString(), postContent.toString()))
                }
                rvAdapter.notifyDataSetChanged()
            }
            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })




        return  viewBinding.root
    }

}