package duo.com.spad.flow.category

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import duo.com.spad.R

class ChooseCategoryActivity : AppCompatActivity() {

    companion object {
        val LIST_ITEM = ChooseCategoryActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category)
    }
}
