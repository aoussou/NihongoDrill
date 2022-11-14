package com.talisol.nihongodrill.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.TrackingAction
import com.talisol.nihongodrill.data.ManagerDataSource
import com.talisol.nihongodrill.quizUtils.Question
import com.talisol.nihongodrill.quizUtils.extractMapFromJson
import com.talisol.nihongodrill.quizUtils.extractStringFromJson
import com.talisol.nihongodrill.quizUtils.getVerbInflection
import com.talisol.nihongodrill.remote.PostsService
import com.talisol.nihongodrill.remote.dto.PostRequest
import com.talisol.nihongodrill.ui.states.QuizState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuizVM @Inject constructor(
    private val managerDataSource: ManagerDataSource,
) : ViewModel() {

    private val _quizState = MutableStateFlow(QuizState())
    val quizState = _quizState.asStateFlow()
    private val service = PostsService.create()
    private val kanjiRecRequired = listOf("kaki", "taigi", "goji", "yoji", "okuri")
    private val mcaType = listOf("shikibetsu", "douon", "taigi")


    private val _qaList = MutableStateFlow(listOf<Question>())

    fun onAction(action: QuizAction) {
        when (action) {
            is QuizAction.LoadQuestionList -> loadQuestion(action.qaList)
            is QuizAction.NextQuestion -> nextQuestion()
            is QuizAction.PreviousQuestion -> previousQuestion()
            is QuizAction.ConfirmAnswer -> confirmAnswer(action.trackingOnAction)
            is QuizAction.ConfirmAnswersList -> confirmAnswersList(action.trackingOnAction)
            is QuizAction.StartQuiz -> startQuiz()
            is QuizAction.InputAnswer -> inputAnswer(action.answerString)
            is QuizAction.EndQuiz -> endQuiz()
            is QuizAction.SelectWrongKanji -> selectWrongKanji(action.kanji, action.kanjiInd)
            is QuizAction.SetQuizType -> setQuizType(action.questionType)
            is QuizAction.UpdateAnswersList -> updateAnswersList(action.answer, action.index)
            is QuizAction.SetSelectedSubQuestion -> setSelectedSubquestionNbr(action.number)
            is QuizAction.SetMCAnbr -> setMCAnumber(action.number)
            is QuizAction.SetExplanation -> getExplanation(action.string)
        }
    }

    private fun setMCAnumber(mcaNumber: Int) {
        _quizState.update { it.copy(mcaNumber = mcaNumber) }
    }

    private fun inputAnswer(answerString: String?) {
        _quizState.update { it.copy(inputAnswer = answerString) }
    }

    private fun setSelectedSubquestionNbr(subQuestionNbr: Int) {
        _quizState.update { it.copy(selectedSubQuestionNbr = subQuestionNbr) }
    }


    private fun updateAnswersList(answer: String, index: Int) {
        Log.i("DEBUG taigi", "in update")
        val selectedAnswersList: MutableList<String?> = _quizState.value.selectedAnswersList!!
        selectedAnswersList[index] = answer
        _quizState.update { it.copy(selectedAnswersList = selectedAnswersList) }
    }

    private fun endQuiz() {
        _quizState.value = QuizState()
    }


    private fun selectQAs(localQuestionNumber: Int) {

        val qas = _qaList.value[localQuestionNumber]

        val isKanjiRecRequired = kanjiRecRequired.contains(qas.format)
        Log.i("DEBUG", isKanjiRecRequired.toString())
        val isMCAtype = mcaType.contains(qas.format)

        val correctAnswersList = if (isMCAtype) {
            extractStringFromJson(qas.answer).toMutableList()
        } else {
            null
        }

        val mcaList = if (
            isMCAtype
            || qas.format == "busyu"
            || qas.format == "bunpou"
            || qas.format == "mcq"
        ) {
            extractStringFromJson(qas.mca_list!!).toMutableList()
        } else {
            null
        }

        val selectedAnswersList =
            if (isMCAtype) {
                MutableList<String?>(correctAnswersList!!.size) { null }
            } else {
                null
            }

        setExplanation(qas)

        if (qas.question == "auto") {
            viewModelScope.launch {
                getJotobaExampleSentence(qas.answer)
                val shuffledList =
                    mcaList?.shuffled()?.take(_quizState.value.mcaNumber - 1)?.toMutableList()
                shuffledList?.add(qas.answer)
                shuffledList?.shuffle()

                _quizState.update {
                    it.copy(
                        localQuestionNumber = localQuestionNumber,
                        correctAnswer = qas.answer,
                        target = qas.target,
                        questionGlobalId = qas.global_id.toInt(),
                        correctAnswersList = correctAnswersList,
                        selectedAnswersList = selectedAnswersList,
                        mcaList = shuffledList,
                        isKanjiRecRequired = isKanjiRecRequired,
                        questionFormat = qas.format,
                    )
                }
            }

        } else if (qas.question == "db_kana_verb") {
            viewModelScope.launch {

                Log.i("DEB1 kanaverb_verb", qas.answer)
                val wordInfo = managerDataSource.getWordInfoGivenReading(qas.answer, qas.target!!)!!
                val wordReading = wordInfo.reading
                Log.i("DEB1 kanaverb_reading", wordReading!!)

                val exampleSentences = managerDataSource.getExamplesList(wordInfo.id).shuffled()

                val example = exampleSentences[0]
                val isKana = example.is_kana == 1L
                val verbForm = example.form
                Log.i("DEB1 kanaverb", verbForm!!)

                val isIchidan = wordInfo.word_type == "ichidan"

                val inflectedVerbMap = getVerbInflection(wordReading, isIchidan)
                val correctInflectedVerb = inflectedVerbMap[verbForm]!!

                Log.i("DEB1 inflected", correctInflectedVerb)


                val mcaList = extractStringFromJson(qas.mca_list!!).toMutableList()

                val shuffledList =
                    mcaList.shuffled().take(_quizState.value.mcaNumber - 1).toMutableList()


                val newMCAlist = mutableListOf(correctInflectedVerb)

                for (v in shuffledList) {
                    Log.i(" kanaverb", v)
                    val qWordInfo = managerDataSource.getWordInfo(v)[0]
                    val wordReading = qWordInfo.reading!!
                    val isIchidan = qWordInfo.word_type == "ichidan"
                    val inflectedVerbMap = getVerbInflection(wordReading, isIchidan)
                    val inflectedVerb = inflectedVerbMap[verbForm]!!
                    newMCAlist.add(inflectedVerb)
                }

                newMCAlist.shuffle()

                val target = if (isKana) {
                    correctInflectedVerb
                } else {
                    val kajiInflectedVerbMap = getVerbInflection(wordReading, isIchidan)
                    kajiInflectedVerbMap[verbForm]!!
                }

                val question = example.sentence_ja.replace(target,"（　）")

                Log.i("DEB1 question", question)

                _quizState.update {
                    it.copy(
                        localQuestionNumber = localQuestionNumber,
                        correctAnswer = correctInflectedVerb,
                        question = question,
                        target = null,
                        questionGlobalId = qas.global_id.toInt(),
                        correctAnswersList = correctAnswersList,
                        selectedAnswersList = selectedAnswersList,
                        mcaList = newMCAlist,
                        isKanjiRecRequired = isKanjiRecRequired,
                        questionFormat = qas.format,
                        explanation = example.sentence_en
                    )

                }

            }
        } else {
            _quizState.update {
                it.copy(
                    localQuestionNumber = localQuestionNumber,
                    question = qas.question,
                    correctAnswer = qas.answer,
                    target = qas.target,
                    questionGlobalId = qas.global_id.toInt(),
                    correctAnswersList = correctAnswersList,
                    selectedAnswersList = selectedAnswersList,
                    mcaList = mcaList,
                    isKanjiRecRequired = isKanjiRecRequired,
                    questionFormat = qas.format
                )
            }
        }

    }

    private fun confirmAnswer(trackingOnAction: (TrackingAction) -> Unit) {
        if (!_quizState.value.isAnswerConfirmed) {

            _quizState.update { it.copy(isAnswerConfirmed = true) }
            val id = _quizState.value.questionGlobalId!!

            if (_quizState.value.questionType == "goji") {
                if (_quizState.value.selectedWrongKanji != _quizState.value.target) {
                    _quizState.update {
                        it.copy(isAnswerCorrect = false)
                    }
                    trackingOnAction(TrackingAction.AddOneWrong(id))
                    return
                }
            }

            if (_quizState.value.inputAnswer == _quizState.value.correctAnswer) {
                _quizState.update { it.copy(isAnswerCorrect = true) }
                trackingOnAction(TrackingAction.AddOneCorrect(id))
            } else {
                _quizState.update { it.copy(isAnswerCorrect = false) }
                trackingOnAction(TrackingAction.AddOneWrong(id))
            }

        }
    }

    private fun confirmAnswersList(trackingOnAction: (TrackingAction) -> Unit) {

        if (!_quizState.value.isAnswerConfirmed) {

            _quizState.update { it.copy(isAnswerConfirmed = true) }
            val id = _quizState.value.questionGlobalId!!


            if (_quizState.value.selectedAnswersList == _quizState.value.correctAnswersList) {
                _quizState.update { it.copy(isAnswerCorrect = true) }
                trackingOnAction(TrackingAction.AddOneCorrect(id))
            } else {
                _quizState.update { it.copy(isAnswerCorrect = false) }
                trackingOnAction(TrackingAction.AddOneWrong(id))
            }

        }

    }


    private fun previousQuestion() {

        if (quizState.value.localQuestionNumber != null) {

            if (quizState.value.localQuestionNumber!! > 0) {
                _quizState.update {
                    it.copy(
                        localQuestionNumber = quizState.value.localQuestionNumber!! - 1
                    )
                }

                selectQAs(quizState.value.localQuestionNumber!!)
                _quizState.update {
                    it.copy(
                        selectedAnswer = null,
                        isAnswerConfirmed = false,
                        isLastQuestion = false,
                        isAnswerCorrect = null,
                        selectedWrongKanji = null,
                        selectedWrongKanjiInd = null,
                        selectedSubQuestionNbr = null
                    )
                }

                if (quizState.value.localQuestionNumber == 0) _quizState.update {
                    it.copy(
                        isFirstQuestion = true
                    )
                }

            }
        }
    }


    private fun nextQuestion() {
        if (quizState.value.localQuestionNumber != null) {
            if (quizState.value.localQuestionNumber!! < _qaList.value.size - 1) {
                _quizState.update {
                    it.copy(
                        localQuestionNumber = quizState.value.localQuestionNumber!! + 1,
                        inputAnswer = null
                    )
                }

                selectQAs(quizState.value.localQuestionNumber!!)
                _quizState.update {
                    it.copy(
                        selectedAnswer = null,
                        isAnswerConfirmed = false,
                        isFirstQuestion = false,
                        isAnswerCorrect = null,
                        selectedWrongKanji = null,
                        selectedWrongKanjiInd = null,
                        selectedSubQuestionNbr = null,
                    )
                }
                if (quizState.value.localQuestionNumber == _qaList.value.size - 1) _quizState.update {
                    it.copy(isLastQuestion = true)
                }
            }
        }
    }


    private fun loadQuestion(selectedQuestionsList: List<Question>) {
        _qaList.value = selectedQuestionsList
    }


    private fun startQuiz() {

        selectQAs(0)
        _quizState.update {
            it.copy(
                isQuizOver = false,
                isQuizStarted = true,
                isFirstQuestion = true,
                isLastQuestion = _qaList.value.size == 1
            )
        }
    }

    private fun selectWrongKanji(kanji: String?, kanjiInd: Int?) {
        _quizState.update { it.copy(selectedWrongKanji = kanji, selectedWrongKanjiInd = kanjiInd) }
    }

    private fun setQuizType(type: String?) {
        _quizState.update { it.copy(questionType = type) }
    }

    private fun setExplanation(question: Question) {

        if (question.format == "kaki" || question.format == "type" || question.format == "mcq") {

            val whole = if (question.format == "kaki") {
                question.question.replace(question.target!!, question.answer)
            } else if (question.format == "type") {
                question.question
            } else {
                question.answer
            }


            Log.i("DEBUG whole", whole)

            val explanation = managerDataSource.getWordInfo(whole)
            if (explanation.isNotEmpty()) {
                val explanation = explanation[0].explanation_ja ?: explanation[0].explanation_en


                Log.i("DEBUG explanationJA", explanation!!)
                _quizState.update { it.copy(explanation = explanation) }
            } else {
                _quizState.update { it.copy(explanation = null) }
            }
        }
    }

    fun getExplanation(word: String): String {

        val explanation = managerDataSource.getWordExplanation(word)

        return if (explanation != null) {
            val explanation = explanation.explanation_ja ?: explanation.explanation_en

            explanation ?: "no explanation"

        } else {
            "no explanation"
        }

    }

    private suspend fun getJotobaExampleSentence(word: String) {

        val request = PostRequest(
            query = word,
            language = "English",
            no_english = true
        )

        val jotobaResponse = service.createPost(request)

        Log.i("INVM0", jotobaResponse.toString())

        if (jotobaResponse != null) {
            val sentencesJsonElement = extractMapFromJson(jotobaResponse, "sentences")
            val sentencesElementList =
                extractStringFromJson(sentencesJsonElement.toString(), removeQuotationMarks = false)

            Log.i("INVM1", word)
            Log.i("INVM2", sentencesElementList.size.toString())

            if (sentencesElementList.size != 0) {

                val randomIndex = Random.nextInt(sentencesElementList.size)
                val selectedSentence = sentencesElementList[randomIndex]

                Log.i("INVM0", selectedSentence)

                val question =
                    extractMapFromJson(selectedSentence, "content").toString().replace(word, "　(　）")
                        .replace(""""""", "")
                val explanation = extractMapFromJson(selectedSentence, "translation").toString()
                    .replace(""""""", "")

                _quizState.update { it.copy(question = question) }
                _quizState.update { it.copy(questionTranslation = explanation) }

            } else {
                val explanation = managerDataSource.getWordInfo(word)
                if (explanation != null) {
                    val explanation = explanation[0].explanation_ja ?: explanation[0].explanation_en


                    Log.i("DEBUG explanationJA", explanation!!)
                    _quizState.update { it.copy(question = explanation) }
                } else {
                    _quizState.update { it.copy(question = "$word no example") }
                }
            }


        }


    }


}