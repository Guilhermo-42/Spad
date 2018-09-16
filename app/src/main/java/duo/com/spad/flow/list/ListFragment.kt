package duo.com.spad.flow.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import duo.com.spad.R
import duo.com.spad.flow.list.add.AddItemActivity
import duo.com.spad.ui.UiLoader
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    companion object {
        const val TAG = "LISTFRAGMENTTAG"
        fun newInstance() = ListFragment()
    }

    private val adapter by lazy {
        ListAdapter(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange))

        setupList()

        setListeners()
    }

    private fun setListeners() {
        listScreenFab.setOnClickListener {
            UiLoader.goToActivity(requireContext(), AddItemActivity::class.java)
        }
    }

    private fun setupList() {
        itemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemsRecyclerView.adapter = adapter
    }

}