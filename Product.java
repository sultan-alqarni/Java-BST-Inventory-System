package csc212pro2;

public class Product implements Comparable<Product> {
    private String productId;
    private String name;
    private double price;
    private int stock;
    private CustomArrayList reviews;
    
    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new CustomArrayList();
    }
    
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public CustomArrayList getReviews() { return reviews; }
    
    public void updateProduct(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    
    public void addReview(Review review) {
        reviews.add(review);
    }
    
    public double getAverageRating() {
        if (reviews.size() == 0) return 0.0;
        
        double sum = 0;
        for (int i = 0; i < reviews.size(); i++) {
            Review review = (Review) reviews.get(i);
            sum += review.getRating();
        }
        return sum / reviews.size();
    }
    
    public boolean isOutOfStock() {
        return stock == 0;
    }
    
    public void decreaseStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        }
    }
    
    public void increaseStock(int quantity) {
        stock += quantity;
    }
    
    @Override
    public int compareTo(Product other) {
        return this.productId.compareTo(other.productId);
    }
    
    @Override
    public String toString() {
        return String.format("Product[ID: %s, Name: %s, Price: $%.2f, Stock: %d, Avg Rating: %.2f]", 
                           productId, name, price, stock, getAverageRating());
    }
}