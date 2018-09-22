package duo.com.spad.flow.category

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import duo.com.spad.R
import kotlinx.android.synthetic.main.activity_choose_category.*

class ChooseCategoryActivity : AppCompatActivity() {

    companion object {
        const val NOTE = "NoteExtra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category)

        setSupportActionBar(chooseCategoryToolbar)
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_return)
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.category)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
