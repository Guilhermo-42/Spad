package duo.com.spad.flow.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import duo.com.spad.App
import duo.com.spad.R
import duo.com.spad.flow.list.add.AddItemActivity
import duo.com.spad.model.note.Note
import duo.com.spad.ui.UiLoader
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment : Fragment(), ListPresenter {

    companion object {
        const val TAG = "LISTFRAGMENTTAG"
        fun newInstance() = ListFragment()
    }

    private val adapter by lazy {
        ListAdapter(requireContext())
    }

    @Inject
    lateinit var presenterImpl: ListPresenterImpl

    init {
        App.getPresenterComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenterImpl.setNewPresenter(this)

        setupList()

        setListeners()

        presenterImpl.tryRetrieveNotes()
    }

    override fun onLoadNotes() {
        listLoader.visibility = View.VISIBLE
        emptyStateMessage.visibility = View.GONE
        itemsRecyclerView.visibility = View.GONE
    }

    override fun onNotesLoaded(notes: List<Note>) {
        listLoader.visibility = View.GONE
        itemsRecyclerView.visibility = View.VISIBLE
        itemMeter.text = notes.size.toString()
        adapter.updateList(notes)
        adapter.notifyDataSetChanged()
    }

    override fun onErrorLoadingNotes() {
        Toast.makeText(requireContext(), getString(R.string.error_loading_notes), Toast.LENGTH_LONG).show()
    }

    override fun showEmptyState() {
        emptyStateMessage.visibility = View.VISIBLE
        itemsRecyclerView.visibility = View.GONE
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