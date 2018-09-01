package duo.com.spad.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * @author Guilherme
 * @since 01/09/2018
 */
class UiLoader {

    companion object {

        fun goToActivity(context: Context, activity: Class<*>) {
            context.startActivity(Intent(context, activity))
        }

        fun goToActivityWithData(context: Context, activity: Class<*>, bundle: Bundle) {
            val intent = Intent(context, activity)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

}