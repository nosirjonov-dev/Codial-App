package com.example.codialapp.fragments.Guruhlar.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.R
import com.example.codialapp.adapters.StudentAdapter
import com.example.codialapp.adapters.MyData
import com.example.codialapp.databinding.FragmentStudentBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Talabalar



class StudentFragment : Fragment(),StudentAdapter.RvAction {
    private val binding by lazy { FragmentStudentBinding.inflate(layoutInflater) }
    lateinit var myDb: MyDb
    lateinit var studentAdapter: StudentAdapter
    lateinit var list:ArrayList<Talabalar>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raqam = arguments?.getInt("raqam")

        val grupa_id = arguments?.getInt("key10")
        myDb = MyDb(requireContext())


        var name = ""
        var vaqt = ""
        myDb.showGroup().forEach {
            if (it.id == grupa_id) {
                vaqt = it.time.toString()
                name = it.name.toString()
                MyData.grupalar = it
            }
        }
        binding.nameGroup.text = name
        binding.vaqti.text = vaqt
        val myLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rv.apply {
            layoutManager = myLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    myLayoutManager.orientation
                )
            )
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.addStudentFragment)
        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        if (raqam==100){
            binding.btnBoshlash.visibility = View.GONE
        }else {

            binding.btnBoshlash.setOnClickListener {
                val p = MyData.grupalar
                val grupalar = Grupalar(
                    p?.id,
                    p?.name,
                    p?.mentor_id,
                    p?.day,
                    p?.time,
                    1
                )
                myDb.editGroup(grupalar)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        return binding.root
    }

    override fun editClick(talabalar: Talabalar) {
        findNavController().navigate(R.id.editStudentFragment)
        MyData.talabalar = talabalar
    }

    override fun deleteClick(talabalar: Talabalar) {
        myDb.deleteStudent(talabalar)
        onResume()
    }

    override fun onResume() {
        super.onResume()
        val grupa_id = arguments?.getInt("key10")
        myDb = MyDb(requireContext())
        list = ArrayList()

        for (student in myDb.showStudents()) {
            if (student.groupId?.id == grupa_id) {
                list.add(student)
            }
        }
        binding.count.text = list.size.toString()
        studentAdapter = StudentAdapter(this@StudentFragment, list)
        binding.rv.adapter = studentAdapter
    }

}