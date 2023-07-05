package com.tiara.appmovie.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tiara.appmovie.data.model.TvShowResponse
import com.tiara.appmovie.databinding.FragmentTvshowBinding
import com.tiara.appmovie.ui.detail.DetailActivity
import com.tiara.appmovie.utils.showToast



class TvShowFragment : Fragment() {

    private var _binding: FragmentTvshowBinding? = null
    private lateinit var binding: FragmentTvshowBinding

    private lateinit var adapter: TvAdapter

    private lateinit var viewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        if (_binding == null){
            _binding = FragmentTvshowBinding.inflate(inflater, container, false)
            binding = _binding as FragmentTvshowBinding
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[TvViewModel::class.java]
        adapter = TvAdapter().apply {
            onClick { data ->
                Intent(activity, DetailActivity::class.java).also { intent ->
                    intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.data[1])
                    intent.putExtra(DetailActivity.EXTRA_DATA, data)
                    startActivity(intent)
                }
            }
        }

        getTvShow()
    }

    private fun getTvShow() {

        showLoading(true)
        viewModel.setTvShow()
        viewModel.getMovies().observe(viewLifecycleOwner){
            if (it != null){
                adapter.tvshow = it as MutableList<TvShowResponse>
                binding.apply {
                    rvTvshow.setHasFixedSize(true)
                    rvTvshow.adapter = adapter
                    showLoading(false)
                }
            }else{
                showLoading(false)
                activity?.showToast("Data Failed to load or Data is Empty")
            }
        }

    }

    private fun showLoading(state: Boolean){
        binding.apply {
            if (state){
                progressBar.visibility = View.VISIBLE
                rvTvshow.visibility = View.GONE
            }else{
                progressBar.visibility = View.GONE
                rvTvshow.visibility = View.VISIBLE
            }
        }
    }
}