package duo.com.spad.flow.category

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import duo.com.spad.R
import duo.com.spad.model.note.Category
import kotlinx.android.synthetic.main.category_list_item.view.*
import java.util.*

/**
 * @author Guilherme
 * @since 22/09/2018
 */
class CategoryListAdapter(

        var context: Context,
        private var categories: MutableList<Category> = LinkedList(),
        private var currentCategory: Category? = null

) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    var presenter: ChooseCategoryPresenter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false))
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]

        if (category == currentCategory) {
            holder.itemView.isSelected = true
            holder.categoryImage.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
            holder.categoryName.setTextColor(Color.WHITE)
        } else {
            holder.itemView.isSelected = false
            holder.categoryImage.setColorFilter(ContextCompat.getColor(context, R.color.hintTextColor), PorterDuff.Mode.MULTIPLY)
            holder.categoryName.setTextColor(ContextCompat.getColor(context, R.color.hintTextColor))
        }

        category.image?.let { holder.categoryImage.setImageResource(it) }
        category.name?.let { holder.categoryName.text = it }

        holder.itemView.setOnClickListener {
            presenter?.onCategorySelected(category)
        }

    }

    fun onNewCategorySelected(category: Category) {
        this.currentCategory = category
    }

    fun setNewPresenter(presenter: ChooseCategoryPresenter) {
        this.presenter = presenter
    }

    fun updateList(newList: List<Category>) {
        this.categories.addAll(newList)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val categoryImage: ImageView = itemView.categoryImage
        val categoryName: TextView = itemView.categoryName

    }

}