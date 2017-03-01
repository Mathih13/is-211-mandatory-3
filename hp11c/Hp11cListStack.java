/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hp11c;

import java.util.ArrayList;

/**
 *
 * @author mathiashartveit1
 */
class Hp11cListStack<T> {
    
    private class StackNode {
        T value;
        StackNode next;

        public StackNode(T value, StackNode next) {
            this.value = value;
            this.next = next;
        }
    }
    
    StackNode stack;
    
    public void push(T v) {
        stack = new StackNode(v, stack);
    }
    
    /**
     * Pop the top element of the list stack.
     * @return top element of stack
     */
    public T pop() {
        if (stack == null) throw new StackOverflowError("Underflow");
        
        T v = stack.value;
        stack = stack.next;
        return v;
    }
    
    /**
     * Grabs a copy of the stack as an Array List
     * @return ArrayList of all stack elements
     */
    public ArrayList<T> popAll() {
        ArrayList l = new ArrayList<>();
        
        while (stack != null) {
            l.add(pop());
        }
        
        return l;
    }
}
