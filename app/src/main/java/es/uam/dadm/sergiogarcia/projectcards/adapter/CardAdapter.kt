package es.uam.dadm.sergiogarcia.projectcards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.dadm.sergiogarcia.projectcards.databinding.ListItemCardBinding
import es.uam.dadm.sergiogarcia.projectcards.fragments.CardListFragmentDirections
import es.uam.dadm.sergiogarcia.projectcards.model.Card

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

            binding.moreInfoText.setOnClickListener {
                    binding.listItemEasiness.visibility = View.VISIBLE
                    binding.listItemNextDate?.visibility = View.VISIBLE
                    binding.listItemInterval?.visibility = View.VISIBLE
                    binding.moreInfoText.visibility = View.INVISIBLE
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

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}