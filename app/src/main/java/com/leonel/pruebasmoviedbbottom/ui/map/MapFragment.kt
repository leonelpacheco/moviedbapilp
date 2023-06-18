package com.leonel.pruebasmoviedbbottom.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.leonel.pruebasmoviedbbottom.R
import com.leonel.pruebasmoviedbbottom.databinding.FragmentMapBinding
import com.leonel.pruebasmoviedbbottom.model.locationuser
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap
    var  lastlocation: LatLng = LatLng(0.0,0.0)

    var mapReady = false
    private lateinit var usuariospintar: List<locationuser>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapuser) as SupportMapFragment
        mapFragment.getMapAsync {
                gooogleMap -> mMap = gooogleMap
            mapReady = true

        }

        mapViewModel.getUbicaciones()

        mapViewModel.ubicaciones.observe(viewLifecycleOwner){
            if (it != null) {
                this.usuariospintar = it
                updatemap()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun updatemap()
    {
        if(mapReady && usuariospintar != null)
        {
            for(loc in usuariospintar){
                val ubicacion = LatLng(loc.latitude!!.toDouble(), loc.longitude!!.toDouble())
                lastlocation = ubicacion
                mMap.addMarker(
                    MarkerOptions().position(ubicacion).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE
                        )
                    ).title("${loc.date}")
                )
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lastlocation))
        }
    }
}