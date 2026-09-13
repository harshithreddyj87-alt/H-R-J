package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContentItem
import com.example.ui.components.ContentCard
import com.example.ui.theme.SunriseAmber
import com.example.ui.theme.SunriseOrange

@Composable
fun SearchScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchResults: List<ContentItem>,
    onContentClick: (ContentItem) -> Unit,
    onReportClick: (ContentItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val suggestedTopics = listOf(
        "Motivation",
        "Science",
        "Space",
        "Success stories",
        "Study tips",
        "Entrepreneurs",
        "English",
        "Mathematics",
        "Moral stories"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("search_screen")
    ) {
        Text(
            text = "Discover & Learn",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )
        Text(
            text = "Safe, age-appropriate content for school students",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF64748B)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search any school subject or inspiration...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = SunriseOrange)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_screen_input"),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SunriseOrange,
                unfocusedBorderColor = Color(0xFFCBD5E1),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Popular Topics
        Text(
            text = "Popular Topics",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF334155)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestedTopics.forEach { topic ->
                AssistChip(
                    onClick = { onSearchQueryChange(topic) },
                    label = { Text(topic, style = MaterialTheme.typography.labelMedium) },
                    shape = RoundedCornerShape(12.dp),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (searchQuery.equals(topic, ignoreCase = true)) {
                            SunriseAmber.copy(alpha = 0.2f)
                        } else {
                            Color.White
                        }
                    ),
                    modifier = Modifier.testTag("topic_chip_${topic.lowercase().replace(" ", "_")}")
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Search Results Count
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (searchQuery.isNotBlank()) "Results for \"$searchQuery\" (${searchResults.size})" else "All Curated Learning Resources (${searchResults.size})",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF475569)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (searchResults.isEmpty()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF8FAFC),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("📚", fontSize = 36.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "No results matching \"$searchQuery\"",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1E293B)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Try searching for Science, Space, Math, Habits or Kalam",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            } else {
                items(searchResults, key = { it.id }) { item ->
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
}
