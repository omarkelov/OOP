package ru.nsu.fit.markelov.engine.attendance

def attended(String... studentIds) {
    [lessons: { String... lessonDates ->
        course.changeAttendance(studentIds, lessonDates, true)
    }]
}

def missed(String... studentIds) {
    [lessons: { String... lessonDates ->
        course.changeAttendance(studentIds, lessonDates, false)
    }]
}
