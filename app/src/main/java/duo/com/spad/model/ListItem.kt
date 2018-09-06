package duo.com.spad.model

import android.support.annotation.DrawableRes
import java.io.Serializable

/**
 * @author Guilherme
 * @since 06/09/2018
 */
class ListItem : Serializable {

    var title: String? = null

    var description: String? = null

    var priority: Priority? = null

    @DrawableRes
    var image: Int? = null

    enum class Priority {

        LOW,
        MEDIUM,
        HIGH,
        URGENT

    }

}