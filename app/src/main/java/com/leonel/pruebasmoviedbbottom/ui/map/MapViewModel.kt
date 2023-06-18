package com.leonel.pruebasmoviedbbottom.ui.map

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.leonel.pruebasmoviedbbottom.model.locationuser
import com.leonel.pruebasmoviedbbottom.repository.firestoreRepository
import com.leonel.pruebasmoviedbbottom.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(private val repofirestore: firestoreRepository): ViewModel() {

    val ubicaciones = MutableLiveData<List<locationuser>?>()

    fun getUbicaciones(){
        repofirestore.getDatosUbicacion().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                ubicaciones.value = null
                return@EventListener
            }

            var ubicacionesList : MutableList<locationuser> = mutableListOf()
            for (doc in value!!) {
                var addressItem = doc.toObject(locationuser::class.java)
                ubicacionesList.add(addressItem)
            }
            ubicaciones.value = ubicacionesList
        })

    }

}