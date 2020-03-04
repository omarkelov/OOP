package ru.nsu.fit.markelov;

/**
 * The <code>Order</code> class is used as an abstraction of a pizza order in <code>Pizzeria</code>
 * class. It provides two methods: <code>getId()</code> and <code>getName()</code>.
 *
 * @author Oleg Markelov
 * @see    Pizzeria
 */
public class Order {

    private int id;
    private String name;

    /**
     * Constructs a new <code>Order</code> object with specified id and corresponding name:
     * "Order_<id>".
     *
     * @param id a unique id of the order.
     */
    public Order(int id) {
        this.id = id;
        name = "Order_" + id;
    }

    /**
     * Returns the unique id of the order.
     *
     * @return the unique id of the order.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the order.
     *
     * @return the name of the order.
     */
    public String getName() {
        return name;
    }
}
