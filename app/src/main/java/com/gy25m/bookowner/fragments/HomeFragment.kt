package com.gy25m.bookowner.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.gy25m.bookowner.R
import com.gy25m.bookowner.activites.MainActivity
import com.gy25m.bookowner.activites.SearchActivity
import com.gy25m.bookowner.databinding.FragmentFavorBinding
import com.gy25m.bookowner.databinding.FragmentHomeBinding
import com.gy25m.bookowner.model.RankItem

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var main=requireActivity() as MainActivity
        main.supportFragmentManager.beginTransaction().replace(binding.frame.id,BestFragment()).commit()
        binding.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            search()
            false
        }
        binding.ivSearch.setOnClickListener {search()}

        binding.btnBest.isSelected=true
        binding.btnBest.setOnClickListener {
            it.isSelected=true
            binding.btnNew.isSelected=false
            main.supportFragmentManager.beginTransaction().replace(binding.frame.id,BestFragment()).commit()
        }
        binding.btnNew.setOnClickListener {
            it.isSelected=true
            binding.btnBest.isSelected=false
            main.supportFragmentManager.beginTransaction().replace(binding.frame.id,NewFragment()).commit()
        }
        main.supportFragmentManager.beginTransaction().replace(binding.frameReco.id,RecommandFragment()).commit()

        var fire=FirebaseFirestore.getInstance()
        var ui=fire.collection("userInfo")
        var a=ui.orderBy("lv",Query.Direction.DESCENDING).limit(3)
        var rank= mutableListOf<RankItem>()
        a.get().addOnSuccessListener {
            for (snapshot in it){
                var aa: MutableMap<String, Any> = snapshot.data
                var id= aa.get("id").toString()
                var lv= aa.get("lv").toString()
                var grade=aa.get("grade").toString()
                rank.add(RankItem(id,lv,grade))
            }
            binding.first.text= "1.  ${rank[0].id}   lv.${rank[0].lv}   ${rank[0].grade}"
            binding.second.text="2.  ${rank[1].id}   lv.${rank[1].lv}   ${rank[1].grade}"
            binding.third.text= "3.  ${rank[2].id}   lv.${rank[2].lv}   ${rank[2].grade}"

        }

    }

    fun search(){
        val intent = Intent(activity,SearchActivity::class.java)
        if (binding.etSearch.text.toString()==""){
            Toast.makeText(requireActivity(), "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }
        intent.putExtra("bookName",binding.etSearch.text.toString())
        startActivity(intent)
        binding.etSearch.text.clear()
        binding.etSearch.clearFocus()
    }

}