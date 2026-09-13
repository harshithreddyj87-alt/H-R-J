package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.RiseAndShineDatabase
import com.example.data.local.StudentProgressEntity
import com.example.data.model.ContentCategory
import com.example.data.model.ContentItem
import com.example.data.model.ContentReport
import com.example.data.model.DailyMotivation
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizSession
import com.example.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ScreenTab(val title: String, val iconName: String) {
    HOME("Home", "home"),
    MOTIVATION("Motivation", "bolt"),
    EDUCATION("Education", "school"),
    CAREERS("Careers", "work"),
    STORIES("Stories", "auto_stories"),
    LIVE("Live", "live_tv"),
    PROGRESS("My Progress", "emoji_events")
}

data class UiState(
    val currentTab: ScreenTab = ScreenTab.HOME,
    val selectedCategoryFilter: ContentCategory = ContentCategory.ALL,
    val searchQuery: String = "",
    val activeContentDetail: ContentItem? = null,
    val isReportDialogOpen: Boolean = false,
    val reportingContent: ContentItem? = null,
    val isApiGuideDialogOpen: Boolean = false,
    val userNotificationMessage: String? = null,
    val activeQuiz: QuizSession? = null,
    val watchedLastHourCount: Int = 0
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContentRepository

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val progress: StateFlow<StudentProgressEntity>
    val isTodayChallengeCompleted: StateFlow<Boolean>
    val dailyMotivation: DailyMotivation

    init {
        val database = RiseAndShineDatabase.getInstance(application)
        repository = ContentRepository(database.progressDao())
        dailyMotivation = repository.getTodayMotivation()

        progress = repository.getProgressFlow()
            .map { it ?: StudentProgressEntity() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StudentProgressEntity(
                    streakDays = 3,
                    totalChallengesCompleted = 2,
                    totalVideosWatched = 5,
                    totalLessonsCompleted = 3,
                    totalStoriesRead = 4
                )
            )

        isTodayChallengeCompleted = repository.isTodayChallengeCompletedFlow()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )

        refreshWatchedLastHourCount()
    }

    fun selectTab(tab: ScreenTab) {
        _uiState.value = _uiState.value.copy(
            currentTab = tab,
            selectedCategoryFilter = when (tab) {
                ScreenTab.MOTIVATION -> ContentCategory.MOTIVATION
                ScreenTab.EDUCATION -> ContentCategory.EDUCATION
                ScreenTab.CAREERS -> ContentCategory.CAREERS
                ScreenTab.STORIES -> ContentCategory.MORAL_STORIES
                ScreenTab.LIVE -> ContentCategory.LIVE
                else -> ContentCategory.ALL
            }
        )
    }

    fun setCategoryFilter(category: ContentCategory) {
        _uiState.value = _uiState.value.copy(selectedCategoryFilter = category)
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun openContentDetail(item: ContentItem) {
        _uiState.value = _uiState.value.copy(activeContentDetail = item)
    }

    fun closeContentDetail() {
        _uiState.value = _uiState.value.copy(activeContentDetail = null)
    }

    fun markContentAsWatched(item: ContentItem) {
        viewModelScope.launch {
            repository.markContentAsWatched(item)
            val recent = repository.getWatchedItemsInLastHour()
            _uiState.value = _uiState.value.copy(
                userNotificationMessage = "Awesome! Progress recorded for '${item.title}'",
                watchedLastHourCount = recent.size
            )
        }
    }

    fun completeTodayChallenge() {
        viewModelScope.launch {
            repository.completeTodayChallenge(dailyMotivation.positiveChallenge)
            _uiState.value = _uiState.value.copy(
                userNotificationMessage = "Superb! Daily Challenge Completed! Streak +1 🌟"
            )
        }
    }

    fun openReportDialog(item: ContentItem) {
        _uiState.value = _uiState.value.copy(
            isReportDialogOpen = true,
            reportingContent = item
        )
    }

    fun dismissReportDialog() {
        _uiState.value = _uiState.value.copy(
            isReportDialogOpen = false,
            reportingContent = null
        )
    }

    fun refreshWatchedLastHourCount() {
        viewModelScope.launch {
            val recent = repository.getWatchedItemsInLastHour()
            _uiState.value = _uiState.value.copy(watchedLastHourCount = recent.size)
        }
    }

    fun startQuizForLastHour() {
        viewModelScope.launch {
            val session = repository.generateQuizForLastHour()
            _uiState.value = _uiState.value.copy(
                activeQuiz = session,
                watchedLastHourCount = session.watchedVideosCountInLastHour
            )
        }
    }

    fun selectQuizOption(index: Int) {
        val currentSession = _uiState.value.activeQuiz ?: return
        if (currentSession.isAnswerChecked) return
        _uiState.value = _uiState.value.copy(
            activeQuiz = currentSession.copy(selectedOptionIndex = index)
        )
    }

    fun checkQuizAnswer() {
        val currentSession = _uiState.value.activeQuiz ?: return
        val currentQ = currentSession.questions.getOrNull(currentSession.currentQuestionIndex) ?: return
        val selected = currentSession.selectedOptionIndex ?: return

        val isCorrect = selected == currentQ.correctOptionIndex
        val newCorrectCount = if (isCorrect) currentSession.correctAnswersCount + 1 else currentSession.correctAnswersCount

        _uiState.value = _uiState.value.copy(
            activeQuiz = currentSession.copy(
                isAnswerChecked = true,
                correctAnswersCount = newCorrectCount
            )
        )
    }

    fun nextQuizQuestion() {
        val currentSession = _uiState.value.activeQuiz ?: return
        val nextIndex = currentSession.currentQuestionIndex + 1

        if (nextIndex >= currentSession.questions.size) {
            // Quiz finished
            viewModelScope.launch {
                repository.recordQuizCompletion(
                    currentSession.correctAnswersCount,
                    currentSession.questions.size
                )
            }
            _uiState.value = _uiState.value.copy(
                activeQuiz = currentSession.copy(isFinished = true),
                userNotificationMessage = "Quiz Complete! +1 Lesson Added to Progress 🌟"
            )
        } else {
            _uiState.value = _uiState.value.copy(
                activeQuiz = currentSession.copy(
                    currentQuestionIndex = nextIndex,
                    selectedOptionIndex = null,
                    isAnswerChecked = false
                )
            )
        }
    }

    fun closeQuiz() {
        _uiState.value = _uiState.value.copy(activeQuiz = null)
        refreshWatchedLastHourCount()
    }

    fun submitReport(reason: String) {
        val item = _uiState.value.reportingContent
        _uiState.value = _uiState.value.copy(
            isReportDialogOpen = false,
            reportingContent = null,
            userNotificationMessage = "Thank you! Content reported for safety review: $reason"
        )
    }

    fun toggleApiGuideDialog(open: Boolean) {
        _uiState.value = _uiState.value.copy(isApiGuideDialogOpen = open)
    }

    fun clearNotificationMessage() {
        _uiState.value = _uiState.value.copy(userNotificationMessage = null)
    }

    // Repository content query helpers
    fun getFilteredContent(): List<ContentItem> {
        val state = _uiState.value
        return if (state.searchQuery.isNotBlank()) {
            repository.searchContent(state.searchQuery, state.selectedCategoryFilter)
        } else {
            repository.getContentByCategory(state.selectedCategoryFilter)
        }
    }

    fun getRecommendedContent(): List<ContentItem> {
        return repository.getRecommendedContent()
    }

    fun getLiveContent(): List<ContentItem> {
        return repository.getLiveContent()
    }

    fun getContentById(id: String): ContentItem? {
        return repository.getContentById(id)
    }
}
