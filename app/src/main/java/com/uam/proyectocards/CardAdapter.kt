package com.uam.proyectocards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.uam.proyectocards.databinding.ListItemCardBinding

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    lateinit var binding : ListItemCardBinding
    var data =  listOf<Card>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var card: Card
        fun bind(card: Card) {
            this.card = card
            binding.card = card
        }

        init {
            binding.listItemQuestion.setOnClickListener {
                val id = card.id
                it.findNavController()
                        .navigate(
                            CardListFragmentDirections
                                .actionCardListFragmentToCardEditFragment(id))
            }
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