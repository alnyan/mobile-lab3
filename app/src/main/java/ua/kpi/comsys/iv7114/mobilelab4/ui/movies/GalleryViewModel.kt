package ua.kpi.comsys.iv7114.mobilelab4.ui.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class GalleryViewModel(app: Application) : AndroidViewModel(app) {
    val items = MutableLiveData<MutableList<GalleryItem>>(mutableListOf())
    fun addItem(m: GalleryItem) {
        items.value!!.add(m)
        items.value = items.value
    }
}