package com.example.testapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(private val items: MutableList<ChatData>, private val context: Context) :
    RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
        //한 화면에 그려지는 아이템 개수만큼 레이아웃 생성
        // 뷰홀더는 현재 화면에 보여지는 개수만큼만 생성되고
        // 스크롤 될 경우 가장 위의 뷰홀더를 재사용한 후 데이터만 바꿔줌
        // 한 화면에 여덟 줄이 보여지면 여덟 번 호출됨
        //뷰홀더를 생성(레이아웃 생성)하는 코드 작성
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {
        // 생성된 아이템 레이아웃에 값 입력 후 목록에 출력
        // 뷰홀더가 재활용될때 실행되는 메소드 작성
        holder.bindItems(items[position])


    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: ChatData) {

            val rv_msg = itemView.findViewById<TextView>(R.id.rv_msg_textView)
            rv_msg.text = item.msg

            val rv_time = itemView.findViewById<TextView>(R.id.rv_time)
            rv_time.text = item.time


        }
    }
}