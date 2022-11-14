package com.talisol.nihongodrill.quizUtils


object VerbEndings {

    private val uMap = GodanEndings(
        "う",
        "い",
        "わ",
        "え",
        "お",
        "って",
        "った",
    )

    private val kuMap = GodanEndings(
        "く",
        "き",
        "か",
        "け",
        "こ",
        "いて",
        "いた"
    )

    private val guMap = GodanEndings(
        "ぐ",
        "ぎ",
        "が",
        "げ",
        "ご",
        "いで",
        "いだ"
    )

    private val suMap = GodanEndings(
        "す",
        "し",
        "さ",
        "せ",
        "そ",
        "して",
        "した"
    )


    private val tuMap = GodanEndings(
        "つ",
        "ち",
        "た",
        "て",
        "と",
        "って",
        "った"
    )


    private val nuMap = GodanEndings(
        "ぬ",
        "に",
        "な",
        "ね",
        "の",
        "んで",
        "んだ"
    )

    private val buMap = GodanEndings(
        "ぶ",
        "び",
        "ば",
        "べ",
        "ぼ",
        "んで",
        "んだ"
    )


    private val muMap = GodanEndings(
        "む",
        "み",
        "ま",
        "め",
        "も",
        "んで",
        "んだ"
    )


    private val ruMap = GodanEndings(
        "る",
        "り",
        "ら",
        "れ",
        "ろ",
        "って",
        "った"
    )

    val endingsMap = mapOf(
        "う" to uMap,
        "く" to kuMap,
        "ぐ" to guMap,
        "す" to suMap,
        "つ" to tuMap,
        "ぬ" to nuMap,
        "ぶ" to buMap,
        "む" to muMap,
        "る" to ruMap
    )


}

