package ru.nsu.fit.markelov.engine.lessons

import ru.nsu.fit.markelov.objects.LessonObject

import static ru.nsu.fit.markelov.Main.DATE_FORMAT

lessonsDSL = new TreeSet<LessonObject>()

def since(String start) {
    [till: {String end ->
        [every: {int days ->
            for (
                Calendar calendar = Calendar.getInstance().tap{time = DATE_FORMAT.parse(start)},
                         endCalendar = Calendar.getInstance().tap{time = DATE_FORMAT.parse(end)};
                calendar <= endCalendar;
                calendar.add(Calendar.DATE, days)
            ) {
                lessonsDSL.add(new LessonObject().tap {
                    date = calendar.getTime()
                })
            }
        }]
    }]
}

void add(String... lessons) {
    for (String lesson : lessons) {
        lessonsDSL.add(new LessonObject().tap {
            date = DATE_FORMAT.parse(lesson)
        })
    }
}

void remove(String... lessons) {
    for (String lesson : lessons) {
        lessonsDSL.remove(new LessonObject().tap {
            date = DATE_FORMAT.parse(lesson)
        })
    }
}
