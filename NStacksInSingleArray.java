import java.util.*;
import java.lang.*;
import java.io.*;

class MyStack
{
    private class StackInfo {
        private int start;
        private int size;
        private int capacity;
        
        public StackInfo(int start, int capacity) {
            this.start = start;
            this.capacity = capacity;
            this.size = 0;
        }
        
        private boolean isWithinStackCapacity(int index) {
            if(index < 0 && index > values.length)
                return false;
            
            int continuousIndex = (index < start)? index + values.length :  index;
            int end = start + capacity;
            return start <= continuousIndex && continuousIndex < end;
        }
        
        private int lastCapacityIndex() {
            return adjustIndex(start + capacity - 1);
        }
        
        private int lastSizeIndex() {
            return adjustIndex(start + size -1);
        }
        
        private boolean isFull() {
            return size == capacity;
        }
        
        private boolean isEmpty() {
            return size == 0;
        }
    }
    private int[] values;
    private StackInfo[] infos;
    
    public MyStack(int defaultStackSize, int numberOfStack) {
        values= new int[numberOfStack * defaultStackSize];
        infos = new StackInfo[numberOfStack];
        for(int i=0;i<numberOfStack;i++) {
            infos[i] = new StackInfo(i*defaultStackSize, defaultStackSize);
        }
    }
    
    private int adjustIndex(int index) {
        int max = values.length;
        return (index%max + max)%max;
    }
    
    private int previousIndex(int index) {
        return adjustIndex(index-1);
    }
    
    private int nextIndex(int index) {
        return adjustIndex(index+1);
    }
    
    private int numberOfElements() {
        int totalElements = 0;
        for(StackInfo info : infos) {
            totalElements += info.size;
        }
        return totalElements;
    }
    
    private boolean areAllStackFull() {
        return numberOfElements() == values.length;
    }
    
    private void shift(int stackNumber) {
        StackInfo stack = infos[stackNumber];
        if(stack.size == stack.capacity) {
            shift((stackNumber+1)%infos.length);
            stack.capacity++;
        }
        int index = nextIndex(stack.lastSizeIndex());
        while(stack.isWithinStackCapacity(index)) {
           values[index] = values[previousIndex(index)];
           index = previousIndex(index);
        }
        values[stack.start] = 0;
        stack.start = nextIndex(stack.start);
        stack.capacity--;
    }
    
    private void expand(int stackNumber) {
        shift((stackNumber + 1)%infos.length);
        infos[stackNumber].capacity++;
    }
    
    public boolean push(int stackNumber, int value) {
        if(areAllStackFull())
            return false;
        StackInfo stack = infos[stackNumber];
        if(stack.isFull())
            expand(stackNumber);
        stack.size++;
        values[stack.lastSizeIndex()] = value;
        return true;
    }
    
    public int pop(int stackNumber) {
        StackInfo stack = infos[stackNumber];
        if(stack.isEmpty())
            return -1;
            
        int value = values[stack.lastSizeIndex()];
        values[stack.lastSizeIndex()] = 0;
        stack.size--;
        return value;
    }
    
	public static void main (String[] args) throws java.lang.Exception
	{
    	MyStack myStack = new MyStack(3, 3);
    	myStack.push(0, 1);
    	myStack.push(1, 2);
    	myStack.push(2, 3);
    	myStack.push(2, 4);
    	myStack.push(0, 5);
    	myStack.push(2, 6);
    	myStack.push(0, 7);
    	myStack.push(1, 8);
    	myStack.push(2, 9);
    	System.out.println(myStack.pop(2));
    	System.out.println(myStack.pop(2));
	}
}
