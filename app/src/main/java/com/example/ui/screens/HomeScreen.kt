package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContentCategory
import com.example.data.model.ContentItem
import com.example.data.model.DailyMotivation
import com.example.ui.components.CategoryChipRow
import com.example.ui.components.ContentCard
import com.example.ui.components.DailyMotivationCard
import com.example.ui.components.RiseHeader
import com.example.ui.theme.SunriseOrange

@Composable
fun HomeScreen(
    dailyMotivation: DailyMotivation,
    isChallengeCompleted: Boolean,
    selectedCategory: ContentCategory,
    contentList: List<ContentItem>,
    recommendedList: List<ContentItem>,
    searchQuery: String,
    onCategorySelected: (ContentCategory) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onCompleteChallenge: () -> Unit,
    onContentClick: (ContentItem) -> Unit,
    onReportClick: (ContentItem) -> Unit,
    onWatchRecommended: (String) -> Unit,
    onApiGuideClick: () -> Unit,
    watchedLastHourCount: Int,
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_list")
    ) {
        // App Top Header
        item {
            RiseHeader(
                onSearchClick = { /* User can focus on search bar below */ },
                onApiGuideClick = onApiGuideClick
            )
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Search Motivation, Science, Space, Careers...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon",
                        tint = SunriseOrange
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("home_search_input"),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SunriseOrange,
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        // Daily Motivation Banner (shown when not searching)
        if (searchQuery.isBlank()) {
            item {
                DailyMotivationCard(
                    dailyMotivation = dailyMotivation,
                    isChallengeCompleted = isChallengeCompleted,
                    onCompleteChallenge = onCompleteChallenge,
                    onWatchRecommended = onWatchRecommended
                )
            }

            // 1-Hour Quiz Banner
            item {
                androidx.compose.material3.ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("home_quiz_card"),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                    colors = androidx.compose.material3.CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    ),
                    elevation = androidx.compose.material3.CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                androidx.compose.material3.Surface(
                                    shape = androidx.compose.foundation.shape.CircleShape,
                                    color = if (watchedLastHourCount > 0) Color(0xFFE8F5E9) else Color(0xFFFFF3E0),
                                    modifier = Modifier.padding(end = 10.dp)
                                ) {
                                    Text(
                                        text = if (watchedLastHourCount > 0) "🎯" else "🧠",
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Quiz on Last 1h Videos",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                    Text(
                                        text = if (watchedLastHourCount > 0) {
                                            "🔥 $watchedLastHourCount video(s) watched in the last hour"
                                        } else {
                                            "Review recent topics & test your knowledge"
                                        },
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (watchedLastHourCount > 0) com.example.ui.theme.GrowthGreen else Color(0xFF64748B),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            androidx.compose.material3.Button(
                                onClick = onStartQuiz,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = if (watchedLastHourCount > 0) com.example.ui.theme.GrowthGreen else com.example.ui.theme.SunriseAmber
                                ),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("start_1h_quiz_btn")
                            ) {
                                Text("Take Quiz", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }

        // Category Filter Chips
        item {
            Spacer(modifier = Modifier.height(6.dp))
            CategoryChipRow(
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected
            )
        }

        // Section Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (searchQuery.isNotBlank()) {
                        "Search Results (${contentList.size})"
                    } else if (selectedCategory != ContentCategory.ALL) {
                        "${selectedCategory.displayName} (${contentList.size})"
                    } else {
                        "Recommended for You"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                if (searchQuery.isBlank() && selectedCategory == ContentCategory.ALL) {
                    Text(
                        text = "Curated & Safe",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Content Cards List
        val itemsToDisplay = if (searchQuery.isNotBlank()) {
            contentList
        } else if (selectedCategory == ContentCategory.ALL) {
            recommendedList
        } else {
            contentList
        }

        if (itemsToDisplay.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🔍", fontSize = 36.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "No educational items found for '$searchQuery'",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            items(itemsToDisplay, key = { it.id }) { item ->
                ContentCard(
                    item = item,
                    onCardClick = { onContentClick(item) },
                    onReportClick = { onReportClick(item) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
