package duo.com.spad.flow.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import duo.com.spad.R
import duo.com.spad.flow.list.ListFragment
import duo.com.spad.ui.UiLoader
import kotlinx.android.synthetic.main.activity_main_flow.*

class MainFlowActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_list -> {
                UiLoader.loadFragment(supportFragmentManager, R.id.mainContainer, ListFragment.newInstance(), ListFragment.TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_flow)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
