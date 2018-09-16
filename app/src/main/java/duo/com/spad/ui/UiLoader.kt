package duo.com.spad.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

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

        fun loadFragmentNoBackstack(fm: FragmentManager, @IdRes frame: Int, fragment: Fragment, tag: String) {
            if (fm.findFragmentByTag(tag) != null) {
                fm.popBackStack()
            }

            fm.beginTransaction()
                    .replace(frame, fragment, tag)
                    .commit()
        }

    }

}