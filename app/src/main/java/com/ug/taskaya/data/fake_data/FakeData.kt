package com.ug.taskaya.data.fake_data

import androidx.compose.ui.graphics.Color
import com.ug.taskaya.ui.theme.Bronze
import com.ug.taskaya.ui.theme.Challenger
import com.ug.taskaya.ui.theme.Diamond
import com.ug.taskaya.ui.theme.Emerald
import com.ug.taskaya.ui.theme.Gold
import com.ug.taskaya.ui.theme.Grandmaster
import com.ug.taskaya.ui.theme.Iron
import com.ug.taskaya.ui.theme.Master
import com.ug.taskaya.ui.theme.Platinum
import com.ug.taskaya.ui.theme.Silver
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object FakeData {

    val timeManagementQuotes = listOf(
        "I must govern the clock, not be governed by it. -Golda Meir-",
        "Until you value yourself, you will not value your time. Until you value your time, you will not do anything with it. -M. Scott Peck",
        "Your greatest asset is your earning ability. Your greatest resource is your time. -Brian Tracy",
        "Give me six hours to chop down a tree and I will spend the first four sharpening the axe. -Abraham Lincoln",
        "You can only manage time if you track it right. - Spica’s team",
        "Concentrate all your thoughts upon the work in and. The sun’s rays do not burn until brought to a focus. -Alexander Graham Bell",
        "Don’t wait. The time will never be just right. -Napoleon Hill",
        "The secret of your future is hidden in your daily routine. -Mike Murdock",
        "It’s not the daily increase but daily decrease. Hack away at the unessential. -Bruce Lee",
        "Once you have mastered time, you will understand how true it is that most people overestimate what they can accomplish in a year – and underestimate what they can achieve in a decade! -Anthony Robbins",
        "You can have it all. Just not all at once. -Oprah Winfrey",
        "Tough times never last, but tough people do. -Robert H. Schuller",
        "The most efficient way to live reasonably is every morning to make a plan of one’s day and every night to examine the results obtained. Alexis Carrel",
        "Don’t count every hour in the day. Make every hour in the day count. Alfred Binet",
        "Your future is created by what you do today, not tomorrow. Anonymous",
        "One thing you can’t recycle is wasted time. Anonymous",
        "Money, I can only gain or lose. But time I can only lose. So, I must spend it carefully. Anonymous",
        "If you have time to whine and complain about something then you have the time to do something about it. Anthony J. D’Angelo",
        "Whether it’s the best of times or the worst of times, it’s the only time we’ve got. Art Buchwald",
        "The difference between successful people and others is how long they spend time feeling sorry for themselves. Barbara Corcoran",
        "The essence of self-discipline is to do the important thing rather than the urgent thing. Barry Werner",
        "You may delay, but time will not. Benjamin Franklin",
        "It’s surprising how much free time and productivity you gain when you lose the busyness in your mind. Brittany Burgunder",
        "To think too long about doing a thing often becomes its undoing. Eva Young",
        "Time is money. Use it wisely. Folorunsho Alakija",
        "It's not enough to be busy, so are the ants. The question is, what are we busy about? Henry David Thoreau",
        "The first hour of the morning is the rudder of the day. Henry Ward Beecher",
        "Those who make the worse use of their time are the first to complain of its shortness. Jean De La Bruyere",
        "Time is more valuable than money. You can get more money, but you cannot get more time. Jim Rohn",
        "He who lets time rule him will live the life of a slave. John Arthorne",
        "We must use time as a tool, not as a couch. John F. Kennedy",
        "Gaining time is gaining everything in love, trade and war. John Shebbeare",
        "Time is a created thing. To say, I don’t have time is to say, I don’t want to. Lao Tzu",
        "If you want to make good use of your time, you’ve got to know what’s most important and then give it all you’ve got. Lee Iacocca",
        "Time has no meaning in itself unless we choose to give it significance. Leo Buscaglia",
        "The surest way to be late is to have plenty of time. Leo Kennedy",
        "The two most powerful warriors are patience and time. Leo Tolstoy",
        "Time stays long enough for those who use it. Leonardo Da Vinci",
        "Take care of the minutes and the hours will take care of themselves. Lord Chesterfield",
        "Time you enjoy wasting is not wasted time. Marthe Troly Curtin",
        "This is the key to time management - to see the value of every moment. Menachem Mendel Schneerson",
        "The bad news is time flies. The good news is you're the pilot. Michael Altshuler",
        "One can find time for everything if one is never in a hurry. Mikhail Bulgakov",
        "Yesterday is gone. Tomorrow has not yet come. We have only today. Let us begin. Mother Teresa",
        "The shorter way to do many things is to only do one thing at a time. Mozart",
        "This time, like all times, is a very good one, if we but know what to do with it. Ralph Waldo Emerson",
        "What may be done at any time will be done at no time. Scottish Proverb",
    )

    val ranks = listOf(
        Triple(0..9,"Unranked", Color.Black) ,
        Triple(10..19,"Iron", Iron),
        Triple(20..39,"Bronze", Bronze),
        Triple(40..79,"Silver", Silver),
        Triple(80..159,"Gold" , Gold),
        Triple(160..319,"Platinum", Platinum),
        Triple(320..639,"Emerald", Emerald),
        Triple(640..1279,"Diamond", Diamond),
        Triple(1280..2559,"Master", Master),
        Triple(2560..5119,"Grandmaster", Grandmaster),
        Triple(5120..Int.MAX_VALUE ,"Challenger", Challenger),
    )

    fun getRank(completedTasksNumber: Int): Triple<IntRange,String,Color>{

        for (rank in ranks){

            if (completedTasksNumber in rank.first) return rank
        }

        return ranks[0]
    }

    fun getDatesOfThisWeek(): List<String> {
        val today = LocalDate.now()
        val currentDayOfWeek = today.dayOfWeek.value // 1 (Monday) to 7 (Sunday)

        // Calculate the start of the week (Saturday) and end of the week (Friday)
        val startOfWeek = today.minusDays(((currentDayOfWeek - DayOfWeek.SATURDAY.value + 7) % 7).toLong())
        val endOfWeek = startOfWeek.plusDays(6)

        val weekDates = mutableListOf<LocalDate>()
        var currentDate = startOfWeek
        while (!currentDate.isAfter(endOfWeek)) {
            weekDates.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }

        // Format the dates if needed
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return weekDates.map { it.format(formatter) }
    }
}