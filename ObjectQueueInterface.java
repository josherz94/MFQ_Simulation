/**
 * Interface for the ObjectQueue class to implement.
 *
 * @author Joshua Matson id# 012024010
 * @version 4/5/2019
 */
public interface ObjectQueueInterface
{   
    // subclass ObjectStack implements these methods
    boolean isEmpty();
    
    boolean isFull();
    
    void clear();
    
    void insert(Object o);
    
    Object remove();
    
    Object query();
}
