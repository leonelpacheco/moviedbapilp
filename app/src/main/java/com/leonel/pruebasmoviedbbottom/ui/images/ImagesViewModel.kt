package com.leonel.pruebasmoviedbbottom.ui.images

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class ImagesViewModel : ViewModel() {
    val storage = FirebaseStorage.getInstance().getReference()
    var storageRef = storage.storage.reference
    val isLoading = MutableLiveData<Boolean>()


    fun OnCreate(imageUri: Uri?)
    {
        viewModelScope.launch {
            isLoading.postValue(true)
            subircloud(imageUri)
            isLoading.postValue(false)
        }
    }

     fun subircloud(imageUri: Uri?){
        if(imageUri!=null) {
            val uTask = storageRef.child("images").child(imageUri!!.lastPathSegment!!)
                .putFile(imageUri).addOnCompleteListener {
                    if (it.isSuccessful && it.result != null)
                        Log.d("img subida","subido")
                    else
                        Log.d("img no subida","subido")
                }
        }
    }
}