package com.example.data.repository

import com.example.data.local.CompletedChallengeEntity
import com.example.data.local.ProgressDao
import com.example.data.local.StudentProgressEntity
import com.example.data.local.WatchedItemEntity
import com.example.data.model.ContentCategory
import com.example.data.model.ContentItem
import com.example.data.model.ContentReport
import com.example.data.model.DailyMotivation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ContentRepository(private val progressDao: ProgressDao) {

    // Curated child-friendly educational and motivational catalog
    private val contentItems: List<ContentItem> = listOf(
        // ----------------- MOTIVATION -----------------
        ContentItem(
            id = "mot_01",
            title = "The Power of Believing You Can Improve",
            description = "Carol Dweck explains the 'Growth Mindset' and how learning from mistakes makes your brain stronger and smarter.",
            category = ContentCategory.MOTIVATION,
            topic = "Confidence",
            sourceName = "TED Talks",
            sourcePlatform = "TED",
            duration = "10 mins",
            youtubeVideoId = "_X0mgOOSpLU",
            webUrl = "https://www.ted.com/talks/carol_dweck_the_power_of_believing_that_you_can_improve",
            moralOrKeyLesson = "You haven't failed; you just haven't succeeded 'YET'. Your brain grows every time you solve a tough challenge.",
            isRecommended = true,
            tags = listOf("mindset", "confidence", "hard work", "learning", "growth")
        ),
        ContentItem(
            id = "mot_02",
            title = "Small Habits, Big Results: Atomic Habits for Students",
            description = "How improving just 1% every single day compounds into extraordinary knowledge and personal mastery over the school year.",
            category = ContentCategory.MOTIVATION,
            topic = "Building good habits",
            sourceName = "The School of Greatness",
            sourcePlatform = "YouTube",
            duration = "12 mins",
            youtubeVideoId = "U_nzqnXWvSo",
            webUrl = "https://lewishowes.com/school-of-greatness",
            moralOrKeyLesson = "Discipline is choosing what you want most over what you want now. Focus on daily systems rather than only distant goals.",
            isRecommended = true,
            tags = listOf("habits", "discipline", "goals", "routine", "study tips")
        ),
        ContentItem(
            id = "mot_03",
            title = "Grit: The Power of Passion and Perseverance",
            description = "Psychologist Angela Lee Duckworth proves that grit—perseverance and long-term passion—is a greater predictor of student success than raw talent.",
            category = ContentCategory.MOTIVATION,
            topic = "Hard work",
            sourceName = "TED / TED-Ed",
            sourcePlatform = "TED",
            duration = "6 mins",
            youtubeVideoId = "H14bBuluwB8",
            webUrl = "https://www.ted.com/talks/angela_lee_duckworth_grit_the_power_of_passion_and_perseverance",
            moralOrKeyLesson = "Talent alone doesn't make you gritty. Effort counts twice as much as natural ability!",
            tags = listOf("grit", "hard work", "success", "perseverance", "discipline")
        ),
        ContentItem(
            id = "mot_04",
            title = "A.P.J. Abdul Kalam's Inspiring Message to Youth",
            description = "Former President of India and missile scientist Dr. Kalam shares his four golden rules for students: great aim, continuous knowledge, hard work, and perseverance.",
            category = ContentCategory.MOTIVATION,
            topic = "Dreams and goals",
            sourceName = "Goalcast & Public Address",
            sourcePlatform = "Goalcast",
            duration = "8 mins",
            youtubeVideoId = "laGZaS4sdeU",
            webUrl = "https://www.goalcast.com",
            moralOrKeyLesson = "Dream is not that which you see while sleeping; it is something that does not let you sleep.",
            isRecommended = true,
            tags = listOf("kalam", "dreams", "goals", "india", "leadership", "science")
        ),
        ContentItem(
            id = "mot_05",
            title = "Mastering Time: How to Stop Procrastinating",
            description = "Tim Urban's famous, funny, and deeply insightful exploration of the 'Procrastination Monkey' and how students can take control of their homework schedules.",
            category = ContentCategory.MOTIVATION,
            topic = "Time management",
            sourceName = "TED Talks",
            sourcePlatform = "TED",
            duration = "14 mins",
            youtubeVideoId = "arj7oStGLkU",
            webUrl = "https://www.ted.com/talks/tim_urban_inside_the_mind_of_a_master_procrastinator",
            moralOrKeyLesson = "Break huge tasks into 15-minute bite-sized steps so the procrastination urge loses its power.",
            tags = listOf("time management", "procrastination", "focus", "homework", "study tips")
        ),
        ContentItem(
            id = "mot_06",
            title = "Why Helping Others is the Greatest Superpower",
            description = "Heartwarming stories showing how young volunteers transformed their local schools and communities by simple acts of daily kindness.",
            category = ContentCategory.MOTIVATION,
            topic = "Helping others",
            sourceName = "Goalcast Youth",
            sourcePlatform = "Goalcast",
            duration = "7 mins",
            youtubeVideoId = "O1yZ7h9d1s4",
            webUrl = "https://www.goalcast.com",
            moralOrKeyLesson = "True leadership is measured not by how many serve you, but by how many people you uplift.",
            tags = listOf("helping others", "kindness", "leadership", "service", "values")
        ),

        // ----------------- EDUCATION -----------------
        ContentItem(
            id = "edu_01",
            title = "Why is Pi Never-Ending? The Magic of Mathematics",
            description = "Explore the cosmic mystery of Pi (3.14159...), geometry circles, and how ancient mathematicians calculated the stars using ratios.",
            category = ContentCategory.EDUCATION,
            topic = "Mathematics",
            sourceName = "Khan Academy",
            sourcePlatform = "Khan Academy",
            duration = "9 mins",
            youtubeVideoId = "yJ-HwrOpIps",
            webUrl = "https://www.khanacademy.org/math/geometry",
            moralOrKeyLesson = "Math isn't just numbers on a blackboard; it is the universal language describing the shape of nature and space.",
            isRecommended = true,
            tags = listOf("mathematics", "math", "geometry", "pi", "algebra", "numbers")
        ),
        ContentItem(
            id = "edu_02",
            title = "What is Inside an Atom? Subatomic Physics",
            description = "Journey inside protons, neutrons, electrons, and the quantum empty space that makes up everything in our universe.",
            category = ContentCategory.EDUCATION,
            topic = "Science",
            sourceName = "Crash Course Kids",
            sourcePlatform = "Crash Course",
            duration = "8 mins",
            youtubeVideoId = "cpBb2bgFO6I",
            webUrl = "https://thecrashcourse.com",
            moralOrKeyLesson = "Curiosity is the engine of science. Every giant invention started with asking 'What happens inside?'",
            isRecommended = true,
            tags = listOf("science", "physics", "chemistry", "atoms", "space", "stem")
        ),
        ContentItem(
            id = "edu_03",
            title = "How Computers Actually Think: Binary & Logic Gates",
            description = "A beginner-friendly demonstration of 1s and 0s, transistors, algorithms, and how code turns electricity into video games and rockets.",
            category = ContentCategory.EDUCATION,
            topic = "Computer science",
            sourceName = "Crash Course Computer Science",
            sourcePlatform = "Crash Course",
            duration = "11 mins",
            youtubeVideoId = "tpIctyqH29Q",
            webUrl = "https://thecrashcourse.com/courses/computerscience",
            moralOrKeyLesson = "Programming teaches you how to think. When something doesn't work, debugging is simply detective work.",
            tags = listOf("computer science", "coding", "software", "technology", "algorithms")
        ),
        ContentItem(
            id = "edu_04",
            title = "Journey to Mars and Beyond: Space Exploration",
            description = "How ISRO's Mangalyaan and NASA's Perseverance Rover travel millions of kilometers through cold space to find signs of ancient water.",
            category = ContentCategory.EDUCATION,
            topic = "Space",
            sourceName = "TED-Ed",
            sourcePlatform = "TED-Ed",
            duration = "7 mins",
            youtubeVideoId = "gXN_8p0Fh9I",
            webUrl = "https://ed.ted.com",
            moralOrKeyLesson = "Frugal engineering and dedicated teamwork can send a robotic laboratory across interplanetary space.",
            tags = listOf("space", "astronomy", "isro", "nasa", "mars", "science")
        ),
        ContentItem(
            id = "edu_05",
            title = "Secrets of Mastering English Vocabulary and Speaking",
            description = "Proven techniques to build strong English reading comprehension, public speaking confidence, and active vocabulary effortlessly.",
            category = ContentCategory.EDUCATION,
            topic = "English",
            sourceName = "Khan Academy Language",
            sourcePlatform = "Khan Academy",
            duration = "10 mins",
            youtubeVideoId = "d0yGdNEWdn0",
            webUrl = "https://www.khanacademy.org",
            moralOrKeyLesson = "Reading one book chapter every night expands your vocabulary by over 1,000 words each month.",
            tags = listOf("english", "vocabulary", "communication", "reading", "grammar")
        ),
        ContentItem(
            id = "edu_06",
            title = "Climate, Rainforests, and Protecting Our Planet",
            description = "Discover the interconnected water cycles, atmospheric carbon capture, and how school eco-clubs plant native trees to restore greenery.",
            category = ContentCategory.EDUCATION,
            topic = "Environment",
            sourceName = "TED-Ed",
            sourcePlatform = "TED-Ed",
            duration = "8 mins",
            youtubeVideoId = "B-nEYsyRlYo",
            webUrl = "https://ed.ted.com",
            moralOrKeyLesson = "We do not inherit the Earth from our ancestors; we borrow it from our children. Every tree counts.",
            tags = listOf("environment", "geography", "earth", "climate", "nature")
        ),

        // ----------------- BUSINESS & CAREERS -----------------
        ContentItem(
            id = "car_01",
            title = "How ISRO Scientists Built the Chandrayaan Mission",
            description = "Meet the lead scientists and engineers behind India's historic Moon landing. Learn what subjects to study in school to become a space engineer.",
            category = ContentCategory.CAREERS,
            topic = "Scientists",
            careerRole = "Aerospace & Space Scientist",
            sourceName = "The School of Greatness & Public ISRO",
            sourcePlatform = "YouTube",
            duration = "15 mins",
            youtubeVideoId = "7mZc9LgM7uY",
            webUrl = "https://www.isro.gov.in",
            moralOrKeyLesson = "When Chandrayaan-2 faced a setback, the team didn't give up. They analyzed every millisecond of telemetry to triumph in Chandrayaan-3.",
            isRecommended = true,
            tags = listOf("scientists", "isro", "aerospace", "engineering", "careers", "space")
        ),
        ContentItem(
            id = "car_02",
            title = "A Day in the Life of a Pediatrician (Doctor)",
            description = "Dr. Anita explains how doctors diagnose illnesses, calm young patients, and what the medical journey looks like from Class 10 to MBBS.",
            category = ContentCategory.CAREERS,
            topic = "Doctors",
            careerRole = "Pediatric Doctor / Surgeon",
            sourceName = "Careers Today",
            sourcePlatform = "YouTube",
            duration = "12 mins",
            youtubeVideoId = "PZ7wZgerC10",
            webUrl = "https://www.youtube.com",
            moralOrKeyLesson = "Medicine is science fueled by deep empathy. Saving lives requires both sharp medical intellect and a caring heart.",
            tags = listOf("doctors", "medicine", "biology", "healthcare", "careers")
        ),
        ContentItem(
            id = "car_03",
            title = "From Idea to Startup: Young Tech Entrepreneurs",
            description = "How teen founders solved waste management and student tutoring by building simple mobile apps and finding their first mentors.",
            category = ContentCategory.CAREERS,
            topic = "Entrepreneurs",
            careerRole = "Tech Founder & Product Designer",
            sourceName = "TEDx Youth",
            sourcePlatform = "TEDx",
            duration = "11 mins",
            youtubeVideoId = "v1uyQZNg2vE",
            webUrl = "https://www.ted.com/tedx",
            moralOrKeyLesson = "Entrepreneurship isn't about making money fast; it is about finding a real problem in your neighborhood and solving it tenaciously.",
            isRecommended = true,
            tags = listOf("entrepreneurs", "startups", "business", "innovation", "leadership")
        ),
        ContentItem(
            id = "car_04",
            title = "How Authors Write Bestselling Adventure Novels",
            description = "Interview with renowned children's author discussing character building, overcoming writer's block, and turning bedtime thoughts into published books.",
            category = ContentCategory.CAREERS,
            topic = "Authors",
            careerRole = "Novelist & Creative Writer",
            sourceName = "The School of Greatness",
            sourcePlatform = "YouTube",
            duration = "13 mins",
            youtubeVideoId = "W2nU0eQWv74",
            webUrl = "https://lewishowes.com",
            moralOrKeyLesson = "You don't start out writing great chapters; you start out writing rough drafts and polishing them with patience.",
            tags = listOf("authors", "writing", "creativity", "literature", "english")
        ),
        ContentItem(
            id = "car_05",
            title = "Mindset of Champions: Olympic Gold Medalists",
            description = "Olympic athletes break down their 5:00 AM training routine, healthy nutrition, and overcoming physical injuries to represent their nation.",
            category = ContentCategory.CAREERS,
            topic = "Sportspersons",
            careerRole = "Professional Athlete & Sports Coach",
            sourceName = "Goalcast Sports",
            sourcePlatform = "Goalcast",
            duration = "9 mins",
            youtubeVideoId = "8dJRZ4R5w2U",
            webUrl = "https://www.goalcast.com",
            moralOrKeyLesson = "Champions are not made in the ring; they are made from something deep inside them: a desire, a dream, a vision.",
            tags = listOf("sportspersons", "athletes", "fitness", "discipline", "sports")
        ),

        // ----------------- MORAL STORIES -----------------
        ContentItem(
            id = "mor_01",
            title = "The Golden River of Truth",
            description = "Two woodcutters lose their axes in a deep mountain river. The Spirit of the River tests their honesty by offering silver and gold axes first.",
            category = ContentCategory.MORAL_STORIES,
            topic = "Moral stories",
            sourceName = "MoralStories.org",
            sourcePlatform = "MoralStories.org",
            duration = "5 min read",
            webUrl = "https://www.moralstories.org/the-honest-woodcutter",
            moralOrKeyLesson = "Honesty is the richest treasure you can ever carry. A clean conscience brings lasting blessings that gold cannot buy.",
            isRecommended = true,
            tags = listOf("moral stories", "honesty", "values", "character", "integrity")
        ),
        ContentItem(
            id = "mor_02",
            title = "The Bundle of Sticks: Strength in Unity",
            description = "An elder father calls his quarreling sons together and challenges each to snap a single twig, and then a tightly tied bundle of twenty sticks.",
            category = ContentCategory.MORAL_STORIES,
            topic = "Moral stories",
            sourceName = "Storyberries",
            sourcePlatform = "Storyberries",
            duration = "4 min read",
            webUrl = "https://www.storyberries.com",
            moralOrKeyLesson = "United we stand, divided we fall. When classmates and families cooperate, no storm can break them.",
            isRecommended = true,
            tags = listOf("moral stories", "unity", "teamwork", "family", "friendship")
        ),
        ContentItem(
            id = "mor_03",
            title = "The Two Travelers and the Bear",
            description = "Two friends hiking in a dense forest encounter a hungry brown bear. One climbs high into a tree leaving his friend behind.",
            category = ContentCategory.MORAL_STORIES,
            topic = "Moral stories",
            sourceName = "Superbook Children Stories",
            sourcePlatform = "Superbook",
            duration = "6 mins",
            youtubeVideoId = "E7n2s-6Jz3A",
            webUrl = "https://us-en.superbook.cbn.com",
            moralOrKeyLesson = "A friend in need is a friend indeed. Misfortune tests the sincerity of friendship.",
            tags = listOf("moral stories", "friendship", "loyalty", "superbook", "values")
        ),
        ContentItem(
            id = "mor_04",
            title = "The Cracked Pot: Finding Purpose in Imperfection",
            description = "An ancient water bearer carried two clay pots. One had a tiny crack that leaked water along the footpath, while the other was proud and perfect.",
            category = ContentCategory.MORAL_STORIES,
            topic = "Moral stories",
            sourceName = "MoralStories.org",
            sourcePlatform = "MoralStories.org",
            duration = "5 min read",
            webUrl = "https://www.moralstories.org",
            moralOrKeyLesson = "Our unique quirks and flaws can water beautiful wild flowers on the path of life. Value everyone as they are.",
            tags = listOf("moral stories", "acceptance", "purpose", "kindness", "empathy")
        ),

        // ----------------- LIVE VIDEOS -----------------
        ContentItem(
            id = "live_01",
            title = "NASA Space Station Earth Views: Live Stream",
            description = "Watch high-definition live views of Earth from the International Space Station traveling at 28,000 km/h with live astronaut educational telemetry.",
            category = ContentCategory.LIVE,
            topic = "Space",
            sourceName = "NASA Live Broadcast",
            sourcePlatform = "YouTube Live",
            duration = "Live Now",
            youtubeVideoId = "21X5lGlDOfg",
            webUrl = "https://www.youtube.com/live",
            isLive = true,
            isRecommended = true,
            moralOrKeyLesson = "From space, there are no borders—only one fragile, breathtaking blue oasis that we must protect together.",
            tags = listOf("live", "space", "earth", "nasa", "science")
        ),
        ContentItem(
            id = "live_02",
            title = "Live Science Experiment Lab: Chemistry & Physics",
            description = "Live interactive laboratory showcasing safe physics demonstrations, liquid nitrogen, vortex rings, and Q&A for high school students.",
            category = ContentCategory.LIVE,
            topic = "Science",
            sourceName = "Khan Academy Live STEM",
            sourcePlatform = "YouTube Live",
            duration = "Live Broadcast",
            youtubeVideoId = "9FqwhW0B3tY",
            webUrl = "https://www.khanacademy.org",
            isLive = true,
            moralOrKeyLesson = "Scientific inquiry turns everyday household curiosities into world-changing discoveries.",
            tags = listOf("live", "experiments", "science", "stem", "interactive")
        ),
        ContentItem(
            id = "live_03",
            title = "Global Student Study Hall & Focus Lounge",
            description = "Join students worldwide in a silent, calm pomodoro study session with gentle lofi ambient sounds, goal check-ins, and study timer.",
            category = ContentCategory.LIVE,
            topic = "Time management",
            sourceName = "Lofi Girl Edu Stream",
            sourcePlatform = "YouTube Live",
            duration = "Live 24/7",
            youtubeVideoId = "jfKfPfyJRdk",
            webUrl = "https://www.youtube.com/live",
            isLive = true,
            moralOrKeyLesson = "A calm environment and structured 25-minute study intervals double your homework retention.",
            tags = listOf("live", "study tips", "focus", "pomodoro", "homework")
        )
    )

    // Daily Motivations rotation
    private val dailyMotivations: List<DailyMotivation> = listOf(
        DailyMotivation(
            quote = "You cannot change your future, but you can change your habits, and surely your habits will change your future.",
            author = "Dr. A.P.J. Abdul Kalam",
            recommendedContentId = "mot_04",
            positiveChallenge = "Read for 20 minutes",
            challengeDescription = "Pick up a book, newspaper, or educational article and read attentively for 20 minutes without looking at any screen distractions."
        ),
        DailyMotivation(
            quote = "It does not matter how slowly you go as long as you do not stop.",
            author = "Confucius",
            recommendedContentId = "mot_01",
            positiveChallenge = "Help someone today",
            challengeDescription = "Offer a genuine helping hand to a classmate, teacher, or your parents at home without being asked."
        ),
        DailyMotivation(
            quote = "The expert in anything was once a beginner who refused to quit.",
            author = "Helen Hayes",
            recommendedContentId = "edu_01",
            positiveChallenge = "Learn something new",
            challengeDescription = "Discover one fascinating fact in math, space, or biology today and share it with a friend or family member."
        ),
        DailyMotivation(
            quote = "Education is the most powerful weapon which you can use to change the world.",
            author = "Nelson Mandela",
            recommendedContentId = "car_01",
            positiveChallenge = "Practice a skill",
            challengeDescription = "Spend 15 minutes practicing drawing, mental arithmetic, handwriting, or a musical instrument."
        ),
        DailyMotivation(
            quote = "Set your goals high, and don't stop till you get there.",
            author = "Bo Jackson",
            recommendedContentId = "mot_02",
            positiveChallenge = "Write down your goal",
            challengeDescription = "Take a notebook and write down 3 specific milestones you want to achieve this school term."
        )
    )

    fun getAllContent(): List<ContentItem> = contentItems

    fun getContentByCategory(category: ContentCategory): List<ContentItem> {
        return if (category == ContentCategory.ALL) {
            contentItems
        } else {
            contentItems.filter { it.category == category }
        }
    }

    fun getContentById(id: String): ContentItem? {
        return contentItems.find { it.id == id }
    }

    fun getRecommendedContent(): List<ContentItem> {
        return contentItems.filter { it.isRecommended }
    }

    fun getLiveContent(): List<ContentItem> {
        return contentItems.filter { it.isLive || it.category == ContentCategory.LIVE }
    }

    fun searchContent(query: String, category: ContentCategory = ContentCategory.ALL): List<ContentItem> {
        val trimmed = query.trim().lowercase(Locale.ROOT)
        val baseList = if (category == ContentCategory.ALL) contentItems else contentItems.filter { it.category == category }
        if (trimmed.isEmpty()) return baseList

        return baseList.filter { item ->
            item.title.lowercase(Locale.ROOT).contains(trimmed) ||
            item.description.lowercase(Locale.ROOT).contains(trimmed) ||
            item.topic.lowercase(Locale.ROOT).contains(trimmed) ||
            item.sourceName.lowercase(Locale.ROOT).contains(trimmed) ||
            item.sourcePlatform.lowercase(Locale.ROOT).contains(trimmed) ||
            (item.careerRole?.lowercase(Locale.ROOT)?.contains(trimmed) == true) ||
            item.tags.any { it.lowercase(Locale.ROOT).contains(trimmed) }
        }
    }

    fun getTodayMotivation(): DailyMotivation {
        val dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR)
        val index = dayOfYear % dailyMotivations.size
        return dailyMotivations[index]
    }

    fun getTodayDateString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    // Room DB integration methods
    fun getProgressFlow(): Flow<StudentProgressEntity?> = progressDao.getProgress()

    fun isItemWatchedFlow(contentId: String): Flow<Boolean> = progressDao.isItemWatched(contentId)

    fun isTodayChallengeCompletedFlow(): Flow<Boolean> {
        return progressDao.isChallengeCompletedToday(getTodayDateString())
    }

    suspend fun markContentAsWatched(content: ContentItem) {
        val watchedEntity = WatchedItemEntity(
            contentId = content.id,
            title = content.title,
            category = content.category.displayName,
            watchedTimestamp = System.currentTimeMillis()
        )
        progressDao.markWatched(watchedEntity)

        // Update counts in progress entity
        val currentProgress = progressDao.getProgress().firstOrNull() ?: StudentProgressEntity()
        val newVideos = if (content.category != ContentCategory.MORAL_STORIES) {
            currentProgress.totalVideosWatched + 1
        } else {
            currentProgress.totalVideosWatched
        }
        val newStories = if (content.category == ContentCategory.MORAL_STORIES) {
            currentProgress.totalStoriesRead + 1
        } else {
            currentProgress.totalStoriesRead
        }
        val newLessons = if (content.category == ContentCategory.EDUCATION) {
            currentProgress.totalLessonsCompleted + 1
        } else {
            currentProgress.totalLessonsCompleted
        }

        progressDao.insertOrUpdateProgress(
            currentProgress.copy(
                totalVideosWatched = newVideos,
                totalStoriesRead = newStories,
                totalLessonsCompleted = newLessons
            )
        )
    }

    suspend fun completeTodayChallenge(challengeText: String) {
        val today = getTodayDateString()
        progressDao.markChallengeCompleted(
            CompletedChallengeEntity(
                challengeDate = today,
                challengeText = challengeText
            )
        )

        val currentProgress = progressDao.getProgress().firstOrNull() ?: StudentProgressEntity()
        val newStreak = if (currentProgress.lastChallengeDate != today) {
            currentProgress.streakDays + 1
        } else {
            currentProgress.streakDays
        }

        progressDao.insertOrUpdateProgress(
            currentProgress.copy(
                streakDays = newStreak,
                lastChallengeDate = today,
                totalChallengesCompleted = currentProgress.totalChallengesCompleted + 1
            )
        )
    }

    // Quiz Questions Catalog mapped by Content ID
    private val quizQuestionsCatalog: Map<String, List<com.example.data.model.QuizQuestion>> = mapOf(
        "mot_01" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_mot_01_1",
                contentId = "mot_01",
                contentTitle = "The Power of Believing You Can Improve",
                questionText = "According to Carol Dweck, what magic word transforms 'I don't understand this' into a growth mindset?",
                options = listOf("Never", "Yet", "Always", "Later"),
                correctOptionIndex = 1,
                explanation = "Adding 'YET' reminds us that our brain develops new neural pathways every time we practice difficult challenges."
            ),
            com.example.data.model.QuizQuestion(
                id = "q_mot_01_2",
                contentId = "mot_01",
                contentTitle = "The Power of Believing You Can Improve",
                questionText = "What actually happens to your brain when you work through tough mistakes?",
                options = listOf("It shrinks", "It makes new connections and grows stronger", "It loses energy", "Nothing happens"),
                correctOptionIndex = 1,
                explanation = "Neuroplasticity allows neurons to form strong new connections whenever you push through a learning difficulty."
            )
        ),
        "mot_02" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_mot_02_1",
                contentId = "mot_02",
                contentTitle = "Small Habits, Big Results",
                questionText = "In Atomic Habits, how much improvement every day leads to 37 times better results in a year?",
                options = listOf("50%", "10%", "1%", "100%"),
                correctOptionIndex = 2,
                explanation = "Improving just 1% every day compounds into remarkable progress over 365 days!"
            )
        ),
        "mot_03" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_mot_03_1",
                contentId = "mot_03",
                contentTitle = "Grit: Passion & Perseverance",
                questionText = "What did psychologist Angela Duckworth discover is a stronger predictor of student success than natural talent?",
                options = listOf("Grit and perseverance", "Luck", "Expensive equipment", "Sleeping more"),
                correctOptionIndex = 0,
                explanation = "Grit—sticking with long-term goals despite setbacks—is the true engine of achievement."
            )
        ),
        "mot_04" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_mot_04_1",
                contentId = "mot_04",
                contentTitle = "Dr. Kalam's Inspiring Message",
                questionText = "What did Dr. A.P.J. Abdul Kalam famously say about real dreams?",
                options = listOf(
                    "Dreams only happen when you are asleep",
                    "Dreams are what you see while resting",
                    "Dreams are not what you see in sleep; they don't let you sleep",
                    "Dreams are impossible to reach"
                ),
                correctOptionIndex = 2,
                explanation = "Dr. Kalam taught that genuine dreams awaken a deep passion to learn, study, and persevere every single day."
            )
        ),
        "mot_05" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_mot_05_1",
                contentId = "mot_05",
                contentTitle = "Mastering Time & Procrastination",
                questionText = "What is the best way to defeat the urge to procrastinate on big school assignments?",
                options = listOf(
                    "Wait until midnight before the deadline",
                    "Break the task into a small, focused 15-minute start",
                    "Ignore it until tomorrow",
                    "Complain about the teacher"
                ),
                correctOptionIndex = 1,
                explanation = "Taking the first 15-minute step breaks the resistance barrier and creates momentum."
            )
        ),
        "mot_06" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_mot_06_1",
                contentId = "mot_06",
                contentTitle = "Helping Others",
                questionText = "How is true leadership measured according to inspiring youth leaders?",
                options = listOf(
                    "By having the loudest voice",
                    "By how many people you uplift and support",
                    "By winning every single debate",
                    "By working completely alone"
                ),
                correctOptionIndex = 1,
                explanation = "True leadership and character shine when you lift others up and cooperate with classmates."
            )
        ),
        "edu_01" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_edu_01_1",
                contentId = "edu_01",
                contentTitle = "The Magic of Mathematics & Pi",
                questionText = "Pi (π) represents the ratio of a circle's circumference to its:",
                options = listOf("Radius", "Diameter", "Area", "Arc length"),
                correctOptionIndex = 1,
                explanation = "Pi is the ratio of any circle's circumference to its diameter (approximately 3.14159)."
            )
        ),
        "edu_02" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_edu_02_1",
                contentId = "edu_02",
                contentTitle = "Inside the Atom",
                questionText = "Which subatomic particles are located together inside the nucleus of an atom?",
                options = listOf(
                    "Electrons and photons",
                    "Protons and neutrons",
                    "Neutrons and electrons",
                    "Molecules and ions"
                ),
                correctOptionIndex = 1,
                explanation = "The heavy nucleus contains positively charged protons and neutral neutrons, surrounded by electron shells."
            )
        ),
        "edu_03" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_edu_03_1",
                contentId = "edu_03",
                contentTitle = "How Computers Think: Binary",
                questionText = "What two numbers make up the entire language of binary code inside computer microchips?",
                options = listOf("1 and 2", "0 and 1", "0 and 9", "1 and 10"),
                correctOptionIndex = 1,
                explanation = "Microscopic transistors represent electronic ON (1) and OFF (0) states to compute complex operations."
            )
        ),
        "edu_04" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_edu_04_1",
                contentId = "edu_04",
                contentTitle = "Journey to Mars & Space",
                questionText = "What was the landmark accomplishment of ISRO's Mangalyaan (Mars Orbiter Mission)?",
                options = listOf(
                    "It reached Mars orbit on its very first attempt at an extraordinary low cost",
                    "It landed humans on Mars",
                    "It built a permanent Mars colony",
                    "It brought back Martian soil samples"
                ),
                correctOptionIndex = 0,
                explanation = "India's ISRO became the first nation in the world to reach Mars orbit on its maiden attempt with frugal engineering."
            )
        ),
        "car_01" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_car_01_1",
                contentId = "car_01",
                contentTitle = "ISRO Chandrayaan Mission Scientists",
                questionText = "When Chandrayaan-2 faced a moon landing setback, what did the scientists do next?",
                options = listOf(
                    "They gave up on space science",
                    "They analyzed telemetry data, corrected software, and succeeded with Chandrayaan-3",
                    "They blamed others",
                    "They dismantled the launchpad"
                ),
                correctOptionIndex = 1,
                explanation = "Real scientists treat setbacks as valuable data, redesigning systems to triumph in subsequent attempts."
            )
        ),
        "car_02" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_car_02_1",
                contentId = "car_02",
                contentTitle = "A Day in the Life of a Doctor",
                questionText = "In addition to medical science and biology, what quality is essential for a good doctor?",
                options = listOf("Impatience", "Empathy and caring communication", "Ignoring patient feelings", "Working in isolation"),
                correctOptionIndex = 1,
                explanation = "Empathy helps doctors comfort anxious patients and understand symptoms accurately."
            )
        ),
        "car_03" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_car_03_1",
                contentId = "car_03",
                contentTitle = "Young Tech Entrepreneurs",
                questionText = "What is the core foundation of building a meaningful startup or business?",
                options = listOf(
                    "Trying to become famous overnight",
                    "Solving an important real-world problem for people in society",
                    "Spending all money on advertisements",
                    "Copying without understanding"
                ),
                correctOptionIndex = 1,
                explanation = "True entrepreneurs find an unmet problem and create a practical, valuable solution."
            )
        ),
        "mor_01" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_mor_01_1",
                contentId = "mor_01",
                contentTitle = "The Golden River of Truth",
                questionText = "What was the moral lesson demonstrated when the woodcutter refused the golden axe?",
                options = listOf(
                    "Honesty is the richest treasure; integrity brings lasting blessings",
                    "Golden axes are too heavy to cut trees",
                    "Never swim in rivers",
                    "Greed always triumphs"
                ),
                correctOptionIndex = 0,
                explanation = "Honesty earned the woodcutter respect, peace of mind, and the river spirit's gift."
            )
        ),
        "mor_02" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_mor_02_1",
                contentId = "mor_02",
                contentTitle = "The Bundle of Sticks",
                questionText = "Why could the sons easily break one stick, but not the bundle of twenty sticks?",
                options = listOf(
                    "The single stick was old",
                    "United we stand, divided we fall; strength lies in unity and teamwork",
                    "The bundle was glued with metal",
                    "They were tired"
                ),
                correctOptionIndex = 1,
                explanation = "Cooperation and unity protect families, teams, and classmates from adversity."
            )
        ),
        "live_01" to listOf(
            com.example.data.model.QuizQuestion(
                id = "q_live_01_1",
                contentId = "live_01",
                contentTitle = "NASA International Space Station",
                questionText = "At approximately what speed does the International Space Station orbit planet Earth?",
                options = listOf("100 km/h", "1,000 km/h", "28,000 km/h", "100,000 km/h"),
                correctOptionIndex = 2,
                explanation = "The ISS orbits at roughly 28,000 km/h (17,500 mph), circling our entire planet every 90 minutes!"
            )
        )
    )

    fun getQuestionsForContent(contentId: String): List<com.example.data.model.QuizQuestion> {
        return quizQuestionsCatalog[contentId] ?: emptyList()
    }

    suspend fun getWatchedItemsInLastHour(): List<WatchedItemEntity> {
        val oneHourAgo = System.currentTimeMillis() - 3600_000L
        return progressDao.getWatchedItemsSinceDirect(oneHourAgo)
    }

    suspend fun generateQuizForLastHour(): com.example.data.model.QuizSession {
        val oneHourAgo = System.currentTimeMillis() - 3600_000L
        val recentWatched = progressDao.getWatchedItemsSinceDirect(oneHourAgo)

        val questions = mutableListOf<com.example.data.model.QuizQuestion>()

        // Gather questions from items watched in the last 1 hour
        recentWatched.forEach { watchedItem ->
            quizQuestionsCatalog[watchedItem.contentId]?.let { qList ->
                questions.addAll(qList)
            }
        }

        // If student hasn't watched videos in the last hour, or watched videos don't have enough questions,
        // provide questions from recently watched items or top foundational learning videos
        if (questions.isEmpty()) {
            val allWatched = progressDao.getAllWatchedItems().firstOrNull() ?: emptyList()
            allWatched.forEach { watchedItem ->
                quizQuestionsCatalog[watchedItem.contentId]?.let { qList ->
                    questions.addAll(qList)
                }
            }
        }

        // If still empty (first time student), pull from foundational lessons
        if (questions.isEmpty()) {
            listOf("mot_01", "mot_02", "edu_01", "edu_02", "car_01", "mor_01").forEach { id ->
                quizQuestionsCatalog[id]?.let { qList ->
                    questions.addAll(qList)
                }
            }
        }

        val finalQuestions = questions.distinctBy { it.id }.shuffled().take(5)

        return com.example.data.model.QuizSession(
            questions = finalQuestions,
            currentQuestionIndex = 0,
            selectedOptionIndex = null,
            isAnswerChecked = false,
            correctAnswersCount = 0,
            isFinished = false,
            watchedVideosCountInLastHour = recentWatched.size
        )
    }

    suspend fun recordQuizCompletion(correctAnswers: Int, totalQuestions: Int) {
        val currentProgress = progressDao.getProgress().firstOrNull() ?: StudentProgressEntity()
        val updatedLessons = currentProgress.totalLessonsCompleted + 1
        progressDao.insertOrUpdateProgress(
            currentProgress.copy(totalLessonsCompleted = updatedLessons)
        )
    }
}

