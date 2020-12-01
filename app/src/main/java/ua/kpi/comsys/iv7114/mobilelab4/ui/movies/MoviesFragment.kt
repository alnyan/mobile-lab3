package ua.kpi.comsys.iv7114.mobilelab4.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ua.kpi.comsys.iv7114.mobilelab4.R

class MoviesFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        moviesViewModel = activity.run {
            ViewModelProvider(this!!).get(MoviesViewModel::class.java)
        }
        val root = inflater.inflate(R.layout.fragment_movies, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.movie_recycler)
        val searchView: EditText = root.findViewById(R.id.movie_search_bar)
        val fab: FloatingActionButton = root.findViewById(R.id.movie_fab)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        moviesViewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter = MovieListAdapter(it)
            recyclerView.adapter = adapter
            searchView.text.clear()
            adapter.filter.filter("")
        })
        searchView.addTextChangedListener {
            adapter.filter.filter(it)
        }
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_movies_to_navigation_movie_add)
        }
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                moviesViewModel.removeItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return root
    }
}