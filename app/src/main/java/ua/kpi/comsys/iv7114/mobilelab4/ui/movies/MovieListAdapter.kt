package ua.kpi.comsys.iv7114.mobilelab4.ui.movies

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.iv7114.mobilelab4.MainActivity
import ua.kpi.comsys.iv7114.mobilelab4.R
import ua.kpi.comsys.iv7114.mobilelab4.assetDrawable
import java.io.IOException
import java.io.InputStream

class MovieListAdapter(private val items: List<MovieModel>):
    RecyclerView.Adapter<MovieViewHolder>(), Filterable {
    var filteredData: List<MovieModel> = items
    var mFilter = ItemFilter(this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun getItemCount() = filteredData.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(filteredData[position])
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    class ItemFilter(val adapter: MovieListAdapter): Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            val cons = constraint.toString().toLowerCase()
            val nlist = adapter.items.filter {
                it.title.toLowerCase().contains(cons)
            }
            results.values = nlist
            results.count = nlist.count()
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            adapter.filteredData = results!!.values as List<MovieModel>
            adapter.notifyDataSetChanged()
        }
    }
}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.movie_item, parent, false)) {
    private val titleView: TextView = itemView.findViewById(R.id.movie_item_title)
    private val yearView: TextView = itemView.findViewById(R.id.movie_item_year)
    private val imdbIdView: TextView = itemView.findViewById(R.id.movie_item_imdb_id)
    private val posterView: ImageView = itemView.findViewById(R.id.movie_item_poster)

    fun assetExists(context: Context, name: String): Boolean {
        var inputStream: InputStream? = null
        try {
            inputStream = context.assets.open(name)
            return true
        } catch (e: IOException) {
            return false
        } finally {
            inputStream?.close()
        }
    }

    fun bind(movie: MovieModel) {
        titleView.text = movie.title
        yearView.text = "Year: ${movie.year}"
        if (movie.imdbId != null) {
            imdbIdView.text = "IMDB: ${movie.imdbId}"
        } else {
            imdbIdView.visibility = View.GONE
        }

        var poster: Drawable? = null
        if (movie.poster != null) {
            poster = assetDrawable(posterView.context, "posters/" + movie.poster)
        }

        if (poster != null) {
            posterView.setImageDrawable(poster)
        } else {
            posterView.setImageResource(R.drawable.ic_baseline_movie_24)
        }

        itemView.setOnClickListener {
            val m = posterView.context as MainActivity

            if (movie.imdbId == null) {
                Toast.makeText(m, "Item has no description!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val navController = m.findNavController(R.id.nav_host_fragment)

            if (assetExists(m, "details/${movie.imdbId}.json")) {
                navController.navigate(
                    R.id.action_navigation_movies_to_navigation_movie_details, bundleOf(
                        Pair("movie_id", movie.imdbId)
                    )
                )
            } else {
                Toast.makeText(m, "Item has no description file!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
