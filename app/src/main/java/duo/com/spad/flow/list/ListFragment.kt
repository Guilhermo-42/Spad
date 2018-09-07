package duo.com.spad.flow.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import duo.com.spad.R
import duo.com.spad.model.ListItem
import duo.com.spad.model.User
import duo.com.spad.model.database.DatabaseUser
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
        //test()

        setListeners()
    }

    private fun setListeners() {
        listScreenFab.setOnClickListener {  }
    }

    private fun setupList() {
        itemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemsRecyclerView.adapter = adapter
    }


    // Test methods
    private fun addTest() {
        val user = User()
        user.name = "guilherme"
        user.email = "guilhermeasrgl9@gmail.com"
        user.type = User.TYPE.GOOGLE
        user.id = "1"

        val firestore = FirebaseFirestore.getInstance()

        user.email?.let {
            firestore.collection("users")
                    .document(it)
                    .set(DatabaseUser)
        }
        firestore.collection("users").get().addOnSuccessListener {
            it
        }
    }

    private fun test() {
        adapter.updateList(getMock())
        adapter.notifyDataSetChanged()
    }

    private fun getMock(): List<ListItem> {
        val listItem = ListItem()
        listItem.description = "Description test"
        listItem.title = "Title test"
        listItem.image = R.drawable.icon_categoria_sport
        listItem.priority = ListItem.Priority.LOW

        val olistItem = ListItem()
        olistItem.description = "Description test medium"
        olistItem.title = "Title test medium"
        olistItem.image = R.drawable.icon_categoria_family
        olistItem.priority = ListItem.Priority.MEDIUM

        val alistItem = ListItem()
        alistItem.description = "Description test house"
        alistItem.title = "Title test very big asuhasuhhdasuh as hell"
        alistItem.image = R.drawable.icon_categoria_house
        alistItem.priority = ListItem.Priority.HIGH

        val salistItem = ListItem()
        salistItem.description = "Description test urgent as hell test big aaaaaaaaaaaa"
        salistItem.title = "Title test very big asuhasuhhdasuh as hell urgent task"
        salistItem.image = R.drawable.icon_categoria_recreation
        salistItem.priority = ListItem.Priority.URGENT

        return listOf(listItem, olistItem, alistItem, salistItem)
    }
}
