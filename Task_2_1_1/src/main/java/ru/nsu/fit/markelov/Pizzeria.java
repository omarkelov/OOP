package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.BakerProperties;
import ru.nsu.fit.markelov.properties.CourierProperties;
import ru.nsu.fit.markelov.properties.OperatorProperties;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;
import ru.nsu.fit.markelov.util.UniqueIntGenerator;
import ru.nsu.fit.markelov.validation.IllegalInputException;
import ru.nsu.fit.markelov.validation.Validatable;
import ru.nsu.fit.markelov.workers.Baker;
import ru.nsu.fit.markelov.workers.Courier;
import ru.nsu.fit.markelov.workers.Operator;
import ru.nsu.fit.markelov.workers.Worker;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;
import static ru.nsu.fit.markelov.validation.IllegalInputException.requireNonNull;

/**
 * The <code>Pizzeria</code> class is used for simulating the working day in pizzeria. There are
 * three types of workers:
 * <ul>
 *     <li><code>Operator</code> — receives the order and puts it to the queue of new orders.
 *     <li><code>Baker</code> — takes the order from the queue of new orders, bakes the pizza and
 *     puts it to the storage.
 *     <li><code>Courier</code> — takes the pizza from the storage and delivers it to the client.
 * </ul>
 * Once the working day is finished, all the workers stop doing their work immediately.
 * <p>
 * Once a worker finishes some action, this information is logged using the <code>Log</code>
 * interface.
 *
 * @author Oleg Markelov
 * @see    PizzeriaProperties
 * @see    Log
 * @see    UniqueIntGenerator
 */
public class Pizzeria {

    private Log log;

    private PizzeriaProperties pizzeriaProperties;
    private UniqueIntGenerator idGenerator;

    private BlockingQueue<Order> newOrders;
    private BlockingQueue<Order> storedOrders;
    private Object courierLock;

    private ArrayList<Worker> workers;

    /**
     * Constructs a new <code>Pizzeria</code> object with specified properties and simulates a
     * single working day in pizzeria.
     *
     * @param pizzeriaProperties pizzeria properties.
     * @param log                log for sending messages about the current status of an order.
     * @param idGenerator        id generator used by operators.
     * @throws IllegalInputException if any validating parameter is null or illegal.
     * @see Validatable
     */
    public Pizzeria(PizzeriaProperties pizzeriaProperties, Log log,
                    UniqueIntGenerator idGenerator) throws IllegalInputException {
        this.pizzeriaProperties = requireNonNull(pizzeriaProperties,
            buildMessage(Pizzeria.class, "pizzeriaProperties", NOT_NULL));

        this.log = requireNonNull(log,
            buildMessage(Pizzeria.class, "log", NOT_NULL));

        this.idGenerator = requireNonNull(idGenerator,
            buildMessage(Pizzeria.class, "idGenerator", NOT_NULL));

        pizzeriaProperties.validate();

        init();
        simulate();
    }

    private void init() throws IllegalInputException {
        newOrders = new LinkedBlockingQueue<>(pizzeriaProperties.getNewOrdersCapacity());
        storedOrders = new LinkedBlockingQueue<>(pizzeriaProperties.getStorageCapacity());
        courierLock = new Object();

        workers = new ArrayList<>();

        for (OperatorProperties operatorProperties : pizzeriaProperties.getOperatorsProperties()) {
            workers.add(new Operator(operatorProperties, idGenerator, newOrders, log));
        }

        for (BakerProperties bakerProperties : pizzeriaProperties.getBakersProperties()) {
            workers.add(new Baker(bakerProperties, newOrders, storedOrders, log));
        }

        for (CourierProperties courierProperties : pizzeriaProperties.getCouriersProperties()) {
            workers.add(new Courier(courierProperties, courierLock, storedOrders, log));
        }
    }

    private void simulate() {
        try {
            for (Worker worker : workers) {
                worker.startWorkingDay();
            }

            Thread.sleep(pizzeriaProperties.getWorkingTime());

            for (Worker worker : workers) {
                worker.finishWorkingDay();
            }
        } catch (InterruptedException e) {
            log.e("Main thread is interrupted.");
        }
    }
}
