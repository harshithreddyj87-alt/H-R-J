package com.example.data.model

enum class ContentCategory(val displayName: String, val emoji: String, val shortDesc: String) {
    ALL("All", "🌟", "Everything for you"),
    MOTIVATION("Motivation", "🔥", "Inspiration & Good Habits"),
    EDUCATION("Education", "📚", "Math, Science & Life Skills"),
    CAREERS("Business & Careers", "🚀", "Real People, Inspiring Careers"),
    MORAL_STORIES("Moral Stories", "🌱", "Timeless Values & Character"),
    LIVE("Live Streams", "🔴", "Safe Educational Broadcasts")
}

data class ContentItem(
    val id: String,
    val title: String,
    val description: String,
    val category: ContentCategory,
    val topic: String,
    val sourceName: String,
    val sourcePlatform: String,
    val duration: String,
    val youtubeVideoId: String? = null,
    val webUrl: String,
    val moralOrKeyLesson: String? = null,
    val careerRole: String? = null,
    val targetAgeGroup: String = "Class 6–10",
    val isLive: Boolean = false,
    val isRecommended: Boolean = false,
    val tags: List<String> = emptyList()
)

data class DailyMotivation(
    val quote: String,
    val author: String,
    val recommendedContentId: String,
    val positiveChallenge: String,
    val challengeDescription: String
)

data class ContentReport(
    val contentId: String,
    val contentTitle: String,
    val reason: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class QuizQuestion(
    val id: String,
    val contentId: String,
    val contentTitle: String,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class QuizSession(
    val questions: List<QuizQuestion>,
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerChecked: Boolean = false,
    val correctAnswersCount: Int = 0,
    val isFinished: Boolean = false,
    val watchedVideosCountInLastHour: Int = 0
)

