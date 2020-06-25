package ru.nsu.fit.markelov.engine.group

import ru.nsu.fit.markelov.objects.Student

class StudentDSL extends Student {
    void id(String id) {
        super.id = id
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
