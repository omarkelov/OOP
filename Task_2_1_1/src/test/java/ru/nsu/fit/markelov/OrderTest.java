package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class OrderTest {
    @Test
    public void testBasics() {
        Order order = new Order(5);
        Assert.assertEquals(5, order.getId());
        Assert.assertEquals("Order_5", order.getName());
    }
}
