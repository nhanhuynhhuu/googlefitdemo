package com.ddevs.ggfit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ddevs.ggfit.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness


class ProfileFragment : Fragment() {
    lateinit var profileBinding: FragmentProfileBinding
    lateinit var viewModel:ProfileViewModel
   

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        viewModel= ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.historyClient= Fitness.getHistoryClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(context)!!)
        profileBinding.viewModel=viewModel
        viewModel.fetchUserData()
        viewModel.fetchFromGoogleAPI()
        viewModel.weight.observe(viewLifecycleOwner) {
            profileBinding.textView3.setText(viewModel.weight.value)
        }
        viewModel.height.observe(viewLifecycleOwner) {
            profileBinding.height.setText(viewModel.height.value)
        }
        viewModel.dp.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it).placeholder(R.drawable.ic_baseline_account_circle_24)
                .circleCrop()
                .into(profileBinding.profileImage)
        }
        viewModel.onSignOut.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        return profileBinding.root
    }

}