package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.Pizzeria;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.OperatorProperties;
import ru.nsu.fit.markelov.util.UniqueIntGenerator;
import ru.nsu.fit.markelov.validation.IllegalInputException;
import ru.nsu.fit.markelov.validation.Validatable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;
import static ru.nsu.fit.markelov.validation.IllegalInputException.requireNonNull;

/**
 * The <code>Operator</code> class is used as an abstraction of an operator in <code>Pizzeria</code>
 * class. An operator receives the order and puts it to the queue of new orders.
 * <p>
 * The <code>Operator</code> class extends the <code>Worker</code> abstract class overriding its
 * <code>handleNextOrder()</code> method.
 * <p>
 * Once an operator finishes some action, this information is logged using the <code>Log</code>
 * interface.
 *
 * @author Oleg Markelov
 * @see    Pizzeria
 * @see    Worker
 * @see    Order
 * @see    Log
 */
public class Operator extends Worker {

    private Log log;

    private long orderHandlingTimeMin;
    private long orderHandlingTimeMax;

    private UniqueIntGenerator idGenerator;

    private final BlockingQueue<Order> newOrders;

    /**
     * Constructs a new <code>Operator</code> object with specified properties.
     *
     * @param operatorProperties operator properties.
     * @param idGenerator        id generator for the order id generating.
     * @param newOrders          a synchronized queue for putting new order into it.
     * @param log                log for sending messages about the current status of an order.
     * @throws IllegalInputException if any validating parameter is null or illegal.
     * @see UniqueIntGenerator
     * @see Validatable
     */
    public Operator(OperatorProperties operatorProperties, UniqueIntGenerator idGenerator,
                    BlockingQueue<Order> newOrders, Log log) throws IllegalInputException {
        // super(operatorProperties.getName(), log);
        super(
            requireNonNull(operatorProperties,
                buildMessage(Operator.class, "operatorProperties", NOT_NULL)).validate().getName(),

            requireNonNull(log,
                buildMessage(Operator.class, "log", NOT_NULL))
        );

        this.idGenerator = requireNonNull(idGenerator,
            buildMessage(Operator.class, "idGenerator", NOT_NULL));

        this.newOrders = requireNonNull(newOrders,
            buildMessage(Operator.class, "newOrders", NOT_NULL));

        this.log = log;

        orderHandlingTimeMin = operatorProperties.getOrderHandlingTimeMin();
        orderHandlingTimeMax = operatorProperties.getOrderHandlingTimeMax();
    }

    /**
     * Receives the order and puts it the queue of new order.
     *
     * @throws InterruptedException if this worker's thread is interrupted.
     */
    @Override
    protected void handleNextOrder() throws InterruptedException {
        // generating the order
        Order order = new Order(idGenerator.getNextInt());

        // "receiving" the order
        Thread.sleep(ThreadLocalRandom.current().nextLong(orderHandlingTimeMin, orderHandlingTimeMax));

        // putting the order
        newOrders.put(order);
        log.i(order.getName() + " is put to newOrders by " + getName());
    }
}
