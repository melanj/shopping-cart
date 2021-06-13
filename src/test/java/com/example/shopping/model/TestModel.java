package com.example.shopping.model;

import org.meanbean.test.BeanTester;
import org.meanbean.test.EqualsMethodTester;
import org.meanbean.test.HashCodeMethodTester;
import org.testng.annotations.*;

public class TestModel {

    private BeanTester beanTester;
    private HashCodeMethodTester hashCodeTester;
    private EqualsMethodTester equalsTester;

    @BeforeClass
    public void setUp() {
        beanTester = new BeanTester();
        hashCodeTester = new HashCodeMethodTester();
        equalsTester = new EqualsMethodTester();
    }

    @Test
    public void testCartItem() {
        beanTester.testBean(CartItem.class);
        hashCodeTester.testHashCodeMethod(CartItem.class);
        equalsTester.testEqualsMethod(CartItem.class);
    }

    @Test
    public void testCategory() {
        beanTester.testBean(Category.class);
        hashCodeTester.testHashCodeMethod(Category.class);
        equalsTester.testEqualsMethod(Category.class);
    }

    @Test
    public void testCoupon() {
        beanTester.testBean(Coupon.class);
        hashCodeTester.testHashCodeMethod(Coupon.class);
        equalsTester.testEqualsMethod(Coupon.class);
    }

    @Test
    public void testCustomer() {
        beanTester.testBean(Customer.class);
        hashCodeTester.testHashCodeMethod(Customer.class);
        equalsTester.testEqualsMethod(Customer.class);
    }

    @Test
    public void testProduct() {
        beanTester.testBean(Product.class);
        hashCodeTester.testHashCodeMethod(Product.class);
        equalsTester.testEqualsMethod(Product.class);
    }

    @Test
    public void testShoppingCart() {
        beanTester.testBean(ShoppingCart.class);
        hashCodeTester.testHashCodeMethod(ShoppingCart.class);
        equalsTester.testEqualsMethod(ShoppingCart.class);
    }
}
