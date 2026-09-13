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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import com.example.data.local.StudentProgressEntity
import com.example.ui.theme.GrowthGreen
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunGold
import com.example.ui.theme.SunriseAmber
import com.example.ui.theme.SunriseOrange

@Composable
fun ProgressScreen(
    progress: StudentProgressEntity,
    isTodayChallengeCompleted: Boolean,
    watchedLastHourCount: Int,
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("progress_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(SunriseOrange, SunriseAmber, SunGold)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "My Learning Journey",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Personal growth step-by-step",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }

                            // Streak Badge
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color.White.copy(alpha = 0.25f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Streak",
                                        tint = Color.Yellow,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${progress.streakDays} Days",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Black.copy(alpha = 0.2f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🌱", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = if (isTodayChallengeCompleted) {
                                        "You completed today's challenge! Your daily habit is growing stronger."
                                    } else {
                                        "Remember to complete today's positive challenge on the Home tab!"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quiz on Last 1h Videos Card
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("progress_quiz_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFFF3E0),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🧠", fontSize = 22.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "1-Hour Video Quiz",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = if (watchedLastHourCount > 0) {
                                    "$watchedLastHourCount video(s) watched in the past hour"
                                } else {
                                    "Test your recall on recent video lessons"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    androidx.compose.material3.Button(
                        onClick = onStartQuiz,
                        shape = RoundedCornerShape(12.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = SunriseOrange),
                        modifier = Modifier.testTag("progress_take_quiz_btn")
                    ) {
                        Text("Start Quiz", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Stats Grid
        item {
            Text(
                text = "Tracked Achievements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Videos Watched",
                    count = progress.totalVideosWatched,
                    icon = Icons.Default.OndemandVideo,
                    tint = SunriseOrange,
                    bgColor = Color(0xFFFFF3E0),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Lessons Done",
                    count = progress.totalLessonsCompleted,
                    icon = Icons.Default.School,
                    tint = SkyBlue,
                    bgColor = Color(0xFFE1F5FE),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Stories Read",
                    count = progress.totalStoriesRead,
                    icon = Icons.Default.MenuBook,
                    tint = GrowthGreen,
                    bgColor = Color(0xFFE8F5E9),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Daily Challenges",
                    count = progress.totalChallengesCompleted,
                    icon = Icons.Default.CheckCircle,
                    tint = Color(0xFF6A1B9A),
                    bgColor = Color(0xFFF3E5F5),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Positive Growth Badges
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Character & Habit Badges",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )
        }

        item {
            BadgeCard(
                emoji = "🚀",
                title = "Curious Mind",
                description = "Exploring mathematics, space, and science every week.",
                isUnlocked = progress.totalVideosWatched >= 3
            )
        }

        item {
            BadgeCard(
                emoji = "⭐",
                title = "Consistency Champion",
                description = "Maintained a positive learning streak for 3+ days.",
                isUnlocked = progress.streakDays >= 3
            )
        }

        item {
            BadgeCard(
                emoji = "🤝",
                title = "Values & Kindness",
                description = "Read moral stories and practiced helping others.",
                isUnlocked = progress.totalStoriesRead >= 2
            )
        }

        // Healthy Mindset Note
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFF8FAFC),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SunriseAmber)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "A Note from Rise & Shine",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "We never compare students with each other. Real success is competing with yourself: becoming a little kinder, wiser, and more curious than you were yesterday!",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatCard(
    title: String,
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = bgColor,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$count",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF64748B),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun BadgeCard(
    emoji: String,
    title: String,
    description: String,
    isUnlocked: Boolean,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = if (isUnlocked) Color(0xFFFFF8E1) else Color(0xFFF1F5F9),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = if (isUnlocked) emoji else "🔒",
                        fontSize = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) Color(0xFF1E293B) else Color(0xFF94A3B8)
                    )
                    if (isUnlocked) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = CircleShape,
                            color = GrowthGreen
                        ) {
                            Text(
                                text = "Active",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}
