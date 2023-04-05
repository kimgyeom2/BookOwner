package com.gy25m.bookowner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.activites.MainActivity
import com.gy25m.bookowner.adapters.BestAdapter
import com.gy25m.bookowner.databinding.FragmentBestBinding
import com.gy25m.bookowner.model.AladinApiResponce
import com.gy25m.bookowner.model.AladinBookList
import com.gy25m.bookowner.network.RetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class BestFragment : Fragment() {
    lateinit var binding:FragmentBestBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://www.aladin.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService=retrofit.create(RetrofitApiService::class.java)
        val call: Call<AladinBookList> = retrofitService.booklist("Bestseller","ttbrlarua72021535002")
    
        call.enqueue(object : Callback<AladinBookList>{
            override fun onResponse(
                call: Call<AladinBookList>,
                response: Response<AladinBookList>
            ) {
               val ala=response.body()
                binding.recyclerBest.adapter=BestAdapter(activity as MainActivity,ala!!.item)
            }

            override fun onFailure(call: Call<AladinBookList>, t: Throwable) {
                Toast.makeText(requireActivity(), "실패", Toast.LENGTH_SHORT).show()
            }

        })
    
    }
}