package com.example.testapp

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val items: List<ChatData>, private val context: Context, private val mynickName : String) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        //한 화면에 그려지는 아이템 개수만큼 레이아웃 생성
        // 뷰홀더는 현재 화면에 보여지는 개수만큼만 생성되고
        // 스크롤 될 경우 가장 위의 뷰홀더를 재사용한 후 데이터만 바꿔줌
        // 한 화면에 여덟 줄이 보여지면 여덟 번 호출됨
        //뷰홀더를 생성(레이아웃 생성)하는 코드 작성
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        // 생성된 아이템 레이아웃에 값 입력 후 목록에 출력
        // 뷰홀더가 재활용될때 실행되는 메소드 작성
        holder.bindItems(items[position])


    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: ChatData) {

            val msgBox = itemView.findViewById<LinearLayout>(R.id.msgBox)
            val rv_msg = itemView.findViewById<TextView>(R.id.rv_msg_textView)
            rv_msg.text = item.msg

            val rv_time = itemView.findViewById<TextView>(R.id.rv_time)
            rv_time.text = item.time

            //text 위치 조정정
           if(item.mynickName.equals(mynickName)){
                Log.d("who", "its me")
               msgBox.setGravity(Gravity.LEFT)

            } else {
               Log.d("who", "its other")
               msgBox.setGravity(Gravity.RIGHT)
               //drawable 설정
              rv_msg.background = ContextCompat.getDrawable(context,R.drawable.chattingbackgroundother)
              rv_msg.setTextColor(Color.parseColor("#FFFFFF"))
            }

        }
    }
}