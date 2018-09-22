package duo.com.spad.model.note

import android.support.annotation.DrawableRes
import java.io.Serializable

/**
 * @author Guilherme
 * @since 08/09/2018
 */
data class Category(

        var name: String? = null,

        @DrawableRes
        var image: Int? = null

) : Serializable