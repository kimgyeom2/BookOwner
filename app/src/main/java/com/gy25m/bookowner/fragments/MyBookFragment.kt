package com.gy25m.bookowner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.R
import com.gy25m.bookowner.adapters.MyBookAdapter
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
        var dia:AlertDialog
        binding.btnInfo.setOnClickListener{
            AlertDialog.Builder(requireContext()).setView(layoutInflater.inflate(R.layout.dialog_info,null)).show()
        }
        binding.addBook.setOnClickListener{
            var dialogBinding=DialogAddBookBinding.inflate(layoutInflater)
            var dia=AlertDialog.Builder(requireContext()).setView(dialogBinding.root).show()

            dialogBinding.btnConfirm.setOnClickListener {
                var title=dialogBinding.etTitle.text.toString()
                var review=dialogBinding.etReviewreal.text.toString()

                list.add(MyBookItem(title,review))
                var adapter=MyBookAdapter(requireContext(),list)
                binding.recyclerMybook.adapter=adapter
                adapter.notifyItemInserted(0)
                binding.recyclerMybook.scrollToPosition(0)
                dia.dismiss()

            }
        }






    }
}