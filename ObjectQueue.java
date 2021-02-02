// ObjectQueue.java
/**
 * ObjectQueue class creates the blueprint for a queue data structure.
 * 
 */
public class ObjectQueue implements ObjectQueueInterface{
    private Object[] item;
    private int front;
    private int rear;
    private int count;

    /**
     * Constructor for objects of class ObjectQueue.
     */
    public ObjectQueue() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }

    /**
     * Checks if array has any contents.
     * 
     * @return      boolean true if empty false if not
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Checks if array cannot contain any more values.
     * 
     * @return      true if full, false if not
     */
    public boolean isFull() {
        return count == item.length;
    }

    /**
     * Clears an array and creates a new array in it's place. Resets array pointers and count.
     */
    public void clear() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }

    /**
     * Inserts an Object into the rear of the array.
     * 
     * @param       Object to be inserted
     */
    public void insert(Object o) {
        if (isFull())
            resize(2 * item.length);
        rear = (rear+1) % item.length;
        item[rear] = o;
        ++count;
    }

    /**
     * Removes an Object from the front of the array.
     * 
     * @return      Object that was removed.
     */
    public Object remove() {
        if (isEmpty()) {     
            new Exception("Remove Runtime Error: Queue Underflow").printStackTrace();     
            System.exit(1); 
        }
        Object temp = item[front];
        item[front] = null;
        front = (front+1) % item.length;
        --count;
        if (count == item.length/4 && item.length != 1)
            resize(item.length/2);
        return temp;
    }

    /**
     * Returns an Object at the front of the array without removing it.
     * 
     * @return      Object at the front of the array
     */
    public Object query() {
        if (isEmpty()) {     
            new Exception("Remove Runtime Error: Queue Underflow").printStackTrace();     
            System.exit(1); 
        }
        return item[front];
    }

    /**
     * Scales the array to a new size by creating a new array.
     * 
     * @param       int size for array to be set to
     */
    private void resize(int size) {
        Object[] temp = new Object[size];
        for (int i = 0; i < count; ++i) {
            temp[i] = item[front];
            front = (front+1) % item.length;
        }
        front = 0;
        rear = count-1;
        item = temp;
    }
}


