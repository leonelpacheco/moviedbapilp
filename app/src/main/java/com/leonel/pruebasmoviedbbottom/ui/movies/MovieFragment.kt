package com.leonel.pruebasmoviedbbottom.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leonel.pruebasmoviedbbottom.R
import com.leonel.pruebasmoviedbbottom.adaptes.MovieAdapter
import com.leonel.pruebasmoviedbbottom.databinding.FragmentMovieBinding
import com.leonel.pruebasmoviedbbottom.utils.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val movieViewModel =
            ViewModelProvider(this).get(MovieViewModel::class.java)

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root


        connectionLiveData = activity?.let { ConnectionLiveData(it) }!!
        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvailable ->
            isNetworkAvailable?.let {
                if(it) {
                    movieViewModel.onCreate()
                }
            }
        }
        movieViewModel.isLoading.observe(viewLifecycleOwner){
            binding.loading.isVisible = it
        }
        binding.includePopulares.txtCategoryMovie.text= getString(R.string.nav_header_titlepopulares)
        binding.includeCalificadas.txtCategoryMovie.text= getString(R.string.nav_header_titlemascalificadas)
        binding.includeRecomendadas.txtCategoryMovie.text= getString(R.string.nav_header_titlerecomendaciones)

        binding.includePopulares.rwlistmovies.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.includeCalificadas.rwlistmovies.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.includeRecomendadas.rwlistmovies.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        movieViewModel.listmovieModel.observe(viewLifecycleOwner){
            val adapter = MovieAdapter(it)
            binding.includePopulares.rwlistmovies.adapter = adapter
            movieViewModel.getMoviesRate()
        }

        movieViewModel.listmovieRateModel.observe(viewLifecycleOwner){
            val adapter = MovieAdapter(it)
            binding.includeCalificadas.rwlistmovies.adapter = adapter
            movieViewModel.getMoviesTrending()

        }

        movieViewModel.listmovieTrendingModel.observe(viewLifecycleOwner){
            val adapter = MovieAdapter(it)
            binding.includeRecomendadas.rwlistmovies.adapter = adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}