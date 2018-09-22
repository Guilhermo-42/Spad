package duo.com.spad.flow.category

import duo.com.spad.model.User
import duo.com.spad.model.note.Category
import duo.com.spad.model.note.Note

/**
 * @author Guilherme
 * @since 22/09/2018
 */
interface ChooseCategoryPresenter {

    fun onDefaultCategoriesLoaded(categories: List<Category>)

    fun onCategorySelected(category: Category)

    fun onCategoryRequired()

    fun onSaveClicked(note: Note)

    fun onSaveFailed()

    fun onSaveSuccess()

    fun onGetAllNotesSuccess(notes: MutableList<Note>)

    fun onTrySavesToUserFinished(user: User)

}