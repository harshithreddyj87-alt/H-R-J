package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ContentCategory
import com.example.ui.MainViewModel
import com.example.ui.ScreenTab
import com.example.ui.components.ApiGuideDialog
import com.example.ui.components.QuizDialog
import com.example.ui.components.ReportContentDialog
import com.example.ui.components.VideoPlayerModal
import com.example.ui.screens.CategoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.theme.RiseAndShineTheme
import com.example.ui.theme.SunriseAmber
import com.example.ui.theme.SunriseOrange

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RiseAndShineTheme {
                RiseAndShineApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun RiseAndShineApp(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val progress by viewModel.progress.collectAsStateWithLifecycle()
    val isTodayChallengeCompleted by viewModel.isTodayChallengeCompleted.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Listen for notification messages and show snackbar
    LaunchedEffect(uiState.userNotificationMessage) {
        uiState.userNotificationMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            viewModel.clearNotificationMessage()
        }
    }

    val navItems = listOf(
        Triple(ScreenTab.HOME, "Home", Icons.Default.Home),
        Triple(ScreenTab.MOTIVATION, "Motivation", Icons.Default.Bolt),
        Triple(ScreenTab.EDUCATION, "Education", Icons.Default.School),
        Triple(ScreenTab.CAREERS, "Careers", Icons.Default.Work),
        Triple(ScreenTab.STORIES, "Stories", Icons.Default.AutoStories),
        Triple(ScreenTab.LIVE, "Live", Icons.Default.LiveTv),
        Triple(ScreenTab.PROGRESS, "Progress", Icons.Default.EmojiEvents)
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isExpandedScreen = maxWidth >= 700.dp

        Scaffold(
            modifier = Modifier.fillMaxSize().testTag("rise_and_shine_scaffold"),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                if (!isExpandedScreen) {
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 8.dp,
                        modifier = Modifier.testTag("bottom_nav_bar")
                    ) {
                        navItems.forEach { (tab, title, icon) ->
                            val selected = uiState.currentTab == tab
                            NavigationBarItem(
                                selected = selected,
                                onClick = { viewModel.selectTab(tab) },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = title,
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = title,
                                        fontSize = 10.sp,
                                        maxLines = 1
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = SunriseOrange,
                                    selectedTextColor = SunriseOrange,
                                    indicatorColor = Color(0xFFFFE0B2),
                                    unselectedIconColor = Color(0xFF64748B),
                                    unselectedTextColor = Color(0xFF64748B)
                                ),
                                modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Adaptive NavigationRail for Tablets / Wide Screens
                if (isExpandedScreen) {
                    NavigationRail(
                        containerColor = Color.White,
                        modifier = Modifier
                            .fillMaxHeight()
                            .testTag("navigation_rail")
                    ) {
                        navItems.forEach { (tab, title, icon) ->
                            val selected = uiState.currentTab == tab
                            NavigationRailItem(
                                selected = selected,
                                onClick = { viewModel.selectTab(tab) },
                                icon = { Icon(imageVector = icon, contentDescription = title) },
                                label = { Text(title) },
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = SunriseOrange,
                                    selectedTextColor = SunriseOrange,
                                    indicatorColor = Color(0xFFFFE0B2)
                                ),
                                modifier = Modifier.testTag("rail_item_${tab.name.lowercase()}")
                            )
                        }
                    }
                }

                // Main Content View based on current tab
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    when (uiState.currentTab) {
                        ScreenTab.HOME -> {
                            HomeScreen(
                                dailyMotivation = viewModel.dailyMotivation,
                                isChallengeCompleted = isTodayChallengeCompleted,
                                selectedCategory = uiState.selectedCategoryFilter,
                                contentList = viewModel.getFilteredContent(),
                                recommendedList = viewModel.getRecommendedContent(),
                                searchQuery = uiState.searchQuery,
                                onCategorySelected = { cat ->
                                    if (cat == ContentCategory.MOTIVATION) viewModel.selectTab(ScreenTab.MOTIVATION)
                                    else if (cat == ContentCategory.EDUCATION) viewModel.selectTab(ScreenTab.EDUCATION)
                                    else if (cat == ContentCategory.CAREERS) viewModel.selectTab(ScreenTab.CAREERS)
                                    else if (cat == ContentCategory.MORAL_STORIES) viewModel.selectTab(ScreenTab.STORIES)
                                    else if (cat == ContentCategory.LIVE) viewModel.selectTab(ScreenTab.LIVE)
                                    else viewModel.setCategoryFilter(cat)
                                },
                                onSearchQueryChange = { q -> viewModel.updateSearchQuery(q) },
                                onCompleteChallenge = { viewModel.completeTodayChallenge() },
                                onContentClick = { item -> viewModel.openContentDetail(item) },
                                onReportClick = { item -> viewModel.openReportDialog(item) },
                                onWatchRecommended = { contentId ->
                                    viewModel.getContentById(contentId)?.let {
                                        viewModel.openContentDetail(it)
                                    }
                                },
                                onApiGuideClick = { viewModel.toggleApiGuideDialog(true) },
                                watchedLastHourCount = uiState.watchedLastHourCount,
                                onStartQuiz = { viewModel.startQuizForLastHour() }
                            )
                        }
                        ScreenTab.MOTIVATION -> {
                            CategoryScreen(
                                category = ContentCategory.MOTIVATION,
                                contentList = viewModel.getFilteredContent(),
                                onContentClick = { item -> viewModel.openContentDetail(item) },
                                onReportClick = { item -> viewModel.openReportDialog(item) }
                            )
                        }
                        ScreenTab.EDUCATION -> {
                            CategoryScreen(
                                category = ContentCategory.EDUCATION,
                                contentList = viewModel.getFilteredContent(),
                                onContentClick = { item -> viewModel.openContentDetail(item) },
                                onReportClick = { item -> viewModel.openReportDialog(item) }
                            )
                        }
                        ScreenTab.CAREERS -> {
                            CategoryScreen(
                                category = ContentCategory.CAREERS,
                                contentList = viewModel.getFilteredContent(),
                                onContentClick = { item -> viewModel.openContentDetail(item) },
                                onReportClick = { item -> viewModel.openReportDialog(item) }
                            )
                        }
                        ScreenTab.STORIES -> {
                            CategoryScreen(
                                category = ContentCategory.MORAL_STORIES,
                                contentList = viewModel.getFilteredContent(),
                                onContentClick = { item -> viewModel.openContentDetail(item) },
                                onReportClick = { item -> viewModel.openReportDialog(item) }
                            )
                        }
                        ScreenTab.LIVE -> {
                            CategoryScreen(
                                category = ContentCategory.LIVE,
                                contentList = viewModel.getLiveContent(),
                                onContentClick = { item -> viewModel.openContentDetail(item) },
                                onReportClick = { item -> viewModel.openReportDialog(item) }
                            )
                        }
                        ScreenTab.PROGRESS -> {
                            ProgressScreen(
                                progress = progress,
                                isTodayChallengeCompleted = isTodayChallengeCompleted,
                                watchedLastHourCount = uiState.watchedLastHourCount,
                                onStartQuiz = { viewModel.startQuizForLastHour() }
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal Video Player / Story Viewer
    uiState.activeContentDetail?.let { item ->
        VideoPlayerModal(
            item = item,
            onDismiss = { viewModel.closeContentDetail() },
            onMarkWatched = { viewModel.markContentAsWatched(item) }
        )
    }

    // Modal 1-Hour Quiz Dialog
    uiState.activeQuiz?.let { session ->
        QuizDialog(
            quizSession = session,
            onSelectOption = { idx -> viewModel.selectQuizOption(idx) },
            onCheckAnswer = { viewModel.checkQuizAnswer() },
            onNextQuestion = { viewModel.nextQuizQuestion() },
            onRestartQuiz = { viewModel.startQuizForLastHour() },
            onDismiss = { viewModel.closeQuiz() }
        )
    }

    // Modal Report Dialog
    if (uiState.isReportDialogOpen && uiState.reportingContent != null) {
        ReportContentDialog(
            item = uiState.reportingContent!!,
            onDismiss = { viewModel.dismissReportDialog() },
            onSubmitReport = { reason -> viewModel.submitReport(reason) }
        )
    }

    // API Integration and Safe Source Guide
    if (uiState.isApiGuideDialogOpen) {
        ApiGuideDialog(
            onDismiss = { viewModel.toggleApiGuideDialog(false) }
        )
    }
}
