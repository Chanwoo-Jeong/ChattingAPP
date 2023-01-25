package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.databinding.HandsupbtnBinding

class HuploadActivity : AppCompatActivity() {
    private lateinit var viewbinding: HandsupbtnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = HandsupbtnBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)

        viewbinding.uploadBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("name",viewbinding.nickname.getText().toString())
            intent.putExtra("postContent",viewbinding.Content.getText().toString())
            setResult(RESULT_OK,intent)
            finish()
            //액티비티를 끝내는 코드
        }

    }
}