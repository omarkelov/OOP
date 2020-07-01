package ru.nsu.fit.markelov.gradle;

public class TestStub implements Test {
    private final String name;
    private final boolean passed;

    public TestStub(String name, boolean passed) {
        this.name = name;
        this.passed = passed;
    }

    /**
     * Returns name.
     *
     * @return name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns passed.
     *
     * @return passed.
     */
    @Override
    public boolean isPassed() {
        return passed;
    }
}
