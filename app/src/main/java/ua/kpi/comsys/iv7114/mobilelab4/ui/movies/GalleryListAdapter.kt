package ua.kpi.comsys.iv7114.mobilelab4.ui.movies

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ua.kpi.comsys.iv7114.mobilelab4.R

class GalleryListAdapter(
    private val values: List<GalleryItem>
) : RecyclerView.Adapter<GalleryListAdapter.ViewHolder>() {
    private val rows: List<GalleryRow> = splitRows(values)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val r = when (viewType) {
            0 -> R.layout.fragment_gallery_row0
            else -> R.layout.fragment_gallery_row1
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(r, parent, false)
        return ViewHolder(view, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = rows[position]
        row.items.withIndex().forEach {
            holder.setItem(it.index, it.value)
        }
    }

    override fun getItemCount(): Int = rows.size

    private fun splitRows(items: List<GalleryItem>): List<GalleryRow> {
        return items.withIndex().groupBy { it.index / 5 }.toSortedMap().map { it0 -> GalleryRow(it0.key % 2, it0.value.map { it.value }) }
    }

    inner class ViewHolder(view: View, type: Int) : RecyclerView.ViewHolder(view) {
        val items = arrayOf<ImageView>(
            view.findViewById(R.id.item_number0),
            view.findViewById(R.id.item_number1),
            view.findViewById(R.id.item_number2),
            view.findViewById(R.id.item_number3),
            view.findViewById(R.id.item_number4)
        )
        fun setItem(index: Int, item: GalleryItem) {
            Picasso.get().load(item.icon).error(R.drawable.ic_launcher_foreground).into(items[index])
        }
    }
    data class GalleryRow(val kind: Int, val items: List<GalleryItem>)
}