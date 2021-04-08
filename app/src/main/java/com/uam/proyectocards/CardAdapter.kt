package com.uam.proyectocards

import android.app.LauncherActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.encode.databinding.ActivityStudyBinding
import com.example.encode.databinding.ListItemCardBinding
import com.example.proyectocards.databinding.ListItemCardBinding
import timber.log.Timber

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    lateinit var binding : ListItemCardBinding
    var data =  listOf<Card>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(card: Card) {
            binding.card = card
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemCardBinding.inflate(layoutInflater, parent, false)
        return CardHolder(binding.root)
    }

    override fun onBindViewHolder(
            holder: CardHolder,
            position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}