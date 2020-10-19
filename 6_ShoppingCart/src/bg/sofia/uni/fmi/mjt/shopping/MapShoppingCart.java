package bg.sofia.uni.fmi.mjt.shopping;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

import java.util.*;

public class MapShoppingCart implements ShoppingCart {

    private Map<Item, Integer> items = new HashMap<>();

    @Override
    public Collection<Item> getUniqueItems() {
        return items.keySet();
    }

    @Override
    public void addItem(Item item) {
        if (item == null) {
            return;
        }
        if (item.getName() == null || (item.getName() != null && item.getDescription() == null) ||
                item.getPrice() <= 0) {
            return;
        }
        Integer occurrences = items.get(item);
        if (occurrences == null) {
            occurrences = 0;
        }
        items.put(item, ++occurrences);
    }

    @Override
    public void removeItem(Item item) throws ItemNotFoundException {
        if (item == null) {
            return;
        }
        if (!items.containsKey(item)) {
            throw new ItemNotFoundException();
        }
        Integer occurrences = items.get(item);
        items.put(item, --occurrences);
    }

    @Override
    public double getTotal() {
        double total = 0.00;
        for (Map.Entry<Item, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        return total;
    }

    @Override
    public Collection<Item> getSortedItems() {
        List<Item> itemsList = new ArrayList<>(items.keySet());
        Collections.sort(itemsList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Double.compare(o2.getPrice(), o1.getPrice());
            }
        });
        return itemsList;
    }
}
