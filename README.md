# Shopping Cart
This is a sample application to demonstrate shopping cart data model.


## Prerequisites

- [x] JDK 8 or later 
- [x] Apache Maven 3.5 or later 
- [x] git client

## Class diagram

### rules
* A customer can have zero or one shopping carts.
* A customer can add one or more of each different products to each shopping cart.
* Every product must have a title, name, price and tax.
* A shopping cart has a total amount, total VAT and shipment costs.

![Alt text](./images/class_diagram.svg)

### design decisions

* A customer should be linked to a user account
* There should be an entity to the link between products and shopping cart
* Couples can be applied to a shopping cart and couple should have a strategy to calculate it
* Shopping cart items can store in the session until the user decides to submit it

## how to run this application

1. clone this repository using git command or alternatively , you may be able to download ZIP archive and extract it

```bash
git clone https://github.com/melanj/shopping-cart.git
```

2. run the application using maven command line
```bash
mvn spring-boot:run
```


## how to see coverage

Tests can be run using following maven command and test reports are available at 'target/site/jacoco/index.html'.

```bash
mvn clean test jacoco:report
```
main scenarios evaluated in following test cases. 
```java
ShoppingApplicationTests#testPurchaseSingleItemAndGetTotal
ShoppingApplicationTests#testPurchaseMultipleItemsAndGetTotal
```
