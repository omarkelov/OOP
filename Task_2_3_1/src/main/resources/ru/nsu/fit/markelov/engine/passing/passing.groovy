package ru.nsu.fit.markelov.engine.passing

exceptionDSL = null

def passed(String studentId) {
    [task: { String taskId ->
        [date: { String date ->
            [message: { String message ->
                course.passTask(studentId, taskId, date, message)
            }]
        }]
    }]
}

def addExtraPoints(String studentId) {
    [task: { String taskId ->
        [points: { int points ->
            [date: { String date ->
                [message: { String message ->
                    course.addExtraPoints(studentId, taskId, points, date, message)
                }]
            }]
        }]
    }]
}
