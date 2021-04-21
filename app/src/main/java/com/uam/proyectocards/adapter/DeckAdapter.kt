package com.uam.proyectocards.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.uam.proyectocards.CardsApplication
import com.uam.proyectocards.R
import com.uam.proyectocards.databinding.ListItemDeckBinding
import com.uam.proyectocards.fragments.DeckListFragmentDirections
import com.uam.proyectocards.model.Deck

class DeckAdapter() : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    lateinit var binding : ListItemDeckBinding

    var data =  listOf<Deck>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var deck: Deck
        fun bind(deck: Deck) {
            this.deck = deck
            binding.deck = deck
        }

        init {
            binding.listDeckName.setOnClickListener {
                val id = deck.id
                CardsApplication.cards = deck.cards
                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentToCardListFragment2(id))
            }

            binding.checkboxRemove.setOnCheckedChangeListener { buttonView, isChecked ->
                CardsApplication.removeDeck(deck)
                notifyItemRemoved(absoluteAdapterPosition)
            }

            }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemDeckBinding.inflate(layoutInflater, parent, false)
        return DeckHolder(binding.root)
    }

    override fun onBindViewHolder(
            holder: DeckHolder,
            position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}