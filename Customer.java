package csc212pro2;

public class Customer implements Comparable<Customer> {
    private String customerId;
    private String name;
    private String email;
    private CustomLinkedList orders;
    
    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.orders = new CustomLinkedList();
    }
    
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public CustomLinkedList getOrders() { return orders; }
    
    public void placeOrder(Order order) {
        orders.add(order);
    }
    
    public CustomLinkedList getOrderHistory() {
        return orders;
    }
    
    @Override
    public int compareTo(Customer other) {
        return this.customerId.compareTo(other.customerId);
    }
    
    @Override
    public String toString() {
        return String.format("Customer[ID: %s, Name: %s, Email: %s, Total Orders: %d]", 
                           customerId, name, email, orders.size());
    }
}