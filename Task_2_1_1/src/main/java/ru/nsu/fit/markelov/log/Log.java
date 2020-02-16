package ru.nsu.fit.markelov.log;

import java.io.Writer;

public interface Log {
    static Log getInstance() {
        return SingletonLog.getInstance();
    }

    void setWriter(Writer writer);

    void i(String message);
    void d(String message);
    void e(String message);
}
