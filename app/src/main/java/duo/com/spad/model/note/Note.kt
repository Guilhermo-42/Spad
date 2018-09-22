package duo.com.spad.model.note

import java.io.Serializable

/**
 * @author Guilherme
 * @since 06/09/2018
 */
data class Note(

        var title: String? = null,

        var description: String? = null,

        var priority: Priority? = null,

        var category: Category? = null

): Serializable {

    fun isValidWithoutCategory(): Boolean {
        return !title.isNullOrEmpty() && !description.isNullOrEmpty() && priority != null
    }

}