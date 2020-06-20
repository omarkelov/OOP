package ru.nsu.fit.markelov.engine.group

import ru.nsu.fit.markelov.objects.StudentObject

class StudentDSL extends StudentObject {
    void nick(String nickname) {
        super.nickname = nickname
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
