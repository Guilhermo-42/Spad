package duo.com.spad.flow.list.add

import duo.com.spad.model.note.Note

/**
 * @author Guilherme
 * @since 08/09/2018
 */
interface AddItemPresenter {

    fun onTitleNotSetted()

    fun onDescriptionNotSetted()

    fun onPriorityNotSetted()

    fun onSaveClicked()

    fun onNoteReceived(note: Note)

    fun onErrorDeletingNote()

    fun onDeleteSuccessful()

}