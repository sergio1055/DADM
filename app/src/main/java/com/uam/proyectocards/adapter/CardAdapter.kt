package com.uam.proyectocards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.databinding.ListItemCardBinding
import com.uam.proyectocards.fragments.CardListFragmentDirections
import com.uam.proyectocards.model.DeckWithCards

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    lateinit var binding : ListItemCardBinding
    var data =  listOf<Card>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var deckId : Long = 0


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
                                CardListFragmentDirections.actionCardListFragmentToCardEditFragment(id, deckId))
            }

            binding.checkboxMoreInfo?.setOnCheckedChangeListener { buttonView, isChecked ->

                if(isChecked && buttonView is CheckBox) {
                    binding.listItemEasiness.visibility = View.VISIBLE
                    binding.listItemNextDate?.visibility = View.VISIBLE
                    binding.listItemInterval?.visibility = View.VISIBLE
                    binding.checkboxMoreInfo!!.visibility = View.INVISIBLE
                    notifyItemChanged(layoutPosition)
                }

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