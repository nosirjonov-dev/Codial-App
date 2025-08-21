package com.example.codialapp.fragments.Guruhlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.R
import com.example.codialapp.adapters.GrupalarAdapter
import com.example.codialapp.adapters.MyData
import com.example.codialapp.databinding.FragmentOchilganGuruhBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar


class OchilganGuruhFragment : Fragment() ,GrupalarAdapter.RvAction5{
    lateinit var grupalarAdapter: GrupalarAdapter
    lateinit var myDb: MyDb
    lateinit var list:ArrayList<Grupalar>
    private val binding by lazy { FragmentOchilganGuruhBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val myLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        binding.rvOchilgan.apply {
            layoutManager = myLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), myLayoutManager.orientation))
        }

        return binding.root
    }
    override fun viewClick(grupalar: Grupalar, position: Int) {
        findNavController().navigate(R.id.studentFragment, bundleOf("raqam" to 100, "key10" to grupalar.id))
    }

    override fun editClick(grupalar: Grupalar, position: Int) {
        findNavController().navigate(R.id.editGroupFragment, bundleOf("edit" to grupalar.id))
    }

    override fun deleteClick(grupalar: Grupalar, position: Int) {
        myDb = MyDb(requireContext())
        var talabalar: Talabalar? = null
        val listStudents = ArrayList<Talabalar>()
        myDb.showStudents().forEach {
            if (it.groupId?.id == grupalar.id) {
                listStudents.add(it)
                talabalar = it
            }
        }
        if (listStudents.size != 0) {
            myDb.deleteStudent(talabalar!!)
            myDb.deleteGroup(grupalar)
            grupalarAdapter.notifyItemChanged(position)
        } else {
            Toast.makeText(context, "Guruh talabalar ro'yxati bo'sh!", Toast.LENGTH_SHORT)
                .show()
        }
        onResume()
    }

    override fun onResume() {
        super.onResume()
        myDb = MyDb(requireContext())
        list = ArrayList()
        for (grupalar in myDb.showGroup()) {
            if (grupalar.mentor_id?.kurs_id?.id== MyData.kurslar?.id && grupalar.ochilganmi==1){
                list.add(grupalar)
            }
        }
        grupalarAdapter = GrupalarAdapter(this@OchilganGuruhFragment,list,myDb)

        binding.rvOchilgan.adapter = grupalarAdapter
    }


}