package ru.nsu.fit.markelov.engine.group

import ru.nsu.fit.markelov.objects.Student

class StudentDSL extends Student {
    void nick(String nickname) {
        super.id = nickname
    }

    void name(String fullName) {
        super.fullName = fullName
    }

    void repository(String repositoryUrl) {
        super.repositoryUrl = repositoryUrl
    }

    void branch(String branchName) {
        super.branchName = branchName
    }
}
