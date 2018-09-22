package duo.com.spad.flow.list

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import duo.com.spad.model.note.Note
import duo.com.spad.network.DatabaseCollections

/**
 * @author Guilherme
 * @since 22/09/2018
 */
class ListPresenterImpl {

    private var presenter: ListPresenter? = null

    fun setNewPresenter(presenter: ListPresenter){
        this.presenter = presenter
    }

    fun tryRetrieveNotes() {
        presenter?.onLoadNotes()

        val database = FirebaseFirestore.getInstance()

        FirebaseAuth.getInstance().currentUser?.email?.let { userEmail ->
            database.collection(DatabaseCollections.USERS.description)
                    .document(userEmail)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                        } else  {
                            presenter?.onErrorLoadingNotes()
                        }
                    }
        }
    }

}