package duo.com.spad.flow.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import duo.com.spad.R
import duo.com.spad.model.User
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    companion object {
        const val USER_EXTRA = "User"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        getData()
    }

    private fun getData() {
        val user: User = intent.getSerializableExtra(USER_EXTRA) as User
        testUser.text = StringBuilder()
                .appendln(user.id)
                .appendln(user.name)
                .appendln(user.email)
                .appendln(user.type)
                .appendln(user.photoUri)
    }
}
