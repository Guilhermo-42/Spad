package duo.com.spad.flow.list.add

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import duo.com.spad.model.User
import duo.com.spad.model.note.Note
import duo.com.spad.model.note.Priority
import duo.com.spad.network.DatabaseCollections

/**
 * @author Guilherme
 * @since 08/09/2018
 */
class AddItemPresenterImpl {

    var model: Note = Note()

    private var presenter: AddItemPresenter? = null

    fun updatePresenter(presenter: AddItemPresenter) {
        this.presenter = presenter
    }

    fun onPriorityClicked(priority: Priority) {
        model.priority = priority
    }

    fun onSaveClicked(title: String?, description: String?) {
        model.title = title
        model.description = description

        if (model.isValidWithoutCategory()) {
            presenter?.onSaveClicked()
            return
        }

        if (model.priority == null) {
            presenter?.onPriorityNotSetted()
        }

        if (model.description.isNullOrEmpty()) {
            presenter?.onDescriptionNotSetted()
        }

        if (model.title.isNullOrEmpty()) {
            presenter?.onTitleNotSetted()
        }

    }

    fun onNoteReceived(note: Note) {
        this.model = note
        presenter?.onNoteReceived(model)
    }

    fun deleteNote(note: Note) {
        FirebaseAuth.getInstance().currentUser?.email?.let {
            FirebaseFirestore.getInstance()
                    .collection(DatabaseCollections.USERS.description)
                    .document(it)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = task.result.toObject(User::class.java)
                            val newNotes = tryDeleteNotes(user?.notes.orEmpty(), note)
                            user?.let { it1 -> trySaveNewNotes(newNotes, it1) }
                        } else {
                            presenter?.onErrorDeletingNote()
                        }
                    }
        }
    }

    private fun tryDeleteNotes(allNotes: List<Note>, noteToBeDeleted: Note): MutableList<Note> {
        val mAllNotes = allNotes.toMutableList()

        synchronized(allNotes) {
            allNotes.forEach { currentNote ->
                if (currentNote.id == noteToBeDeleted.id) {
                    val index = allNotes.indexOf(currentNote)
                    mAllNotes.removeAt(index)
                    return mAllNotes
                }
            }
        }
        return mAllNotes
    }

    private fun trySaveNewNotes(newNotes: List<Note>, user: User) {
        user.notes = newNotes
        user.email?.let {
            FirebaseFirestore.getInstance()
                    .collection(DatabaseCollections.USERS.description)
                    .document(it)
                    .set(user)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            presenter?.onDeleteSuccessful()
                        } else {
                            presenter?.onErrorDeletingNote()
                        }
                    }
        }
    }

}