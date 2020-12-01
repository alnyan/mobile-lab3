package ua.kpi.comsys.iv7114.mobilelab4.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ua.kpi.comsys.iv7114.mobilelab4.R

class AddMovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_movie, container, false)
        val confirmButton: Button = view.findViewById(R.id.movie_add_confirm)
        val imdb: EditText = view.findViewById(R.id.movie_add_imdb)
        val title: EditText = view.findViewById(R.id.movie_add_title)
        val year: EditText = view.findViewById(R.id.movie_add_year)
        confirmButton.setOnClickListener {
            val model = activity?.run {
                ViewModelProvider(this).get(MoviesViewModel::class.java)
            } ?: throw Exception("Invalid Activity")

            // Validate values
            if (title.text.isNullOrEmpty() || year.text.isNullOrEmpty() || imdb.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }
            try {
                val y = year.text.toString().toInt()
                if (y < 1800 || y > 3000) {
                    throw Exception()
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            model.addItem(MovieModel(title.text.toString(), year.text.toString(), "movie", "0.jpg", imdb.text.toString()))
            findNavController().popBackStack()
        }
        return view
    }
}