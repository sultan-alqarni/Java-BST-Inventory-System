package csc212pro2;

import java.util.ArrayList;
import java.util.List;

public class BST<T extends Comparable<T>> {
    private Node root;
    private int size;

    private class Node {
        private T data;
        private Node left, right;

        Node(T data) {
            this.data = data;
        }
    }

    public void insert(T data) {
        root = insertRec(root, data);
        size++;
    }

    private Node insertRec(Node root, T data) {
        if (root == null) {
            return new Node(data);
        }

        if (data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    public T search(T data) {
        return searchRec(root, data);
    }

    private T searchRec(Node root, T data) {
        if (root == null) return null;
        if (data.compareTo(root.data) == 0) return root.data;
        return data.compareTo(root.data) < 0 ? 
               searchRec(root.left, data) : 
               searchRec(root.right, data);
    }

    public boolean remove(T data) {
        int oldSize = size;
        root = removeRec(root, data);
        return size != oldSize;
    }

    private Node removeRec(Node root, T data) {
        if (root == null) return null;

        if (data.compareTo(root.data) < 0) {
            root.left = removeRec(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = removeRec(root.right, data);
        } else {
           
            size--;
            
           
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            
          
            T minValue = findMin(root.right);
            root.data = minValue;
            root.right = removeRec(root.right, minValue);
        }
        return root;
    }

    private T findMin(Node root) {
        return root.left == null ? root.data : findMin(root.left);
    }

    public T findMax() {
        if (root == null) return null;
        return findMaxRec(root);
    }

    private T findMaxRec(Node root) {
        return root.right == null ? root.data : findMaxRec(root.right);
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node root) {
        if (root == null) return -1;
        return 1 + Math.max(heightRec(root.left), heightRec(root.right));
    }

    public List<T> inOrder() {
        List<T> list = new ArrayList<>();
        inOrderRec(root, list);
        return list;
    }

    private void inOrderRec(Node root, List<T> list) {
        if (root != null) {
            inOrderRec(root.left, list);
            list.add(root.data);
            inOrderRec(root.right, list);
        }
    }

    
    public List<T> rangeQuery(T min, T max) {
        List<T> result = new ArrayList<>();
        rangeQueryRec(root, min, max, result);
        return result;
    }

    private void rangeQueryRec(Node root, T min, T max, List<T> result) {
        if (root == null) return;

        if (root.data.compareTo(min) > 0) {
            rangeQueryRec(root.left, min, max, result);
        }

        if (root.data.compareTo(min) >= 0 && root.data.compareTo(max) <= 0) {
            result.add(root.data);
        }

        if (root.data.compareTo(max) < 0) {
            rangeQueryRec(root.right, min, max, result);
        }
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
}