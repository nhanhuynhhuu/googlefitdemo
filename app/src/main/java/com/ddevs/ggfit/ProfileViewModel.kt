package com.ddevs.ggfit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.*
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.DataUpdateRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.TimeUnit


class ProfileViewModel() : ViewModel() {
    private  var _name: MutableLiveData<String> = MutableLiveData()
    private  var _weight: MutableLiveData<String> = MutableLiveData()
    private  var _height: MutableLiveData<String> = MutableLiveData()
    private  var _dp:MutableLiveData<Uri> = MutableLiveData()
    var name:LiveData<String> =_name
    var weight:LiveData<String> =_weight
    var height:LiveData<String> =_height
    var dp:LiveData<Uri> =_dp
    var onSignOut:MutableLiveData<Boolean> = MutableLiveData(false)
    lateinit var historyClient: HistoryClient

    fun fetchUserData(){
        val user:FirebaseUser?=FirebaseAuth.getInstance().currentUser
        _name.value=user?.displayName
        _dp.value= user?.photoUrl
    }
    fun logOut(){
        FirebaseAuth.getInstance().signOut()
        onSignOut.value=true

    }
    fun fetchFromGoogleAPI() {

        val dataReadRequest = DataReadRequest.Builder()
                .read(DataType.TYPE_WEIGHT)
                .setTimeRange(1, System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .setLimit(1)
                .build()
        historyClient.readData(dataReadRequest).addOnSuccessListener {
            _weight.value=it.dataSets.firstOrNull()?.dataPoints?.get(0)?.getValue(Field.FIELD_WEIGHT)?.toString()+"Kg"
        }
        historyClient.readData(dataReadRequest).addOnFailureListener {
            Log.w("ERROR","There was an error reading data from Google Fit", it)
        }

        val heightDataReadRequest = DataReadRequest.Builder()
                .read(DataType.TYPE_HEIGHT)
                .setTimeRange(1, System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .setLimit(1)
                .build()
        historyClient.readData(heightDataReadRequest).addOnSuccessListener {
            val height= it.dataSets.firstOrNull()?.dataPoints?.get(0)?.getValue(Field.FIELD_HEIGHT)?.asFloat()
            if (height != null) {
                _height.value=(height*100).toString()+"cm"
            }
            else{
                _height.value="-"
            }
        }
        historyClient.readData(dataReadRequest).addOnFailureListener {
            Log.w("ERROR","There was an error reading data from Google Fit", it)
        }

    }
}