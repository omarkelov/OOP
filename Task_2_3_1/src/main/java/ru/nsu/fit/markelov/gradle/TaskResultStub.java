package ru.nsu.fit.markelov.gradle;

import java.util.List;

public class TaskResultStub implements TaskResult {
    private Boolean built;
    private Boolean styleChecked;
    private Boolean documentationGenerated;
    private final List<Test> tests;

    public TaskResultStub(List<Test> tests) {
        this.tests = tests;
    }

    public TaskResultStub(boolean built, boolean styleChecked, boolean documentationGenerated, List<Test> tests) {
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
    public Boolean isBuilt() {
        return built;
    }

    /**
     * Returns styleChecked.
     *
     * @return styleChecked.
     */
    @Override
    public Boolean isStyleChecked() {
        return styleChecked;
    }

    /**
     * Returns documentationGenerated.
     *
     * @return documentationGenerated.
     */
    @Override
    public Boolean isDocumentationGenerated() {
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
