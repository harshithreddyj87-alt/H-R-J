package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.QuizSession
import com.example.ui.theme.GrowthGreen
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunGold
import com.example.ui.theme.SunriseAmber
import com.example.ui.theme.SunriseOrange

@Composable
fun QuizDialog(
    quizSession: QuizSession,
    onSelectOption: (Int) -> Unit,
    onCheckAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(14.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = SunriseAmber.copy(alpha = 0.15f),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = null,
                                    tint = SunriseOrange,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "1-Hour Knowledge Check",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = if (quizSession.watchedVideosCountInLastHour > 0) {
                                    "Based on ${quizSession.watchedVideosCountInLastHour} video(s) watched in the last hour"
                                } else {
                                    "Refresher Quiz from Rise & Shine lessons"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = if (quizSession.watchedVideosCountInLastHour > 0) GrowthGreen else Color(0xFF64748B),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_quiz_btn")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close Quiz")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (quizSession.isFinished) {
                    // Result Summary Card
                    QuizResultView(
                        quizSession = quizSession,
                        onRestartQuiz = onRestartQuiz,
                        onDismiss = onDismiss
                    )
                } else {
                    // Active Question Screen
                    val totalQ = quizSession.questions.size
                    val currentIndex = quizSession.currentQuestionIndex
                    val currentQuestion = quizSession.questions.getOrNull(currentIndex)

                    if (currentQuestion != null) {
                        // Progress Bar
                        val progressFraction = (currentIndex + 1).toFloat() / totalQ.toFloat()
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Question ${currentIndex + 1} of $totalQ",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SunriseOrange
                                )
                                Text(
                                    text = "Score: ${quizSession.correctAnswersCount}/$currentIndex",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF64748B)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { progressFraction },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = SunriseOrange,
                                trackColor = Color(0xFFF1F5F9),
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Source Content Attribution Badge
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFFF3E0),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🎥", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "From watched lesson: ${currentQuestion.contentTitle}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFD84315),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Question Card
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFF8FAFC))
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = currentQuestion.questionText,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A),
                                    lineHeight = 24.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Options
                        currentQuestion.options.forEachIndexed { index, optionText ->
                            val isSelected = quizSession.selectedOptionIndex == index
                            val isCorrect = index == currentQuestion.correctOptionIndex
                            val isChecked = quizSession.isAnswerChecked

                            val (bgColor, borderColor, textColor) = when {
                                isChecked && isCorrect -> Triple(Color(0xFFE8F5E9), GrowthGreen, Color(0xFF1B5E20))
                                isChecked && isSelected && !isCorrect -> Triple(Color(0xFFFFEBEE), Color(0xFFD32F2F), Color(0xFFC62828))
                                isSelected -> Triple(Color(0xFFFFF8E1), SunriseOrange, Color(0xFF0F172A))
                                else -> Triple(Color.White, Color(0xFFE2E8F0), Color(0xFF334155))
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .clickable(enabled = !isChecked) { onSelectOption(index) }
                                    .testTag("quiz_option_$index"),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = bgColor),
                                border = BorderStroke(if (isSelected || (isChecked && isCorrect)) 2.dp else 1.dp, borderColor)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = if (isSelected || (isChecked && isCorrect)) borderColor else Color(0xFFF1F5F9),
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = ('A' + index).toString(),
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected || (isChecked && isCorrect)) Color.White else Color(0xFF64748B)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = optionText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = textColor,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (isChecked) {
                                        if (isCorrect) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Correct",
                                                tint = GrowthGreen,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        } else if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Incorrect",
                                                tint = Color(0xFFD32F2F),
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Explanation Banner after answer checked
                        if (quizSession.isAnswerChecked) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (quizSession.selectedOptionIndex == currentQuestion.correctOptionIndex) Color(0xFFE8F5E9) else Color(0xFFFFF3E0),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Lightbulb,
                                            contentDescription = null,
                                            tint = if (quizSession.selectedOptionIndex == currentQuestion.correctOptionIndex) GrowthGreen else SunriseOrange,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (quizSession.selectedOptionIndex == currentQuestion.correctOptionIndex) "Great Job! Key Takeaway" else "Learning Moment",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = if (quizSession.selectedOptionIndex == currentQuestion.correctOptionIndex) GrowthGreen else SunriseOrange
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = currentQuestion.explanation,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF1E293B),
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Bottom Action Button
                        if (!quizSession.isAnswerChecked) {
                            Button(
                                onClick = onCheckAnswer,
                                enabled = quizSession.selectedOptionIndex != null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("submit_quiz_answer_btn"),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SunriseAmber)
                            ) {
                                Text("Check Answer", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Button(
                                onClick = onNextQuestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("next_quiz_question_btn"),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = GrowthGreen)
                            ) {
                                Text(
                                    text = if (currentIndex + 1 < totalQ) "Next Question →" else "See Quiz Results 🏆",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizResultView(
    quizSession: QuizSession,
    onRestartQuiz: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val total = quizSession.questions.size
    val correct = quizSession.correctAnswersCount
    val percentage = if (total > 0) (correct.toFloat() / total.toFloat()) * 100f else 0f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = if (percentage >= 60f) Color(0xFFFFF8E1) else Color(0xFFE1F5FE),
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = if (percentage >= 80f) "🌟" else if (percentage >= 50f) "👍" else "🌱",
                    fontSize = 42.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = if (percentage >= 80f) "Outstanding Mastery!" else if (percentage >= 50f) "Well Done!" else "Keep Learning!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "You answered $correct out of $total questions correctly (${percentage.toInt()}%).",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF475569)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress bonus card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFE8F5E9),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = GrowthGreen,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "+1 Lesson Completed Added!",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = GrowthGreen
                    )
                    Text(
                        text = "Your curiosity and active listening build real intelligence.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onRestartQuiz,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("retake_quiz_btn"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Retake")
            }

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("finish_quiz_btn"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SunriseAmber)
            ) {
                Text("Done", fontWeight = FontWeight.Bold)
            }
        }
    }
}
