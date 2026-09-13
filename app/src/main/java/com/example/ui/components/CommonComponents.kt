package com.example.ui.components

import android.content.Intent
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ContentCategory
import com.example.data.model.ContentItem
import com.example.data.model.DailyMotivation
import com.example.ui.theme.GrowthGreen
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunGold
import com.example.ui.theme.SunriseAmber
import com.example.ui.theme.SunriseOrange

@Composable
fun RiseHeader(
    onSearchClick: () -> Unit,
    onApiGuideClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("app_header_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE65100),
                            Color(0xFFF57C00),
                            Color(0xFF0288D1)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.25f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("☀️", fontSize = 24.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Rise & Shine",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Learn. Believe. Grow.",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }

                    Row {
                        IconButton(
                            onClick = onApiGuideClick,
                            modifier = Modifier.testTag("api_guide_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "API Guide & Safety",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = onSearchClick,
                            modifier = Modifier.testTag("header_search_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.22f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🎓", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Created with purpose by Harshith Reddy J • Class 8, JNV Kolar",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.95f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChipRow(
    selectedCategory: ContentCategory,
    onCategorySelected: (ContentCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ContentCategory.values().forEach { category ->
            val isSelected = selectedCategory == category
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(category.emoji, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(category.displayName, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                    }
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SunriseAmber,
                    selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("chip_${category.name.lowercase()}")
            )
        }
    }
}

@Composable
fun DailyMotivationCard(
    dailyMotivation: DailyMotivation,
    isChallengeCompleted: Boolean,
    onCompleteChallenge: () -> Unit,
    onWatchRecommended: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("daily_motivation_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8E1)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚡", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Daily Motivation",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SunriseOrange
                    )
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SunriseAmber.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Today's Boost",
                        style = MaterialTheme.typography.labelSmall,
                        color = SunriseOrange,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Quote Box
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "“${dailyMotivation.quote}”",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2D3748),
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "— ${dailyMotivation.author}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = SunriseOrange,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Positive Daily Challenge
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (isChallengeCompleted) Color(0xFFE8F5E9) else Color(0xFFE1F5FE),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(if (isChallengeCompleted) "✅" else "🎯", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Today's Positive Challenge",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (isChallengeCompleted) GrowthGreen else SkyBlue
                            )
                        }

                        if (isChallengeCompleted) {
                            Surface(
                                shape = CircleShape,
                                color = GrowthGreen
                            ) {
                                Text(
                                    text = "Completed!",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = dailyMotivation.positiveChallenge,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dailyMotivation.challengeDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { onWatchRecommended(dailyMotivation.recommendedContentId) },
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("watch_daily_recommended_btn")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Recommended Video", style = MaterialTheme.typography.labelMedium)
                        }

                        if (!isChallengeCompleted) {
                            Button(
                                onClick = onCompleteChallenge,
                                colors = ButtonDefaults.buttonColors(containerColor = GrowthGreen),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("complete_challenge_btn")
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("I Did This!", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContentCard(
    item: ContentItem,
    onCardClick: () -> Unit,
    onReportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onCardClick() }
            .testTag("content_card_${item.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Tag Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = when (item.category) {
                            ContentCategory.MOTIVATION -> Color(0xFFFFE082)
                            ContentCategory.EDUCATION -> Color(0xFFB3E5FC)
                            ContentCategory.CAREERS -> Color(0xFFC8E6C9)
                            ContentCategory.MORAL_STORIES -> Color(0xFFFFCCBC)
                            ContentCategory.LIVE -> Color(0xFFFFCDD2)
                            else -> Color(0xFFEEEEEE)
                        }
                    ) {
                        Text(
                            text = "${item.category.emoji} ${item.topic}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    if (item.isLive) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFD32F2F)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color.White, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "LIVE",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    if (item.careerRole != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFEDE7F6)
                        ) {
                            Text(
                                text = "💼 ${item.careerRole}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF4A148C),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onReportClick,
                    modifier = Modifier.size(28.dp).testTag("report_btn_${item.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = "Report Content",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF475569),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )

            // Moral highlight for moral stories
            if (item.moralOrKeyLesson != null && item.category == ContentCategory.MORAL_STORIES) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF1F8E9),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🌱", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Moral: ${item.moralOrKeyLesson}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = GrowthGreen,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Footer row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Source: ${item.sourceName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("•", color = Color(0xFFCBD5E1))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.duration,
                        style = MaterialTheme.typography.labelSmall,
                        color = SunriseAmber,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onCardClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (item.isLive) Color(0xFFD32F2F) else SunriseAmber
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("open_content_${item.id}")
                ) {
                    Icon(
                        imageVector = if (item.category == ContentCategory.MORAL_STORIES && item.youtubeVideoId == null) {
                            Icons.Default.Bookmark
                        } else {
                            Icons.Default.PlayArrow
                        },
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (item.category == ContentCategory.MORAL_STORIES && item.youtubeVideoId == null) "Read" else "Watch",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPlayerModal(
    item: ContentItem,
    onDismiss: () -> Unit,
    onMarkWatched: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isMarkedAsDone by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Top dismiss bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SunriseAmber.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${item.category.emoji} ${item.category.displayName}",
                                style = MaterialTheme.typography.labelMedium,
                                color = SunriseOrange,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Safe Kids Mode",
                            style = MaterialTheme.typography.labelSmall,
                            color = GrowthGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_player_btn")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Embedded Web / YouTube Frame
                if (item.youtubeVideoId != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black)
                    ) {
                        AndroidView(
                            factory = { ctx ->
                                WebView(ctx).apply {
                                    settings.javaScriptEnabled = true
                                    settings.domStorageEnabled = true
                                    settings.mediaPlaybackRequiresUserGesture = false
                                    webChromeClient = WebChromeClient()
                                    webViewClient = WebViewClient()
                                    // Official safe embed with no-cookie privacy domain
                                    loadUrl("https://www.youtube-nocookie.com/embed/${item.youtubeVideoId}?rel=0&modestbranding=1")
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    // Story Reading Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFF9C4), RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7))
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("📖", fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Read Safe Story",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SunriseOrange
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF2D3748),
                                lineHeight = 24.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Key Lesson / Moral Banner
                if (item.moralOrKeyLesson != null) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFE8F5E9),
                        border = CardDefaults.outlinedCardBorder(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = GrowthGreen
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Key Lesson / Moral",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = GrowthGreen
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.moralOrKeyLesson,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Source Information & Attribution
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF8FAFC),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Source & Platform Attribution",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${item.sourceName} (${item.sourcePlatform})",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Content is displayed under legitimate official public embed terms. Copyright remains with the respective creator.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.webUrl))
                            context.startActivity(browserIntent)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("open_source_site_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Source")
                    }

                    Button(
                        onClick = {
                            isMarkedAsDone = true
                            onMarkWatched()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isMarkedAsDone) GrowthGreen else SunriseAmber
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mark_watched_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (isMarkedAsDone) Icons.Default.Done else Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isMarkedAsDone) "Completed!" else "Mark Finished")
                    }
                }
            }
        }
    }
}

@Composable
fun ReportContentDialog(
    item: ContentItem,
    onDismiss: () -> Unit,
    onSubmitReport: (String) -> Unit
) {
    val reportReasons = listOf(
        "Not age-appropriate for school children",
        "Video or link is broken / unavailable",
        "Misleading title or description",
        "Other safety / educational concern"
    )
    var selectedReason by remember { mutableStateOf(reportReasons[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFFD32F2F))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Safety & Content Report", fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column {
                Text(
                    text = "Rise & Shine is strictly for safe, positive learning. Why are you reporting '${item.title}'?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF475569)
                )
                Spacer(modifier = Modifier.height(12.dp))
                reportReasons.forEach { reason ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedReason = reason }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedReason == reason,
                            onClick = { selectedReason = reason }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = reason,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF1E293B)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSubmitReport(selectedReason) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                modifier = Modifier.testTag("submit_report_btn")
            ) {
                Text("Submit Report")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ApiGuideDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = SkyBlue)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Legitimate APIs & Copyright Guide", fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                Text(
                    text = "For Harshith Reddy J & Educators",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = SunriseOrange
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Rise & Shine is designed in full compliance with copyright and safety rules. Here is how live and official APIs are integrated:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF334155)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "1. YouTube Data API v3 & Embeds",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "• Uses official YouTube iframe player API and privacy-enhanced domain (youtube-nocookie.com).\n• To fetch custom live playlists, add YOUTUBE_API_KEY in AI Studio Secrets or BuildConfig.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "2. Khan Academy & Coursera / edX",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "• Content references public lesson metadata and links directly to official domains.\n• Never downloads or re-hosts copyrighted course files.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "3. Zero Private Data Collection",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "• All student progress (streaks, challenges) is saved strictly on the local device via Room Database.\n• No tracking, no personal information asked.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = SunriseAmber)
            ) {
                Text("Understood")
            }
        }
    )
}
