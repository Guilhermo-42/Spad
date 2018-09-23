package duo.com.spad.flow.category

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import duo.com.spad.R
import duo.com.spad.model.User
import duo.com.spad.model.note.Category
import duo.com.spad.model.note.Note
import duo.com.spad.network.DatabaseCollections

/**
 * @author Guilherme
 * @since 22/09/2018
 */
class ChooseCategoryPresenterImpl {

    private var presenter: ChooseCategoryPresenter? = null

    private var category: Category? = null

    fun setNewPresenter(presenter: ChooseCategoryPresenter) {
        this.presenter = presenter
    }

    fun getDefaultCategories(context: Context) {
        presenter?.onDefaultCategoriesLoaded(listOf(
                Category(context.getString(R.string.default_category_home),
                        R.drawable.ic_default_category_house),
                Category(context.getString(R.string.default_category_recreation),
                        R.drawable.ic_default_category_recreation),
                Category(context.getString(R.string.default_category_work),
                        R.drawable.ic_default_category_work),
                Category(context.getString(R.string.default_category_sports),
                        R.drawable.ic_default_category_sport),
                Category(context.getString(R.string.default_category_trip),
                        R.drawable.ic_default_category_trip),
                Category(context.getString(R.string.default_category_family),
                        R.drawable.ic_default_category_family),
                Category(context.getString(R.string.default_category_food),
                        R.drawable.ic_default_category_food)
        ))
    }

    fun onCategorySelected(category: Category) {
        this.category = category
    }

    fun onSaveClicked(note: Note) {
        if (category != null) {
            note.category = category
            presenter?.onSaveClicked(note)
        } else {
            presenter?.onCategoryRequired()
        }
    }

    fun trySavesToUser(newNotes: MutableList<Note>) {
        FirebaseAuth.getInstance().currentUser?.email?.let { userEmail ->
            FirebaseFirestore.getInstance()
                    .collection(DatabaseCollections.USERS.description)
                    .document(userEmail)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = task.result.toObject(User::class.java)
                            user?.notes = newNotes
                            user?.let { presenter?.onTrySavesToUserFinished(it) }
                        } else {
                            presenter?.onSaveFailed()
                        }
                    }
        }
    }

    @Synchronized
    fun tryGetAllNotes(newNote: Note) {
        val database = FirebaseFirestore.getInstance()

        FirebaseAuth.getInstance().currentUser?.email?.let { userEmail ->
            database.collection(DatabaseCollections.USERS.description)
                    .document(userEmail)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val notes = task.result.toObject(User::class.java)?.notes ?: listOf()
                            val newNotes = setupList(notes, newNote)
                            presenter?.onGetAllNotesSuccess(newNotes)
                        } else {
                            presenter?.onSaveFailed()
                        }
                    }
        }
    }

    @Synchronized
    private fun setupList(oldNotes: List<Note>, newNote: Note): MutableList<Note> {
        val newNotes = mutableListOf<Note>()

        if (oldNotes.isEmpty()) {
            newNote.id = 0
            newNotes.add(newNote)
            return newNotes
        } else {
            newNotes.addAll(oldNotes)

            synchronized(newNotes) {
                oldNotes.forEach { currentNote ->
                    if (currentNote.id == newNote.id) {
                        newNotes[oldNotes.indexOf(currentNote)] = newNote
                        return newNotes
                    }
                }
            }
        }

        newNote.id = oldNotes.lastIndex + 1
        newNotes.add(newNote)

        return newNotes
    }

    fun updateUser(user: User) {
        user.email?.let {
            FirebaseFirestore.getInstance()
                    .collection(DatabaseCollections.USERS.description)
                    .document(it)
                    .set(user)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            presenter?.onSaveSuccess()
                        } else {
                            presenter?.onSaveFailed()
                        }
                    }
        }
    }

}