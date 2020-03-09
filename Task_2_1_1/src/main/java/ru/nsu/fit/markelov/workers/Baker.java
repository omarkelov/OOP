package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.Pizzeria;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.BakerProperties;
import ru.nsu.fit.markelov.validation.IllegalInputException;
import ru.nsu.fit.markelov.validation.Validatable;

import java.util.concurrent.BlockingQueue;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;
import static ru.nsu.fit.markelov.validation.IllegalInputException.requireNonNull;

/**
 * The <code>Baker</code> class is used as an abstraction of a baker in <code>Pizzeria</code> class.
 * A baker takes the order from the queue of new orders, bakes the pizza and puts the order to the
 * storage.
 * <p>
 * The <code>Baker</code> class extends the <code>Worker</code> abstract class overriding its
 * <code>handleNextOrder()</code> method.
 * <p>
 * Once a baker finishes some action, this information is logged using the <code>Log</code>
 * interface.
 *
 * @author Oleg Markelov
 * @see    Pizzeria
 * @see    Worker
 * @see    Order
 * @see    Log
 */
public class Baker extends Worker {

    private Log log;

    private long orderHandlingTime;

    private final BlockingQueue<Order> newOrders;
    private final BlockingQueue<Order> storedOrders;

    /**
     * Constructs a new <code>Baker</code> object with specified properties.
     *
     * @param bakerProperties baker properties.
     * @param newOrders       a synchronized queue for taking new order from it.
     * @param storedOrders    a synchronized queue for putting baked orders into it.
     * @param log             log for sending messages about the current status of an order.
     * @throws IllegalInputException if any validating parameter is null or illegal.
     * @see Validatable
     */
    public Baker(BakerProperties bakerProperties, BlockingQueue<Order> newOrders,
                 BlockingQueue<Order> storedOrders, Log log) throws IllegalInputException {
        // super(bakerProperties.getName(), log);
        super(
            requireNonNull(bakerProperties,
                buildMessage(Operator.class, "bakerProperties", NOT_NULL)).validate().getName(),

            requireNonNull(log,
                buildMessage(Operator.class, "log", NOT_NULL))
        );

        this.newOrders = requireNonNull(newOrders,
            buildMessage(Baker.class, "newOrders", NOT_NULL));

        this.storedOrders = requireNonNull(storedOrders,
            buildMessage(Baker.class, "storedOrders", NOT_NULL));

        this.log = log;

        orderHandlingTime = bakerProperties.getOrderHandlingTime();
    }

    /**
     * Takes the order from the queue of new orders, bakes the pizza and puts the order to the queue
     * of stored orders.
     *
     * @throws InterruptedException if this worker's thread is interrupted.
     */
    @Override
    protected void handleNextOrder() throws InterruptedException {
        // taking the order
        Order order = newOrders.take();
        log.i(order.getName() + " is taken from newOrders by " + getName());

        // "baking" the order
        Thread.sleep(orderHandlingTime);
        log.i(order.getName() + " is cooked by " + getName());

        // putting the order
        storedOrders.put(order);
        log.i(order.getName() + " is put to storedOrders by " + getName());
    }
}
