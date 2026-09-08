package csc212pro2;

public class Review implements Comparable<Review> {
    private String reviewId;
    private String productId;
    private String customerId;
    private int rating;
    private String comment;
    
    public Review(String reviewId, String productId, String customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }
    
    public String getReviewId() { return reviewId; }
    public String getProductId() { return productId; }
    public String getCustomerId() { return customerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    
    public void setRating(int rating) { 
        if (rating >= 1 && rating <= 5) {
            this.rating = rating; 
        }
    }
    
    public void setComment(String comment) { this.comment = comment; }
    
    @Override
    public int compareTo(Review other) {
        return this.reviewId.compareTo(other.reviewId);
    }
    
    @Override
    public String toString() {
        return String.format("Review[ID: %s, Rating: %d/5, Comment: %s]", reviewId, rating, comment);
    }
}