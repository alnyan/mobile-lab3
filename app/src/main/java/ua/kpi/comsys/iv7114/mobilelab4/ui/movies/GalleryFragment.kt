package ua.kpi.comsys.iv7114.mobilelab4.ui.movies

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import ua.kpi.comsys.iv7114.mobilelab4.R

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var adapter: GalleryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        galleryViewModel = activity.run {
            ViewModelProvider(this!!).get(GalleryViewModel::class.java)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.list)
        // Set the adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        galleryViewModel.items.observe(viewLifecycleOwner, Observer {
            adapter = GalleryListAdapter(it.toList())
            recyclerView.adapter = adapter
        })
        val button: FloatingActionButton = view.findViewById(R.id.gallery_add_item)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 1)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            galleryViewModel.addItem(GalleryItem(data?.data!!))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}