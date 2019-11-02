package nsu.fit.markelov;

public class ClassicCredit implements Record {

    private String subject;
    private boolean isPassed;
    private int semester;

    public ClassicCredit(String subject, boolean isPassed, int semester) {
        this.subject = subject;
        this.isPassed = isPassed;
        this.semester = semester;
    }

    public boolean isPassed() {
        return isPassed;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public int getSemester() {
        return semester;
    }

    @Override
    public String getEvaluation() {
        return (isPassed) ? "passed" : "not passed";
    }
}
