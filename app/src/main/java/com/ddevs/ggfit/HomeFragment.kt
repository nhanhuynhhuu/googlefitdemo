package com.ddevs.ggfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ddevs.ggfit.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness

class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding:FragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        val viewModel:HomeViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.historyClient= Fitness.getHistoryClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(context)!!)
        viewModel.fetchStepsAndCalories()
        binding.viewModel=viewModel
        viewModel.steps.observe(viewLifecycleOwner) {
            binding.stepCount.setText(viewModel.steps.value)
        }
        viewModel.calories.observe(viewLifecycleOwner) {
            binding.caloriesCount.setText(viewModel.calories.value)
        }
        return binding.root
    }

}