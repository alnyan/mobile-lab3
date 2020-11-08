package ua.kpi.comsys.iv7114.mobilelab3.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.iv7114.mobilelab3.R

class MoviesFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        moviesViewModel =
                ViewModelProvider(this).get(MoviesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_movies, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.movie_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var adapter: MovieListAdapter
        moviesViewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter = MovieListAdapter(it)
            recyclerView.adapter = adapter
        })
        return root
    }
}