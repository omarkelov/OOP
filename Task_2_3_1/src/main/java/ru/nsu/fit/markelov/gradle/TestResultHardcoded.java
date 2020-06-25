package ru.nsu.fit.markelov.gradle;

import java.util.List;

public class TestResultHardcoded implements TestResult {
    private boolean built;
    private boolean styleChecked;
    private boolean documentationGenerated;
    private final List<Test> tests;

    public TestResultHardcoded(List<Test> tests) {
        this.tests = tests;
    }

    public TestResultHardcoded(boolean built, boolean styleChecked, boolean documentationGenerated, List<Test> tests) {
        this.built = built;
        this.styleChecked = styleChecked;
        this.documentationGenerated = documentationGenerated;
        this.tests = tests;
    }

    /**
     * Returns built.
     *
     * @return built.
     */
    @Override
    public boolean isBuilt() {
        return built;
    }

    /**
     * Returns styleChecked.
     *
     * @return styleChecked.
     */
    @Override
    public boolean isStyleChecked() {
        return styleChecked;
    }

    /**
     * Returns documentationGenerated.
     *
     * @return documentationGenerated.
     */
    @Override
    public boolean isDocumentationGenerated() {
        return documentationGenerated;
    }

    /**
     * Returns tests.
     *
     * @return tests.
     */
    @Override
    public List<Test> getTests() {
        return tests;
    }
}
