package ru.nsu.fit.markelov.gradle;

public class TestHardcoded implements Test {
    private final String name;
    private final boolean passed;

    public TestHardcoded(String name, boolean passed) {
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
