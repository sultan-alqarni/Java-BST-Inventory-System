# Advanced Inventory Management System (BST Edition) 🌳📦

An optimized, enterprise-level E-Commerce Inventory Management System built in Java. This project serves as an advanced iteration of a custom inventory system, migrating the core data storage from linear structures to generic Binary Search Trees (BST) for significantly improved search and retrieval performance.

## ✨ Core Features
* **Custom Binary Search Trees:** Implemented a generic `BST<T>` class from scratch to manage Products, Customers, Orders, and Reviews with $O(\log n)$ average time complexity.
* **Comparable Interfaces:** Deep integration of Java's `Comparable` interface to sort and organize complex objects dynamically within the trees.
* **Recursive Algorithms:** Utilizes advanced recursive tree traversals (In-Order) and depth calculations (Tree Height) for data processing and statistical reporting.
* **Data Persistence:** Seamlessly parses, loads, and relates data from external `.csv` files into the tree structures.

## 🛠️ Technologies & Concepts
* **Language:** Java
* **Advanced Data Structures:** Binary Search Trees (BST), Dynamic Arrays
* **Core Concepts:** Generics, Recursion, Object-Oriented Programming (OOP)

## 🚀 How to Run
1. Clone this repository to your local machine.
2. Ensure the required `.csv` data files are located in the root directory.
3. Compile the Java files:
   `javac *.java`
4. Run the system:
   `java InventoryManagementSystem`
