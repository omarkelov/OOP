package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.Pizzeria;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.CourierProperties;
import ru.nsu.fit.markelov.validation.Validatable;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;

/**
 * The <code>Courier</code> class is used as an abstraction of a courier in <code>Pizzeria</code>
 * class. A courier takes the orders from the storage and delivers to the clients.
 * <p>
 * The <code>Courier</code> class extends the <code>Worker</code> abstract class overriding its
 * <code>handleNextOrder()</code> method.
 * <p>
 * Once a courier finishes some action, this information is logged using the <code>Log</code>
 * interface.
 *
 * @author Oleg Markelov
 * @see    Pizzeria
 * @see    Worker
 * @see    Order
 * @see    Log
 */
public class Courier extends Worker {

    private Log log;

    private long orderHandlingTime;
    private int bagCapacity;

    private final BlockingQueue<Order> storedOrders;

    /**
     *
     *
     * @param courierProperties courier properties.
     * @param storedOrders      a synchronized queue for taking baked orders from it.
     * @param log               log for sending messages about the current status of an order.
     * @throws NullPointerException     if any input parameter is null.
     * @throws IllegalArgumentException if any Validatable input parameter is invalid.
     * @see Validatable
     */
    public Courier(CourierProperties courierProperties, BlockingQueue<Order> storedOrders, Log log) {
        // super(courierProperties.getName(), log);
        super(
            Objects.requireNonNull(courierProperties,
                buildMessage(Courier.class, "courierProperties", NOT_NULL)).validate().getName(),

            Objects.requireNonNull(log,
                buildMessage(Courier.class, "log", NOT_NULL))
        );

        this.storedOrders = Objects.requireNonNull(storedOrders,
            buildMessage(Operator.class, "storedOrders", NOT_NULL));

        this.log = log;

        orderHandlingTime = courierProperties.getOrderHandlingTime();
        bagCapacity = courierProperties.getBagCapacity();
    }

    /**
     * Takes the orders from the queue of stored orders and delivers to the clients.
     * <p>
     * The amount of orders is limited by courier's bag capacity.
     *
     * @throws InterruptedException if this worker's thread is interrupted.
     */
    @Override
    protected void handleNextOrder() throws InterruptedException {
        StringJoiner orderNames = new StringJoiner(", ");
        int bagSize = 0;

        // taking the order(s)
        synchronized (storedOrders) { //????????????????????????????????????????????????????????????
            while (bagSize < bagCapacity && !storedOrders.isEmpty()) {
                Order order = storedOrders.take();
                orderNames.add(order.getName());
                bagSize++;
            }
        }

        if (orderNames.length() > 0) {
            log.i(orderNames.toString() + " is/are taken from storedOrders by " + getName());
        }

        // "delivering" the order(s)
        Thread.sleep(bagSize * orderHandlingTime);

        if (orderNames.length() > 0) {
            log.i(orderNames.toString() + " is/are delivered by " + getName());
        }
    }
}
