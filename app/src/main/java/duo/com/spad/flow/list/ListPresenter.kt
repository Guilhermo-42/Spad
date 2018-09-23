package duo.com.spad.flow.list

import duo.com.spad.model.note.Note

/**
 * @author Guilherme
 * @since 22/09/2018
 */
interface ListPresenter {

    fun onLoadNotes()

    fun onNotesLoaded(notes: List<Note>)

    fun onErrorLoadingNotes()

    fun showEmptyState()

    fun onNotePressed(note: Note)

}