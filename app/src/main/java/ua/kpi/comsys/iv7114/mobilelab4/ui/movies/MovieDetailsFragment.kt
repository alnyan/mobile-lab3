package ua.kpi.comsys.iv7114.mobilelab4.ui.movies

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import org.w3c.dom.Text
import ua.kpi.comsys.iv7114.mobilelab4.R
import ua.kpi.comsys.iv7114.mobilelab4.assetDrawable
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

private const val ARG_MOVIE_ID = "movie_id"

class MovieDetailsFragment : Fragment() {
    private var movie_id: String? = null
    private var movie: MovieDetailsModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie_id = it.getString(ARG_MOVIE_ID)
        }
    }

    fun loadMovie() {
        var stream: InputStream? = null
        var reader: InputStreamReader? = null
        try {
            stream = context?.assets?.open("details/$movie_id.json")
            reader = InputStreamReader(stream)
            val gson = Gson()
            // !! to throw in case gson returns null
            movie = gson.fromJson(reader, MovieDetailsModel::class.java)!!
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            reader?.close()
            stream?.close()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadMovie()
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        val title: TextView = view.findViewById(R.id.movie_detail_title)
        val posterView: ImageView = view.findViewById(R.id.movie_detail_poster)
        val releaseDate: TextView = view.findViewById(R.id.movie_detail_released)
        val esrb: TextView = view.findViewById(R.id.movie_detail_esrb)
        val genre: TextView = view.findViewById(R.id.movie_detail_genre)
        val runtime: TextView = view.findViewById(R.id.movie_detail_runtime)
        val writtenBy: TextView = view.findViewById(R.id.movie_detail_written)
        val directedBy: TextView = view.findViewById(R.id.movie_detail_directed)
        val producedBy: TextView = view.findViewById(R.id.movie_detail_produced)
        val starring: TextView = view.findViewById(R.id.movie_detail_actors)
        val plot: TextView = view.findViewById(R.id.movie_detail_plot)

        if (movie != null) {
            val m = movie!!
            var poster: Drawable? = null

            title.text = m.title
            if (m.poster != null) {
                poster = assetDrawable(posterView.context, "posters/" + m.poster)
            }

            if (poster != null) {
                posterView.setImageDrawable(poster)
            } else {
                posterView.setImageResource(R.drawable.ic_baseline_movie_24)
            }

            releaseDate.text = "Released: ${m.released} (${m.year})"
            esrb.text = "ESRB: ${m.rated}"
            genre.text = "Genre: ${m.genre}"
            runtime.text = "Runtime: ${m.runtime}"
            writtenBy.text = "Written by: ${m.writer}"
            directedBy.text = "Directed by: ${m.director}"
            producedBy.text = "Produced by: ${m.production}"
            starring.text = "Starring: ${m.actors}"
            plot.text = "Plot:\n${m.plot}"
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(movie_id: String) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MOVIE_ID, movie_id)
                }
            }
    }
}