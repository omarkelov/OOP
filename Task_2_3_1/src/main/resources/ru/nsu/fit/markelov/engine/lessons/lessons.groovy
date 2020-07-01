package ru.nsu.fit.markelov.engine.lessons

import ru.nsu.fit.markelov.objects.Lesson
import ru.nsu.fit.markelov.objects.Lessons

import static ru.nsu.fit.markelov.Main.DATE_FORMAT

lessonsDSL = new Lessons()
exceptionDSL = null

def since(String start) {
    [till: { String end ->
        [every: { int days ->
            for (
                Calendar calendar = Calendar.getInstance().tap{time = DATE_FORMAT.parse(start)},
                    endCalendar = Calendar.getInstance().tap{time = DATE_FORMAT.parse(end)};
                calendar <= endCalendar;
                calendar.add(Calendar.DATE, days)
            ) {
                lessonsDSL.lessons.add(new Lesson(date: calendar.getTime()))
            }
        }]
    }]
}

void add(String... lessons) {
    lessons.each {
        lessonsDSL.lessons.add(new Lesson(date: DATE_FORMAT.parse(it)))
    }
}

void remove(String... lessons) {
    lessons.each {
        lessonsDSL.lessons.remove(new Lesson(date: DATE_FORMAT.parse(it)))
    }
}
