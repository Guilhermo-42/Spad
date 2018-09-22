package duo.com.spad.flow.category

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import duo.com.spad.App
import duo.com.spad.R
import duo.com.spad.flow.main.MainFlowActivity
import duo.com.spad.model.User
import duo.com.spad.model.note.Category
import duo.com.spad.model.note.Note
import duo.com.spad.ui.UiLoader
import kotlinx.android.synthetic.main.activity_choose_category.*
import javax.inject.Inject

class ChooseCategoryActivity : AppCompatActivity(), ChooseCategoryPresenter {

    companion object {
        const val NOTE = "NoteExtra"
    }

    private val adapter: CategoryListAdapter by lazy {
        CategoryListAdapter(this)
    }

    private val note: Note? by lazy {
        intent.extras.getSerializable(NOTE) as? Note
    }

    @Inject
    lateinit var presenter: ChooseCategoryPresenterImpl

    init {
        App.getPresenterComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category)

        presenter.setNewPresenter(this)

        setupViews()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
            }
            R.id.chooseCategoryConfirm -> {
                note?.let { presenter.onSaveClicked(it) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.choose_category_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDefaultCategoriesLoaded(categories: List<Category>) {
        adapter.updateList(categories)
        adapter.notifyDataSetChanged()
    }

    override fun onCategorySelected(category: Category) {
        presenter.onCategorySelected(category)
        adapter.onNewCategorySelected(category)
        adapter.notifyDataSetChanged()
    }

    override fun onCategoryRequired() {
        Toast.makeText(this, getString(R.string.category_needed), Toast.LENGTH_SHORT).show()
    }

    override fun onSaveClicked(note: Note) {
        chooseCategoryLoader.visibility = View.VISIBLE
        presenter.tryGetAllNotes(note)
    }

    override fun onSaveFailed() {
        Toast.makeText(this, getString(R.string.category_save_failed), Toast.LENGTH_SHORT).show()
    }

    override fun onGetAllNotesSuccess(notes: MutableList<Note>) {
        presenter.trySavesToUser(notes)
    }

    override fun onTrySavesToUserFinished(user: User) {
        presenter.updateUser(user)
    }

    override fun onSaveSuccess() {
        chooseCategoryLoader.visibility = View.GONE
        UiLoader.goToActivityNoStack(this, MainFlowActivity::class.java)
    }

    private fun setupViews() {
        setupToolbar()
        setupList()
    }

    private fun setupToolbar() {
        setSupportActionBar(chooseCategoryToolbar)
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_return)
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.category)
        }
    }

    private fun setupList() {
        adapter.setNewPresenter(this)
        categoryList.layoutManager = LinearLayoutManager(this)
        categoryList.adapter = adapter
        presenter.getDefaultCategories(this)
    }

}