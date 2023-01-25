package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityMainBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)


        supportFragmentManager
            .beginTransaction()
            .replace(viewbinding.frameFragment.id,FirstFragment())
            .commitAllowingStateLoss()

        viewbinding.alertBtn.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(viewbinding.frameFragment.id,FirstFragment())
                .commitAllowingStateLoss()
        }

        viewbinding.msgBtn.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(viewbinding.frameFragment.id,SecondFragment())
                .commitAllowingStateLoss()
        }

        getResultText = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val name = result.data?.getStringExtra("name")
                val postContent = result.data?.getStringExtra("postContent")

                val firstFragment =FirstFragment()
                val bundle = Bundle()
                bundle.putString("name", name.toString())
                bundle.putString("postContent", postContent.toString())
                firstFragment.arguments = bundle

                supportFragmentManager
                    .beginTransaction()
                    .replace(viewbinding.frameFragment.id,firstFragment)
                    .commitAllowingStateLoss()
            }
        }





        //핸즈업 올리는 버튼
        viewbinding.handsuploadbtn.setOnClickListener{
            Log.d("MainActivity","im upload button")
            val intent = Intent(this,HuploadActivity::class.java)
            getResultText.launch(intent)
        }


    }
}