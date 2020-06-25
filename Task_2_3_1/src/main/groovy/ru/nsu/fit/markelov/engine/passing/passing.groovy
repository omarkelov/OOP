package ru.nsu.fit.markelov.engine.passing

def passed(String studentId) {
    [task: { String taskId ->
        [date: { String date ->
            [message: { String message ->
                course.passTask(studentId, taskId, date, message, false)
            }]
        }]
    }]
}

def passedExtra(String studentId) {
    [task: { String taskId ->
        [date: { String date ->
            [message: { String message ->
                course.passTask(studentId, taskId, date, message, true)
            }]
        }]
    }]
}
