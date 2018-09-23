package duo.com.spad.flow.list.add

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import duo.com.spad.App
import duo.com.spad.R
import duo.com.spad.flow.category.ChooseCategoryActivity
import duo.com.spad.model.note.Note
import duo.com.spad.model.note.Priority
import duo.com.spad.ui.UiLoader
import kotlinx.android.synthetic.main.activity_add_item.*
import javax.inject.Inject

class AddItemActivity : AppCompatActivity(), AddItemPresenter {

    companion object {
        const val NOTE_EXTRA = "Note Extra"
    }

    @Inject
    lateinit var presenter: AddItemPresenterImpl

    init {
        App.getPresenterComponent().inject(this)
    }

    private val note: Note? by lazy {
        intent.extras?.getSerializable(NOTE_EXTRA) as? Note
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        presenter.updatePresenter(this)

        setupViews()

        setListeners()

        overridePendingTransition(R.anim.slide_in, R.anim.stay_still)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay_still, R.anim.slide_out)
    }

    override fun onTitleNotSetted() {
        titleInputLayout.error = getString(R.string.title_error_hint)
    }

    override fun onDescriptionNotSetted() {
        descriptionInputLayout.error = getString(R.string.description_error_hint)
    }

    override fun onPriorityNotSetted() {
        priorityError.visibility = View.VISIBLE
    }

    override fun onSaveClicked() {
        val bundle = Bundle()
        bundle.putSerializable(ChooseCategoryActivity.NOTE, presenter.model)
        UiLoader.goToActivityWithData(this, ChooseCategoryActivity::class.java, bundle)
    }

    override fun onNoteReceived(note: Note) {
        descriptionInputText.setText(note.description)
        titleInputText.setText(note.title)
        lowPriorityToggle.isChecked = note.priority == Priority.LOW
        mediumPriorityToggle.isChecked = note.priority == Priority.MEDIUM
        highPriorityToggle.isChecked = note.priority == Priority.HIGH
        urgentPriorityToggle.isChecked = note.priority == Priority.URGENT
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
            }
            R.id.addItemConfirm -> {
                presenter.onSaveClicked(
                        titleInputText.text?.toString(),
                        descriptionInputText.text?.toString())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListeners() {
        lowPriorityToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onPriorityClicked(Priority.LOW)
            }
        }
        mediumPriorityToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onPriorityClicked(Priority.MEDIUM)
            }
        }
        highPriorityToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onPriorityClicked(Priority.HIGH)
            }
        }
        urgentPriorityToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onPriorityClicked(Priority.URGENT)
            }
        }
    }

    private fun setupViews() {
        setupActionBar()
        note?.let { presenter.onNoteReceived(it) }

        //TODO Remove this after icons are done
        lowPriorityToggle.buttonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.greenPriority), PorterDuff.Mode.MULTIPLY)
        mediumPriorityToggle.buttonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.yellowPriority), PorterDuff.Mode.MULTIPLY)
        highPriorityToggle.buttonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.orangePriority), PorterDuff.Mode.MULTIPLY)
        urgentPriorityToggle.buttonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.redPriority), PorterDuff.Mode.MULTIPLY)

    }

    private fun setupActionBar() {
        setSupportActionBar(addItemToolbar)
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_return)
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.new_item_label)
        }
    }

}
