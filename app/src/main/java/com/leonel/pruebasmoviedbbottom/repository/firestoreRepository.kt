package com.leonel.pruebasmoviedbbottom.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.leonel.pruebasmoviedbbottom.model.add
import com.leonel.pruebasmoviedbbottom.model.locationuser
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class firestoreRepository @Inject constructor(){
    private val db = FirebaseFirestore.getInstance()

    fun getDatosUbicacion(): CollectionReference {
        var collectionReference = db.collection("locationsUser")
        return collectionReference
    }

}