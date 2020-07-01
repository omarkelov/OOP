package ru.nsu.fit.markelov.gradle;

import java.util.List;

public interface TaskResult {
    Boolean isBuilt();
    Boolean isStyleChecked();
    Boolean isDocumentationGenerated();
    List<Test> getTests();
}
