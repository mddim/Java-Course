package bg.sofia.uni.fmi.mjt.shopping;

import bg.sofia.uni.fmi.mjt.shopping.item.Apple;
import bg.sofia.uni.fmi.mjt.shopping.item.Chocolate;
import bg.sofia.uni.fmi.mjt.shopping.item.Item;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ListShoppingCartTest {

    private final static double PRECISION = 0.0;
    private final static double NEGATIVE_NUMBER = -4.0;
    private final static double NO_ITEMS_COST = 0.0;
    private final static double APPLE_COST = 2.0;
    private final static double CHOCOLATE_COST = 4.0;

    @Test
    public void testEmptyDefaultShoppingCart() {
        ShoppingCart cart = new ListShoppingCart();

        assertEquals("Empty shopping cart's total price must be 0.", NO_ITEMS_COST, cart.getTotal(), PRECISION);
    }

    @Test
    public void testGetUniqueItemsWhenThereAreNone() {
        ShoppingCart cart = new ListShoppingCart();
        Item apple = new Apple("greenApple", "green", APPLE_COST);
        cart.addItem(apple);
        cart.addItem(apple);

        Set<Item> itemCollection = new HashSet<>();
        itemCollection.add(apple);

        assertEquals("Shopping cart should contain \"greenApple\" only once.",
                cart.getUniqueItems(), itemCollection);
    }

    @Test
    public void testGetUniqueItems() {
        ShoppingCart cart = new ListShoppingCart();
        Item apple = new Apple("greenApple", "green", APPLE_COST);
        Item chocolate = new Chocolate("Milka", "milk chocolate", CHOCOLATE_COST);
        cart.addItem(apple);
        cart.addItem(chocolate);
        cart.addItem(chocolate);

        Set<Item> itemCollection = new HashSet<>();
        itemCollection.add(apple);
        itemCollection.add(chocolate);

        assertEquals("Shopping cart should contain \"greenApple\" and \"Milka chocolate\".",
                cart.getUniqueItems(), itemCollection);
    }

    @Test
    public void testAddingTwoItemsWithTheSameKey() {
        ShoppingCart cart = new ListShoppingCart();
        Item apple = new Apple("greenApple", "green", APPLE_COST);
        cart.addItem(apple);
        cart.addItem(apple);

        assertEquals("Adding two items with the same key.", cart.getTotal(), apple.getPrice() * 2, PRECISION);
    }

    @Test
    public void testAddingItemThatIsNotInTheCart() {
        ShoppingCart cart = new ListShoppingCart();
        Item apple = new Apple("greenApple", "green", APPLE_COST);
        Item chocolate = new Chocolate("Milka", "milk chocolate", CHOCOLATE_COST);

        cart.addItem(apple);
        cart.addItem(chocolate);

        assertEquals(cart.getTotal(), apple.getPrice() + chocolate.getPrice(), PRECISION);
    }

    @Test
    public void testAddNull() {
        ShoppingCart cart = new ListShoppingCart();
        Item chocolate1 = new Chocolate(null, "milk", CHOCOLATE_COST);
        Item chocolate2 = new Chocolate("Milka", null, CHOCOLATE_COST);
        Item chocolate3 = new Chocolate(null, null, CHOCOLATE_COST);

        cart.addItem(chocolate1);
        cart.addItem(chocolate2);
        cart.addItem(chocolate3);
        cart.addItem(null);

        assertEquals("Adding null items with null keys and/or null values isn't supposed to " +
                        "add them to the shopping cart",
                cart.getTotal(), NO_ITEMS_COST, PRECISION);
    }

    @Test
    public void testAddNegativePrice() {
        ShoppingCart cart = new ListShoppingCart();
        Item chocolate = new Chocolate("Milka", "dark", NEGATIVE_NUMBER);

        cart.addItem(chocolate);

        assertEquals("Adding items with negative price isn't supposed to " +
                        "register them in the shopping cart",
                cart.getTotal(), NO_ITEMS_COST, PRECISION);
    }

    @Test
    public void testRemoveExistingItem() throws ItemNotFoundException {
        ShoppingCart cart = new ListShoppingCart();
        Item chocolate = new Chocolate("Milka", "dark", CHOCOLATE_COST);

        cart.addItem(chocolate);
        cart.removeItem(chocolate);

        assertEquals("Removing an existing item from the shopping cart.",
                cart.getTotal(), NO_ITEMS_COST, PRECISION);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveNonExistingItem() throws ItemNotFoundException {
        ShoppingCart cart = new ListShoppingCart();
        Item chocolate = new Chocolate("Milka", "dark", CHOCOLATE_COST);
        Item apple = new Apple("greenApple", "green", APPLE_COST);

        cart.addItem(chocolate);
        cart.removeItem(apple);

        assertEquals("Removing a non-existing item from the shopping cart.",
                cart.getTotal(), chocolate.getPrice(), PRECISION);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveFromEmptyCart() throws ItemNotFoundException {
        ShoppingCart cart = new ListShoppingCart();
        Item chocolate = new Chocolate("Milka", "dark", CHOCOLATE_COST);

        cart.removeItem(chocolate);

        assertEquals("Removing an item from an empty shopping cart.", cart.getTotal(), NO_ITEMS_COST, PRECISION);
    }

    @Test
    public void testGetTotalUniqueItems() {
        ShoppingCart cart = new ListShoppingCart();
        Item apple = new Apple("greenApple", "green", 2.50);
        Item chocolate = new Chocolate("Milka", "milk chocolate", CHOCOLATE_COST);

        cart.addItem(apple);
        cart.addItem(chocolate);

        assertEquals("Getting total cost of non-unique items.", cart.getTotal(),
                apple.getPrice() + chocolate.getPrice(), PRECISION);
    }

    @Test
    public void testGetTotalNotOnlyUniqueItems() {
        ShoppingCart cart = new ListShoppingCart();
        Item apple = new Apple("greenApple", "green", 2.50);
        Item chocolate = new Chocolate("Milka", "milk chocolate", CHOCOLATE_COST);

        cart.addItem(apple);
        cart.addItem(apple);
        cart.addItem(chocolate);

        assertEquals("Getting total cost of unique items.", cart.getTotal(),
                apple.getPrice() * 2 + chocolate.getPrice(), PRECISION);
    }

    @Test
    public void testGetSortedItems() {
        ShoppingCart cart = new ListShoppingCart();
        Item apple = new Apple("greenApple", "green", APPLE_COST);
        Item chocolate = new Chocolate("Milka", "milk chocolate", CHOCOLATE_COST);
        cart.addItem(chocolate);
        cart.addItem(apple);
        cart.addItem(chocolate);

        Set<Item> itemsList = new HashSet<>();
        itemsList.add(chocolate);
        itemsList.add(apple);

        assertEquals(cart.getSortedItems(), itemsList);
    }
}
