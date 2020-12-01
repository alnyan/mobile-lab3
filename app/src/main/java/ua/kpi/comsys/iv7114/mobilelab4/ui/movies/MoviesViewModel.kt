package ua.kpi.comsys.iv7114.mobilelab4.ui.movies

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

class MoviesViewModel(app: Application) : AndroidViewModel(app) {
    val movies = MutableLiveData<MutableList<MovieModel>>()
    init {
        movies.value = deserializeMovies(app, "movie_list.json")
    }

    fun addItem(m: MovieModel) {
        movies.value!!.add(m)
        movies.value = movies.value
    }

    fun removeItem(index: Int) {
        movies.value!!.removeAt(index)
        movies.value = movies.value
    }
}

private fun deserializeMovies(ctx: Context, filename: String): MutableList<MovieModel> {
    var stream: InputStream? = null
    var reader: InputStreamReader? = null
    try {
        stream = ctx.assets.open(filename)
        reader = InputStreamReader(stream)
        val gson = Gson()
        val wrap: MovieSearchWrapper = gson.fromJson(reader, MovieSearchWrapper::class.java)
        return wrap.search.map {
            // Sanitize fields
            MovieModel(it.title,
                       it.year,
                       it.type,
                       if (it.poster.isNullOrEmpty()) { null } else { it.poster },
                       if (it.imdbId == null || it.imdbId == "noid") { null } else { it.imdbId })
        }.toMutableList()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        reader?.close()
        stream?.close()
    }

    return arrayListOf()
}

class MovieSearchWrapper {
    val search: List<MovieModel> = emptyList()
}