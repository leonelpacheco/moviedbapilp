package com.leonel.pruebasmoviedbbottom.ui.images

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.leonel.pruebasmoviedbbottom.R
import com.leonel.pruebasmoviedbbottom.databinding.FragmentImageBinding
import com.leonel.pruebasmoviedbbottom.utils.TAG
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ImagesFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null
    private val CAMERA_PERMISSION_CODE  = 1000
    private val IMAGE_CAPTURE_CODE = 1001


    var vFilename: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imagestoreViewModel =
            ViewModelProvider(this).get(ImagesViewModel::class.java)

        _binding = FragmentImageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imgsubir.setImageResource(R.drawable.ic_picture_icon)
        binding.imgsubir.setTag("default")
        binding.imgsubir.setOnClickListener(View.OnClickListener {
            cargarImg()
        })
        binding.fbtnGalery.setOnClickListener(View.OnClickListener {
            cargarImg()
        })
        binding.fbtnCamera.setOnClickListener(View.OnClickListener {
            // Request permission
            val permissionGranted = requestCameraPermission()
            if (permissionGranted) {
                // Open the camera interface
                openCameraInterface()
            }
        })

        binding.fbtnSubirimg.setOnClickListener(View.OnClickListener {
            if(imageUri!=null || binding.imgsubir.tag !="default") {
                imagestoreViewModel.OnCreate(imageUri)
            }
            else
                Toast.makeText(activity, "Seleccione una imagen", Toast.LENGTH_SHORT).show()
        })

        imagestoreViewModel.isLoading.observe(viewLifecycleOwner){
            binding.loadingimg.isVisible = it
            if (it == false) {
                binding.imgsubir.setImageResource(R.drawable.ic_picture_icon)
                Toast.makeText(activity, "Imagen subida con exito", Toast.LENGTH_SHORT).show()
                binding.imgsubir.setTag("default")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun cargarImg(){
        val pickImage = 100
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        gallery.setType("image/*")
        startActivityForResult(gallery, pickImage)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 100) {
            imageUri = data?.data
            binding.imgsubir.setImageURI(imageUri)
            binding.imgsubir.setTag("send")
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            binding.imgsubir.setImageURI(imageUri)
            binding.imgsubir.setTag("send")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode === CAMERA_PERMISSION_CODE) {
            if (grantResults.size === 1 && grantResults[0] ==    PackageManager.PERMISSION_GRANTED){
                // Permission was granted
                openCameraInterface()
            }
            else{
                // Permission was denied
                showAlert("Camera permission was denied. Unable to take a picture.")
            }
        }
    }

    private fun requestCameraPermission(): Boolean {
        var permissionGranted = false
// If system os is Marshmallow or Above, we need to request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val cameraPermissionNotGranted = checkSelfPermission(activity as Context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            if (cameraPermissionNotGranted){
                val permission = arrayOf(Manifest.permission.CAMERA)
                // Display permission dialog
                requestPermissions(permission, CAMERA_PERMISSION_CODE)
            }
            else{
                // Permission already granted
                permissionGranted = true
            }
        }
        else{
            // Android version earlier than M -&gt; no need to request permission
            permissionGranted = true
        }
        return permissionGranted
    }
    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(activity as Context)
        builder.setMessage(message)
        builder.setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }


    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "imagem")
        values.put(MediaStore.Images.Media.DESCRIPTION, "R.string.take_picture_description")
        imageUri = activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
// Create camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
// Launch intent
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }


}