package com.example.testapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.HandsupbtnBinding


class HuploadActivity : AppCompatActivity() {
    private lateinit var viewbinding: HandsupbtnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = HandsupbtnBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)

        viewbinding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                viewbinding.seekbarValue.setText(String.format("%d h", seekBar.getProgress()))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

                // [SeekBar 터치 시작]
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // [SeekBar 터치 종료]
            }
        })
        viewbinding.toggleButton.setOnClickListener{
            if(viewbinding.toggleButton.isChecked){
                viewbinding.toggleButton.setTextColor(Color.parseColor("#215449"))
            } else{
                viewbinding.toggleButton.setTextColor(Color.parseColor("#000000"))
            }
        }
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