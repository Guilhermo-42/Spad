package duo.com.spad.flow.list

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import duo.com.spad.R
import duo.com.spad.model.ListItem
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

/**
 * @author Guilherme
 * @since 06/09/2018
 */
class ListAdapter(

        var context: Context,
        private var itemsList: List<ListItem> = LinkedList()

) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList[position]

        holder.title.text = item.title
        holder.description.text = item.description
        item.image?.let { holder.image.setImageResource(it) }

        when (item.priority) {
            ListItem.Priority.LOW -> {
                val greenColor = ContextCompat.getColor(context, R.color.greenPriority)
                holder.title.setTextColor(greenColor)
                holder.description.setTextColor(greenColor)
                holder.image.setColorFilter(greenColor, PorterDuff.Mode.MULTIPLY)
                holder.bordersBackground.setColorFilter(greenColor, PorterDuff.Mode.MULTIPLY)
            }
            ListItem.Priority.MEDIUM -> {
                val yellowColor = ContextCompat.getColor(context, R.color.yellowPriority)
                holder.title.setTextColor(yellowColor)
                holder.description.setTextColor(yellowColor)
                holder.image.setColorFilter(yellowColor, PorterDuff.Mode.MULTIPLY)
                holder.bordersBackground.setColorFilter(yellowColor, PorterDuff.Mode.MULTIPLY)
            }
            ListItem.Priority.HIGH -> {
                val orangeColor = ContextCompat.getColor(context, R.color.orangePriority)
                holder.title.setTextColor(orangeColor)
                holder.description.setTextColor(orangeColor)
                holder.image.setColorFilter(orangeColor, PorterDuff.Mode.MULTIPLY)
                holder.bordersBackground.setColorFilter(orangeColor, PorterDuff.Mode.MULTIPLY)
            }
            ListItem.Priority.URGENT -> {
                val redColor = ContextCompat.getColor(context, R.color.redPriority)
                holder.title.setTextColor(redColor)
                holder.description.setTextColor(redColor)
                holder.image.setColorFilter(redColor, PorterDuff.Mode.MULTIPLY)
                holder.bordersBackground.setColorFilter(redColor, PorterDuff.Mode.MULTIPLY)
            }
        }

    }

    fun updateList(newList: List<ListItem>) {
        this.itemsList = newList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val bordersBackground: Drawable = itemView.listItemRoot.background
        val title: TextView = itemView.itemListTitle
        val description: TextView = itemView.itemListDescription
        val image: ImageView = itemView.itemListImage

    }

}