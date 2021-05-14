package es.uam.dadm.sergiogarcia.projectcards.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.animation.Easing.EaseInOutQuad
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import es.uam.dadm.sergiogarcia.projectcards.R
import es.uam.dadm.sergiogarcia.projectcards.activities.SettingsActivity
import es.uam.dadm.sergiogarcia.projectcards.databinding.FragmentDeckStatisticsBinding
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import es.uam.dadm.sergiogarcia.projectcards.model.Deck
import es.uam.dadm.sergiogarcia.projectcards.model.DeckWithCards
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.StatisticsDeckViewModel


class StatisticsDeckFragment : Fragment() {
    lateinit var binding: FragmentDeckStatisticsBinding
    lateinit var deck : Deck
    lateinit var cards :List<Card>
    private var auth = FirebaseAuth.getInstance()

    private val statisticsDeckViewModel: StatisticsDeckViewModel by lazy {
        ViewModelProvider(this).get(StatisticsDeckViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentDeckStatisticsBinding>(
            inflater,
            R.layout.fragment_deck_statistics,
            container,
            false
        )

        val args = StatisticsDeckFragmentArgs.fromBundle(requireArguments())

        statisticsDeckViewModel.loadDeckId(args.deckId)
        statisticsDeckViewModel.deckWithCards.observe(
            viewLifecycleOwner,
            Observer {
                deck = it[0].deck!!
                cards = it[0].cards
                setupStatisticsChart()
                loadData()
            })

        return binding.root
    }

    private fun setupStatisticsChart() {
        binding.pieChart.setDrawHoleEnabled(true);
        binding.pieChart.setUsePercentValues(true);
        binding.pieChart.setEntryLabelTextSize(12F);
        binding.pieChart.setEntryLabelColor(Color.BLACK);
        binding.pieChart.setCenterTextSize(24F);
        binding.pieChart.setCenterText("Easiness");
        binding.pieChart.getDescription().setEnabled(false);

        val l: Legend = binding.pieChart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun loadData() {

        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(calculatePercentajesOfEasyCards(), "Easy"))
        entries.add(PieEntry(calculatePercentajesOfDoubtCards(), "Doubt"))
        entries.add(PieEntry(calculatePercentajesOfHardCards(), "Hard"))


        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }

        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }

        val dataSet = PieDataSet(entries, "Card Easiness")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(binding.pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        binding.pieChart.setData(data)
        binding.pieChart.invalidate()

        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun calculatePercentajesOfEasyCards() : Float {
        var numberOfEasyCards = 0
        if(cards.size == 0) {
            return 0F
        }
        cards.forEach {
                if(it.easiness <= 1.00) {
                    numberOfEasyCards++
                }
        }

        return (numberOfEasyCards/cards.size).toFloat()
    }

    private fun calculatePercentajesOfDoubtCards() : Float {
        var numberOfDoubtCards = 0
        if(cards.size == 0) {
            return 0F
        }
        cards.forEach {
                if(it.easiness in 1.00..2.00) {
                    numberOfDoubtCards++
                }
        }

        return (numberOfDoubtCards/cards.size).toFloat()
    }

    private fun calculatePercentajesOfHardCards() : Float {
        var numberOfHardCards = 0
        if(cards.size == 0) {
            return 0F
        }
        cards.forEach {
                if(it.easiness > 2.00) {
                    numberOfHardCards++
                }
            }

        return (numberOfHardCards/cards.size).toFloat()
    }

    private fun logOut() {
        auth.signOut()
        SettingsActivity.setLogged(requireContext(), false)
        Snackbar.make(requireView(), R.string.logout_success, Snackbar.LENGTH_LONG)
        this.findNavController().navigate(R.id.action_statisticsDeckFragment_to_authentication_fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }

            R.id.log_out -> {
                logOut()
            }
        }

        return super.onOptionsItemSelected(item)
    }



}