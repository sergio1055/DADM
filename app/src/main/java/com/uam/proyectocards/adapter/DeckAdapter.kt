package com.uam.proyectocards.adapter


import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.uam.proyectocards.database.CardDatabase
import com.uam.proyectocards.databinding.ListItemDeckBinding
import com.uam.proyectocards.fragments.DeckListFragmentDirections
import com.uam.proyectocards.model.DeckWithCards
import java.util.concurrent.Executors

class DeckAdapter() : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    lateinit var binding : ListItemDeckBinding

    private val executor = Executors.newSingleThreadExecutor()

    var data =  listOf<DeckWithCards>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var deck: DeckWithCards
        fun bind(deck: DeckWithCards) {
            this.deck = deck
            binding.deck = deck
        }

        init {
            binding.listDeckName.setOnClickListener {
                val id = deck.deck.id
                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentToCardListFragment2(id))
            }

            binding.editDeck.setOnClickListener {
                val id = deck.deck.id
                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentToDeckEditFragment(id))
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