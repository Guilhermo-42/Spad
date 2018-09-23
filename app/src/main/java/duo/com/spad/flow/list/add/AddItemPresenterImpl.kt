package duo.com.spad.flow.list.add

import duo.com.spad.model.note.Note
import duo.com.spad.model.note.Priority

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

}