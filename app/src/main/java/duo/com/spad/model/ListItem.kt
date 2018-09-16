package duo.com.spad.model

import java.io.Serializable

/**
 * @author Guilherme
 * @since 06/09/2018
 */
class ListItem : Serializable {

    var title: String? = null

    var description: String? = null

    var priority: Priority? = null

    var category: Category? = null

    fun isValidWithoutCategory(): Boolean {
        return !title.isNullOrEmpty() && !description.isNullOrEmpty() && priority != null
    }

}