package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContentCategory
import com.example.data.model.ContentItem
import com.example.ui.components.ContentCard
import com.example.ui.theme.GrowthGreen
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunGold
import com.example.ui.theme.SunriseAmber
import com.example.ui.theme.SunriseOrange

@Composable
fun CategoryScreen(
    category: ContentCategory,
    contentList: List<ContentItem>,
    onContentClick: (ContentItem) -> Unit,
    onReportClick: (ContentItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientColors = when (category) {
        ContentCategory.MOTIVATION -> listOf(SunriseOrange, SunriseAmber, SunGold)
        ContentCategory.EDUCATION -> listOf(Color(0xFF0288D1), Color(0xFF0277BD), Color(0xFF0097A7))
        ContentCategory.CAREERS -> listOf(Color(0xFF2E7D32), Color(0xFF388E3C), Color(0xFF43A047))
        ContentCategory.MORAL_STORIES -> listOf(Color(0xFFE65100), Color(0xFFEF6C00), Color(0xFFF57C00))
        ContentCategory.LIVE -> listOf(Color(0xFFC62828), Color(0xFFD32F2F), Color(0xFFE53935))
        else -> listOf(SunriseOrange, SkyBlue)
    }

    val subtitle = when (category) {
        ContentCategory.MOTIVATION -> "Build grit, positive habits, discipline, and confidence."
        ContentCategory.EDUCATION -> "Explore Math, Science, Space, Coding & English essentials."
        ContentCategory.CAREERS -> "Interviews with Scientists, Doctors, Tech Founders & Authors."
        ContentCategory.MORAL_STORIES -> "Character-building stories with clear lessons at the end."
        ContentCategory.LIVE -> "Safe live educational, science, and astronomy broadcasts."
        else -> "Learn. Believe. Grow."
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("category_screen_${category.name.lowercase()}"),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Hero Header
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = Brush.horizontalGradient(gradientColors))
                        .padding(20.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.25f),
                                modifier = Modifier.size(46.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(category.emoji, fontSize = 24.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = category.displayName,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "${contentList.size} Lessons & Videos",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.95f)
                        )
                    }
                }
            }
        }

        // Content Cards
        items(contentList, key = { it.id }) { item ->
            ContentCard(
                item = item,
                onCardClick = { onContentClick(item) },
                onReportClick = { onReportClick(item) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
