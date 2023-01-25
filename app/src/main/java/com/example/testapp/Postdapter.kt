package com.example.testapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class Postdapter(private val items: MutableList<PostData>, private val context: Context) :
    RecyclerView.Adapter<Postdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Postdapter.ViewHolder {
        //한 화면에 그려지는 아이템 개수만큼 레이아웃 생성
        // 뷰홀더는 현재 화면에 보여지는 개수만큼만 생성되고
        // 스크롤 될 경우 가장 위의 뷰홀더를 재사용한 후 데이터만 바꿔줌
        // 한 화면에 여덟 줄이 보여지면 여덟 번 호출됨
        //뷰홀더를 생성(레이아웃 생성)하는 코드 작성
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Postdapter.ViewHolder, position: Int) {
        // 생성된 아이템 레이아웃에 값 입력 후 목록에 출력
        // 뷰홀더가 재활용될때 실행되는 메소드 작성
        holder.bindItems(items[position])


    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: PostData) {

            val informationNick = itemView.findViewById<TextView>(R.id.information)
            informationNick.text = item.name

            val postContent = itemView.findViewById<TextView>(R.id.postContent)
            postContent.text = item.postContent


        }
    }
}