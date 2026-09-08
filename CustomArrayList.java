package csc212pro2;

public class CustomArrayList {
    private Object[] elements;
    private int size;
    private int maxSize;
    
    public CustomArrayList() {
        this.maxSize = 50;
        this.elements = new Object[this.maxSize];
        this.size = 0;
    }
    
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        this.maxSize = initialCapacity;
        this.elements = new Object[this.maxSize];
        this.size = 0;
    }
    
    public void add(Object element) {
        if (size == maxSize) {
            resize();
        }
        elements[size++] = element;
    }
    
    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elements[index];
    }
    
    public boolean remove(Object element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] != null && elements[i].equals(element)) {
                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }
                elements[--size] = null;
                return true;
            }
        }
        return false;
    }
    
    public int size() { return size; }
    public int getMaxSize() { return maxSize; }
    public boolean isEmpty() { return size == 0; }
    
    private void resize() {
        int newCapacity = maxSize * 2;
        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
        maxSize = newCapacity;
    }
    
    public boolean contains(Object element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] != null && elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }
}