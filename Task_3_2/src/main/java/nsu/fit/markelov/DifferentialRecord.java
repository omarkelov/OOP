package nsu.fit.markelov;

public class DifferentialRecord implements Record {

    private String subject;
    private int mark;
    private int semester;

    public DifferentialRecord(String subject, int mark, int semester) {
        this.subject = subject;
        this.mark = mark;
        this.semester = semester;
    }

    public int getMark() {
        return mark;
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
        return mark + "";
    }
}
