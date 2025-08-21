package com.example.codialapp.fragments.Guruhlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.codialapp.R
import com.example.codialapp.adapters.MyViewPagerAdapter
import com.example.codialapp.adapters.MyData
import com.example.codialapp.databinding.FragmentAddGroupBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Kurslar
import com.google.android.material.tabs.TabLayoutMediator


@Suppress("UNREACHABLE_CODE")
class AddGroupFragment : Fragment() {
    private val binding by lazy { FragmentAddGroupBinding.inflate(layoutInflater) }
    lateinit var myViewPagerAdapter: MyViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val p = arguments?.getSerializable("ali") as Kurslar
        myViewPagerAdapter = MyViewPagerAdapter(childFragmentManager)
        binding.pager.adapter = myViewPagerAdapter
        binding.tablayout.setupWithViewPager(binding.pager)
        val myDb = MyDb(requireContext())
        binding.name.text = p.name
        binding.pager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position==0) binding.add.visibility = View.INVISIBLE else binding.add.visibility = View.VISIBLE
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.guruhQoshishFragment, bundleOf("vali" to p))

        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

}