package duo.com.spad.flow.list.add

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import duo.com.spad.App
import duo.com.spad.R
import duo.com.spad.flow.category.ChooseCategoryActivity
import duo.com.spad.model.Priority
import duo.com.spad.ui.UiLoader
import kotlinx.android.synthetic.main.activity_add_item.*
import javax.inject.Inject

class AddItemActivity : AppCompatActivity(), AddItemPresenter {

    @Inject
    lateinit var presenter: AddItemPresenterImpl

    init {
        App.getPresenterComponent().inject(this)
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
        bundle.putSerializable(ChooseCategoryActivity.LIST_ITEM, presenter.model)
        UiLoader.goToActivityWithData(this, ChooseCategoryActivity::class.java, bundle)
    }

    private fun setListeners() {
        addItemBackButton.setOnClickListener { onBackPressed() }

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

        addItemSave.setOnClickListener {
            presenter.onSaveClicked(
                    titleInputText.text?.toString(),
                    descriptionInputText.text?.toString())
        }
    }

    private fun setupViews() {
        addItemBackButton.setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.MULTIPLY)
        addItemSave.setColorFilter(ContextCompat.getColor(this, R.color.greenPriority), PorterDuff.Mode.MULTIPLY)

        //TODO Remove this after icons are done
        lowPriorityToggle.buttonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.greenPriority), PorterDuff.Mode.MULTIPLY)
        mediumPriorityToggle.buttonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.yellowPriority), PorterDuff.Mode.MULTIPLY)
        highPriorityToggle.buttonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.orangePriority), PorterDuff.Mode.MULTIPLY)
        urgentPriorityToggle.buttonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.redPriority), PorterDuff.Mode.MULTIPLY)

    }

}
