package com.gy25m.bookowner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.DialogAddBookBinding
import com.gy25m.bookowner.databinding.FragmentHomeBinding
import com.gy25m.bookowner.databinding.FragmentMybookBinding
import com.gy25m.bookowner.model.MyBookItem

class MyBookFragment : Fragment() {

    private lateinit var binding: FragmentMybookBinding
    var list: MutableList<MyBookItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMybookBinding.inflate(inflater,container,false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnInfo.setOnClickListener{
            AlertDialog.Builder(requireContext()).setView(layoutInflater.inflate(R.layout.dialog_info,null)).show()
        }
        binding.addBook.setOnClickListener{
            AlertDialog.Builder(requireContext()).setView(layoutInflater.inflate(R.layout.dialog_add_book,null)).show()
        }

        var dialogBinding=DialogAddBookBinding.inflate(layoutInflater)
        dialogBinding.btnConfirm.setOnClickListener {
            //확인버튼
            list.add(MyBookItem(dialogBinding.etTitle.toString(),dialogBinding.etReview.toString()))
        }
//        binding.recyclerMybook.adapter(MyBookAdapter(context,list))




    }
}