package duo.com.spad.model

import android.support.annotation.DrawableRes
import java.io.Serializable

/**
 * @author Guilherme
 * @since 08/09/2018
 */
class Category : Serializable{

    var name: String? = null

    @DrawableRes
    var image: Int? = null

}