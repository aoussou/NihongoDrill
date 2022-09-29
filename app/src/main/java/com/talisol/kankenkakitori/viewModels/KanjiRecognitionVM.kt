package com.talisol.kankenkakitori.viewModels

import android.app.Application
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.talisol.kankenkakitori.ml.Model
import com.talisol.kankenkakitori.quizUtils.QuizAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.TransformToGrayscaleOp

class KanjiRecognitionVM(application: Application): AndroidViewModel(application) {

    private val imageProcessor = ImageProcessor.Builder()
        .add(
            ResizeOp(
                224,224, ResizeOp.ResizeMethod.BILINEAR
            )
        )
        .add(TransformToGrayscaleOp())
        .add(CastOp(DataType.FLOAT32))
        .build()

    @RequiresApi(Build.VERSION_CODES.P)
    fun onAction(action: QuizAction) {
        when (action) {
            is QuizAction.RecognizeKanji -> predictKanji(action.bitmap)
            is QuizAction.ResetPredictedKanji -> resetPredictedKanji()
            else -> {}
        }
    }

    private val _predictedKanji: MutableStateFlow<String?> = MutableStateFlow(null)
    val predictedKanji = _predictedKanji.asStateFlow()

    private val jsonString = application.assets.open("preds_dict.json").bufferedReader().use { it.readText() }
    private val map = Json.parseToJsonElement(jsonString).jsonObject.toMutableMap()


    private val model = Model.newInstance(application.baseContext)

    @RequiresApi(Build.VERSION_CODES.P)
    private fun predictKanji(imgBitmap: Bitmap){
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(imgBitmap))
        val buffer = tensorImage.tensorBuffer

        val prediction = model.process(buffer)
        val outputBuffer = prediction.outputFeature0AsTensorBuffer
        val finalOutput = outputBuffer.floatArray
        val predictedNumber = finalOutput.indexOfFirst { it == finalOutput.max() }
        val rawString = map[predictedNumber.toString()].toString()
        val processedString = rawString.replace(""""""", "")
        Log.i("TEST",processedString)

        _predictedKanji.value = processedString
    }

    private fun resetPredictedKanji() {
        _predictedKanji.value = null
    }

}