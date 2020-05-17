package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.Pizzeria;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.validation.IllegalInputException;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;
import static ru.nsu.fit.markelov.validation.IllegalInputException.requireNonNull;

/**
 * The <code>Worker</code> class is used as an abstraction of a worker in <code>Pizzeria</code>
 * class. It provides three methods: <code>startWorkingDay()</code>, <code>finishWorkingDay()</code>
 * and <code>getName()</code>.
 * <p>
 * The <code>Worker</code> class has one abstract method <code>handleNextOrder()</code>, which
 * should define a way of handling the <code>Order</code> by this worker.
 * <p>
 * Once a worker finishes some action, this information is logged using the <code>Log</code>
 * interface.
 *
 * @author Oleg Markelov
 * @see    Pizzeria
 * @see    Order
 * @see    Log
 */
public abstract class Worker {

    private Log log;

    private String name;

    private Thread thread;

    /**
     * Constructs a new <code>Worker</code> object with specified name.
     *
     * @param name the name of this worker.
     * @param log  log for sending messages about the current status of an order.
     * @throws NullPointerException if any input parameter is null.
     */
    public Worker(String name, Log log) throws IllegalInputException {
        this.name = requireNonNull(name,
            buildMessage(Worker.class, "name", NOT_NULL));

        this.log = requireNonNull(log,
            buildMessage(Worker.class, "log", NOT_NULL));
    }

    /**
     * Creates and starts a new thread in which this worker handles available orders one after
     * another till the working day is over.
     */
    public void startWorkingDay() {
        thread = new Thread(this::runThread);
        thread.start();
    }

    /**
     * Interrupts this worker's thread and waits till it's finished.
     *
     * @throws InterruptedException if this worker's thread is interrupted during an order handling.
     */
    public void finishWorkingDay() throws InterruptedException {
        thread.interrupt();
        thread.join();
    }

    private void runThread() {
        log.i(name + " has started the working day.");

        while (!Thread.interrupted()) {
            try {
                handleNextOrder();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); //??????????????????????????????????????????????
                log.d(name + " is interrupted.");
            }
        }

        log.i(name + " has finished the working day.");
    }

    /**
     * Handles the next order.
     *
     * @throws InterruptedException if this worker's thread is interrupted.
     */
    protected abstract void handleNextOrder() throws InterruptedException;

    /**
     * Returns the name of this worker.
     *
     * @return the name of this worker.
     */
    public String getName() {
        return name;
    }
}
