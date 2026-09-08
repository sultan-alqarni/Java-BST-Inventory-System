package csc212pro2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InventoryManagementSystem {
    private BST<Product> productTree = new BST<>();
    private BST<Customer> customerTree = new BST<>();
    private BST<Order> orderTree = new BST<>();
    private BST<Review> reviewTree = new BST<>();
    private Scanner sc = new Scanner(System.in);
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public InventoryManagementSystem() {
        dateFormatter.setLenient(false);
    }

    public void loadProductsCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("prodcuts.csv"))) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] d = line.split(",");
                if (d.length >= 4) {
                    Product p = new Product(d[0].trim(), d[1].trim(), 
                                          Double.parseDouble(d[2].trim()), 
                                          Integer.parseInt(d[3].trim()));
                    productTree.insert(p);
                }
            }
            System.out.println("Products loaded: " + productTree.size());
        } catch (Exception e) { 
            System.out.println("Error loading products: " + e.getMessage()); 
        }
    }

    public void loadCustomersCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("customers.csv"))) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] d = line.split(",");
                if (d.length >= 3) {
                    Customer c = new Customer(d[0].trim(), d[1].trim(), d[2].trim());
                    customerTree.insert(c);
                }
            }
            System.out.println("Customers loaded: " + customerTree.size());
        } catch (Exception e) { 
            System.out.println("Error loading customers: " + e.getMessage()); 
        }
    }

    public void loadOrdersCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("orders.csv"))) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] d = line.split(",");
                if (d.length >= 6) {
                    Order o = new Order(d[0].trim(), d[1].trim(), formatDate(d[4].trim()));
                    o.updateStatus(d[5].trim());
                    orderTree.insert(o);
                }
            }
            System.out.println("Orders loaded: " + orderTree.size());
        } catch (Exception e) { 
            System.out.println("Error loading orders: " + e.getMessage()); 
        }
    }

    public void loadReviewsCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("reviews.csv"))) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] d = line.split(",");
                if (d.length >= 5) {
                    Review r = new Review(d[0].trim(), d[1].trim(), d[2].trim(), 
                                        Integer.parseInt(d[3].trim()), d[4].trim());
                    reviewTree.insert(r);
                    
                    Product temp = new Product(d[1].trim(), "", 0, 0);
                    Product p = productTree.search(temp);
                    if (p != null) p.addReview(r);
                }
            }
            System.out.println("Reviews loaded: " + reviewTree.size());
        } catch (Exception e) { 
            System.out.println("Error loading reviews: " + e.getMessage()); 
        }
    }

    private String formatDate(String dateStr) {
        String[] formats = {"M/d/yyyy", "yyyy-MM-dd", "MM/dd/yyyy", "yyyy/M/d"};
        for (String format : formats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false);
                Date date = sdf.parse(dateStr);
                return dateFormatter.format(date);
            } catch (ParseException e) {
                continue;
            }
        }
        return dateStr;
    }

    public void loadAll() {
        loadProductsCSV();
        loadCustomersCSV();
        loadOrdersCSV();
        loadReviewsCSV();
        System.out.println("All data loaded successfully!");
    }

 
    public List<Product> getProductsInPriceRange(double min, double max) {
        List<Product> allProducts = productTree.inOrder();
        List<Product> result = new ArrayList<>();
        
        for (Product p : allProducts) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        return result;
    }

   
    public List<Product> getTopRatedProducts(int count) {
        List<Product> allProducts = productTree.inOrder();
        if (allProducts.size() <= count) {
            allProducts.sort((p1, p2) -> Double.compare(p2.getAverageRating(), p1.getAverageRating()));
            return allProducts;
        }
        
       
        List<Product> topProducts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < allProducts.size(); j++) {
                if (allProducts.get(j).getAverageRating() > allProducts.get(maxIndex).getAverageRating()) {
                    maxIndex = j;
                }
            }
            Collections.swap(allProducts, i, maxIndex);
            topProducts.add(allProducts.get(i));
        }
        return topProducts;
    }

  
    public List<Order> getOrdersBetweenDates(String startStr, String endStr) {
        try {
            Date start = dateFormatter.parse(formatDate(startStr));
            Date end = dateFormatter.parse(formatDate(endStr));
            
            List<Order> allOrders = orderTree.inOrder();
            List<Order> result = new ArrayList<>();
            
            for (Order order : allOrders) {
                Date orderDate = dateFormatter.parse(order.getOrderDate());
                if (!orderDate.before(start) && !orderDate.after(end)) {
                    result.add(order);
                }
            }
            return result;
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD");
            return new ArrayList<>();
        }
    }

    
    public List<Customer> getCustomersWhoReviewedProduct(String productId) {
        Set<String> customerIds = new HashSet<>();
        List<Review> allReviews = reviewTree.inOrder();
        
        for (Review r : allReviews) {
            if (r.getProductId().equals(productId)) {
                customerIds.add(r.getCustomerId());
            }
        }
        
        List<Customer> result = new ArrayList<>();
        for (String customerId : customerIds) {
            Customer temp = new Customer(customerId, "", "");
            Customer c = customerTree.search(temp);
            if (c != null) result.add(c);
        }
        return result;
    }

   
    private int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); 
            }
        }
    }

    private double getValidDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); 
            }
        }
    }

    public void addNewProduct() {
        System.out.print("Enter Product ID: ");
        String id = sc.next();
        System.out.print("Enter Name: ");
        String name = sc.next();
        double price = getValidDoubleInput("Enter Price: ");
        int stock = getValidIntInput("Enter Stock: ");
        
        Product p = new Product(id, name, price, stock);
        productTree.insert(p);
        System.out.println("Product added successfully.");
    }

    public void addNewCustomer() {
        System.out.print("Enter Customer ID: ");
        String id = sc.next();
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Email: ");
        String email = sc.next();
        
        Customer c = new Customer(id, name, email);
        customerTree.insert(c);
        System.out.println("Customer added successfully.");
    }

    public void displaySystemStats() {
        System.out.println("\n=== System Statistics ===");
        System.out.println("Total Products: " + productTree.size());
        System.out.println("Total Customers: " + customerTree.size());
        System.out.println("Total Orders: " + orderTree.size());
        System.out.println("Total Reviews: " + reviewTree.size());
        System.out.println("BST Height - Products: " + productTree.height());
        System.out.println("BST Height - Customers: " + customerTree.height());
        System.out.println("BST Height - Orders: " + orderTree.height());
    }

    public void showAllProducts() {
        List<Product> list = productTree.inOrder();
        System.out.println("\n=== All Products ===");
        for (Product p : list) System.out.println(p);
    }

    public void showAllCustomers() {
        List<Customer> list = customerTree.inOrder();
        System.out.println("\n=== All Customers ===");
        for (Customer c : list) System.out.println(c);
    }

    public void showAllOrders() {
        List<Order> list = orderTree.inOrder();
        System.out.println("\n=== All Orders ===");
        for (Order o : list) System.out.println(o);
    }

    public Product searchProductById(String id) {
        Product temp = new Product(id, "", 0, 0);
        return productTree.search(temp);
    }

    public Customer searchCustomerById(String id) {
        Customer temp = new Customer(id, "", "");
        return customerTree.search(temp);
    }

    public List<Customer> getCustomersSorted() {
        return customerTree.inOrder();
    }

    public void menu() {
        int ch;
        do {
            System.out.println("\n=== E-Commerce System ===");
            System.out.println("1. Show All Products");
            System.out.println("2. Show All Customers");
            System.out.println("3. Show All Orders");
            System.out.println("4. Search Product by ID");
            System.out.println("5. Search Customer by ID");
            System.out.println("6. Products in Price Range");
            System.out.println("7. Customers Sorted");
            System.out.println("8. Orders Between Dates");
            System.out.println("9. Top 3 Rated Products");
            System.out.println("10. Customers Who Reviewed Product");
            System.out.println("11. Add New Product");
            System.out.println("12. Add New Customer");
            System.out.println("13. System Statistics");
            System.out.println("14. Remove Product");
            System.out.println("15. Exit");
            System.out.print("Choose an option: ");
            
            try {
                ch = sc.nextInt();
                handleMenuChoice(ch);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number 1-15.");
                ch = 0;
                sc.next(); 
            }

        } while (ch != 15);
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1: showAllProducts(); break;
            case 2: showAllCustomers(); break;
            case 3: showAllOrders(); break;
            case 4: searchProductByIdMenu(); break;
            case 5: searchCustomerByIdMenu(); break;
            case 6: productsInPriceRangeMenu(); break;
            case 7: showCustomersSorted(); break;
            case 8: ordersBetweenDatesMenu(); break;
            case 9: showTopRatedProducts(); break;
            case 10: customersWhoReviewedProductMenu(); break;
            case 11: addNewProduct(); break;
            case 12: addNewCustomer(); break;
            case 13: displaySystemStats(); break;
            case 14: removeProductMenu(); break;
            case 15: System.out.println("Goodbye!"); break;
            default: System.out.println("Invalid choice! Please enter 1-15.");
        }
    }

    private void searchProductByIdMenu() {
        System.out.print("Enter Product ID: ");
        String pid = sc.next();
        Product p = searchProductById(pid);
        System.out.println(p != null ? p : "Product not found");
    }

    private void searchCustomerByIdMenu() {
        System.out.print("Enter Customer ID: ");
        String cid = sc.next();
        Customer c = searchCustomerById(cid);
        System.out.println(c != null ? c : "Customer not found");
    }

    private void productsInPriceRangeMenu() {
        double min = getValidDoubleInput("Enter min price: ");
        double max = getValidDoubleInput("Enter max price: ");
        List<Product> rangeProducts = getProductsInPriceRange(min, max);
        System.out.println("Products in range $" + min + " - $" + max + ":");
        for (Product prod : rangeProducts) System.out.println(prod);
    }

    private void showCustomersSorted() {
        List<Customer> sorted = getCustomersSorted();
        System.out.println("All Customers (Sorted by ID):");
        for (Customer cust : sorted) System.out.println(cust);
    }

    private void ordersBetweenDatesMenu() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String start = sc.next();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String end = sc.next();
        List<Order> dateOrders = getOrdersBetweenDates(start, end);
        System.out.println("Orders between " + start + " and " + end + ":");
        for (Order o : dateOrders) System.out.println(o);
    }

    private void showTopRatedProducts() {
        List<Product> top = getTopRatedProducts(3);
        System.out.println("Top 3 Rated Products:");
        for (int i = 0; i < top.size(); i++) {
            System.out.println((i+1) + ". " + top.get(i));
        }
    }

    private void customersWhoReviewedProductMenu() {
        System.out.print("Enter Product ID: ");
        String productId = sc.next();
        List<Customer> reviewers = getCustomersWhoReviewedProduct(productId);
        System.out.println("Customers who reviewed product " + productId + ":");
        for (Customer cust : reviewers) System.out.println(cust);
    }

    private void removeProductMenu() {
        System.out.print("Enter Product ID to remove: ");
        String productId = sc.next();
        Product temp = new Product(productId, "", 0, 0);
        if (productTree.remove(temp)) {
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void main(String[] args) {
        InventoryManagementSystem system = new InventoryManagementSystem();
        system.loadAll();
        system.menu();
        system.sc.close();
    }
}