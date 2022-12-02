package com.ddevs.ggfit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import kotlin.math.roundToInt

class HomeViewModel: ViewModel() {
    private var _steps:MutableLiveData<String> = MutableLiveData<String>()
    private var _calories:MutableLiveData<String> = MutableLiveData<String>()
    var steps: LiveData<String> = _steps
    var calories: LiveData<String> = _calories
    lateinit var historyClient: HistoryClient

    fun fetchStepsAndCalories(){
        historyClient.readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA).addOnSuccessListener { result ->
            val ans = result.dataPoints.firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
            _steps.value=ans.toString()
        }.addOnFailureListener {
            Log.d("Error", it.toString())
        }
        historyClient.readDailyTotal(DataType.TYPE_CALORIES_EXPENDED).addOnCompleteListener { result ->
           val ans= result.result?.dataPoints?.firstOrNull()?.getValue(Field.FIELD_CALORIES)?.asFloat()?.roundToInt()
                   ?: 0
            _calories.value=ans.toString()
            Log.d("Calories",_calories.value.toString())
        }
    }

}