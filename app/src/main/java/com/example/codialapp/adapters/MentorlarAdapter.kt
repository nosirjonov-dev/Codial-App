package com.example.codialapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.codialapp.databinding.ItemKursBinding
import com.example.codialapp.databinding.ItemMentorBinding
import com.example.codialapp.models.Kurslar
import com.example.codialapp.models.Mentorlar

class MentorlarAdapter(val rvAction: RvAction, val list:ArrayList<Mentorlar>):RecyclerView.Adapter<MentorlarAdapter.Vh>() {
    inner class Vh(var itemMentorBinding: ItemMentorBinding):ViewHolder(itemMentorBinding.root){
        fun onBind(mentorlar: Mentorlar, position: Int){
            itemMentorBinding.name.text = mentorlar.name
            itemMentorBinding.lastName.text = mentorlar.lastName
            itemMentorBinding.edit.setOnClickListener {
                rvAction.editClick(mentorlar)
            }
            itemMentorBinding.delete.setOnClickListener {
                rvAction.deleteClick(mentorlar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMentorBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }
    interface RvAction{
        fun editClick(mentorlar: Mentorlar)
        fun deleteClick(mentorlar: Mentorlar)
    }
}