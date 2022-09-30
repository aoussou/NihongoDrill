package com.talisol.kankenkakitori.viewModels

import android.app.Application
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.talisol.kankenkakitori.actions.KanjiRecAction
import com.talisol.kankenkakitori.drawingUtils.writeBitmap
import com.talisol.kankenkakitori.ml.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.TransformToGrayscaleOp
import java.io.File

class KanjiRecognitionVM(application: Application) : AndroidViewModel(application) {

    private val context = application.baseContext
    private val numberGuess = 5

    private val imageProcessor = ImageProcessor.Builder()
        .add(
            ResizeOp(
                224, 224, ResizeOp.ResizeMethod.BILINEAR
            )
        )
        .add(TransformToGrayscaleOp())
        .add(CastOp(DataType.FLOAT32))
        .build()

    @RequiresApi(Build.VERSION_CODES.P)
    fun onAction(action: KanjiRecAction) {
        when (action) {
            is KanjiRecAction.RecognizeKanji -> predictKanji(action.bitmap)
            is KanjiRecAction.ResetPredictedKanji -> resetPredictedKanji()
            is KanjiRecAction.SaveImage -> saveImage(action.bitmap,action.kanji)
            is KanjiRecAction.SetOtherGuessesList -> setOtherGuessesList()
            is KanjiRecAction.SetPredictedKanji -> setPredictedKanji(action.kanji)
        }
    }

    private val _predictedKanji: MutableStateFlow<String?> = MutableStateFlow(null)
    val predictedKanji = _predictedKanji.asStateFlow()

    private val _otherGuessesList: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val otherGuessesList = _otherGuessesList.asStateFlow()

    private val _allProbabilities: MutableStateFlow<FloatArray?> = MutableStateFlow(null)

    private val jsonString =
        application.assets.open("preds_dict.json").bufferedReader().use { it.readText() }

    private val map = Json.parseToJsonElement(jsonString).jsonObject.toMutableMap()


    private val model = Model.newInstance(application.baseContext)

    @RequiresApi(Build.VERSION_CODES.P)
    private fun predictKanji(imgBitmap: Bitmap) {
        val model = Model.newInstance(context)
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(imgBitmap))
        val buffer = tensorImage.tensorBuffer

        val prediction = model.process(buffer)
        model.close()
        val outputBuffer = prediction.outputFeature0AsTensorBuffer
        val finalOutput = outputBuffer.floatArray
        _allProbabilities.value = finalOutput
        val predictedNumber = finalOutput.indexOfFirst { it == finalOutput.max() }
        val rawString = map[predictedNumber.toString()].toString()
        val processedString = rawString.replace(""""""", "")

        Log.i("TEST", processedString)

        _predictedKanji.value = processedString

    }

    private fun resetPredictedKanji() {
        _predictedKanji.value = null
        _otherGuessesList.value = null
        _allProbabilities.value = null
    }



    private fun setOtherGuessesList() {

        if (_allProbabilities.value != null) {

            val kanjiList = mutableListOf<String>()
            val resultArray = _allProbabilities.value!!
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    for (i in 1..numberGuess) {
                        val predictedNumber = resultArray.indexOfFirst { it == resultArray.max() }

                        val rawString = map[predictedNumber.toString()].toString()
                        val processedString = rawString.replace(""""""", "")
                        resultArray[predictedNumber] = 0F
                        kanjiList.add(processedString)
                    }
                }

                _otherGuessesList.value = kanjiList
            }
        }

    }

    private fun setPredictedKanji(kanji: String) {
        _predictedKanji.value = kanji
    }

    private fun saveImage(bitmap: Bitmap,kanji: String) {

        val randomId = java.util.UUID.randomUUID().toString()
        bitmap.let {
            File(context.filesDir, "screenshots/${kanji}_$randomId.png")
                .writeBitmap(bitmap, Bitmap.CompressFormat.PNG, 85)
        }

    }


}