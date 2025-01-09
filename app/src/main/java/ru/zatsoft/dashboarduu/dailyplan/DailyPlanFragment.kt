package ru.zatsoft.dashboarduu.dailyplan

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import ru.zatsoft.dashboarduu.MyItemDecoration
import ru.zatsoft.dashboarduu.R
import ru.zatsoft.dashboarduu.databinding.FragmentDailyPlanBinding


class DailyPlanFragment : Fragment() {
    private  var _binding : FragmentDailyPlanBinding? = null
    private val binding get() = _binding!!
    private var dailyList: MutableList<DailyItem> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
         _binding = FragmentDailyPlanBinding.inflate(inflater, container, false)

          var cas: String
          for(i in 0..24) {
              cas = if(i< 10) "0".plus(i.toString())
                                        else i.toString()
              dailyList.add(DailyItem(cas.plus(":00"),"",false))
          }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DailyAdapter(dailyList)
        binding.recycleView .adapter = adapter
        binding.recycleView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.recycleView.addItemDecoration(MyItemDecoration(requireContext(), R.drawable.divider))
        binding.recycleView.setHasFixedSize(true)

        adapter.setOnDailyClickListener(
            object : DailyAdapter.OnDailyClickListener {
                override fun onDailyClick(item: DailyItem, position: Int) {
                    val bundle = Bundle()
                    bundle.putParcelable("daily", item)
                    val fragmentEdit = AddDailyFragment()
                    fragmentEdit.arguments = bundle
                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(null)
                        ?.replace(R.id.main, fragmentEdit)?.commit()

                    setFragmentResultListener("result"){
                            key,backBundle ->
                        val res = backBundle.getString("answer")
                         res?.let{item.task = it}
                    }
                }
            }
        )
    }
}