package ua.kpi.comsys.iv7114.mobilelab3.ui.movies

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.iv7114.mobilelab3.R
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MovieListAdapter(private val items: List<MovieModel>):
    RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.movie_item, parent, false)) {
    private val titleView: TextView = itemView.findViewById(R.id.movie_item_title)
    private val yearView: TextView = itemView.findViewById(R.id.movie_item_year)
    private val imdbIdView: TextView = itemView.findViewById(R.id.movie_item_imdb_id)
    private val posterView: ImageView = itemView.findViewById(R.id.movie_item_poster)

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
    }
}

fun assetDrawable(ctx: Context, filename: String): Drawable? {
    var stream: InputStream? = null
    return try {
        stream = ctx.assets.open(filename)
        Drawable.createFromStream(stream, null)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } finally {
        stream?.close()
    }
}