package ru.zatsoft.dashboarduu.dailyplan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import ru.zatsoft.dashboarduu.databinding.FragmentAddBinding

class AddDailyFragment : Fragment() {
    private var dailyItem: DailyItem? = null
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dailyItem = it.getParcelable ("daily")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.tvTime.text = dailyItem?.hour.toString()
        binding.editItem.setText(dailyItem?.task)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOK.setOnClickListener{
            val newText = binding.editItem.text.toString()
            setFragmentResult("result", bundleOf("answer" to newText))
            fragmentManager?.popBackStack()
        }
        binding.btnCancel.setOnClickListener {fragmentManager?.popBackStack()
        }
    }
}