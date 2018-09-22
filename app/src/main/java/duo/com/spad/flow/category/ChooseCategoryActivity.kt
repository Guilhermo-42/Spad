package duo.com.spad.flow.category

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import duo.com.spad.R

class ChooseCategoryActivity : AppCompatActivity() {

    companion object {
        const val NOTE = "NoteExtra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category)
    }

}
