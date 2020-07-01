package ru.nsu.fit.markelov.engine.group

import ru.nsu.fit.markelov.objects.Student

/**
 * StudentDSL class is used for setting the student data from the user script.
 *
 * @author Oleg Markelov
 */
class StudentDSL extends Student {
    /**
     * Sets id.
     */
    void id(String id) {
        super.id = id
    }

    /**
     * Sets full name.
     */
    void name(String fullName) {
        super.fullName = fullName
    }

    /**
     * Sets repository url.
     */
    void repository(String repositoryUrl) {
        super.repositoryUrl = repositoryUrl
    }

    /**
     * Sets branch name.
     */
    void branch(String branchName) {
        super.branchName = branchName
    }
}
