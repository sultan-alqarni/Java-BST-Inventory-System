package csc212pro2;

public class Order implements Comparable<Order> {
    private String orderId;
    private String customerId;
    private CustomArrayList products;
    private double totalPrice;
    private String orderDate;
    private String status;
    
    public Order(String orderId, String customerId, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.products = new CustomArrayList();
        this.totalPrice = 0.0;
        this.status = "pending";
    }
    
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public CustomArrayList getProducts() { return products; }
    public double getTotalPrice() { return totalPrice; }
    public String getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    
    public void addProduct(Product product) {
        products.add(product);
        totalPrice += product.getPrice();
        product.decreaseStock(1);
    }
    
    public void cancelOrder() {
        this.status = "canceled";
        for (int i = 0; i < products.size(); i++) {
            Product product = (Product) products.get(i);
            product.increaseStock(1);
        }
    }
    
    public void updateStatus(String newStatus) {
        if (isValidStatus(newStatus)) {
            this.status = newStatus;
        }
    }
    
    private boolean isValidStatus(String status) {
        return status.equals("pending") || status.equals("shipped") || 
               status.equals("delivered") || status.equals("canceled");
    }
    
    @Override
    public int compareTo(Order other) {
        return this.orderId.compareTo(other.orderId);
    }
    
    @Override
    public String toString() {
        return String.format("Order[ID: %s, Customer: %s, Total: $%.2f, Date: %s, Status: %s, Products: %d]", 
                           orderId, customerId, totalPrice, orderDate, status, products.size());
    }
}