package ru.nsu.fit.markelov.gradle;

import java.util.List;

public interface TestResult {
    boolean isBuilt();
    boolean isStyleChecked();
    boolean isDocumentationGenerated();
    List<Test> getTests();
}
