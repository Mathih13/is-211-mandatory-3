/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hp11c;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author evenal
 */
public class Hp11C {

    private Hp11cEngine engine;
    private Hp11cListStack<Float> stack;
    private Hp11cListStack<String> decimalStack;
    private Hp11cGui gui;
    
    
    private ArrayList engineStack = new ArrayList();
    private float currentNumber = 0;
    
    
    private boolean decimalInputMode = false;


    public Hp11C() {
        stack = new Hp11cListStack<>();
        decimalStack = new Hp11cListStack<>();
        engine = new Hp11cEngine() {
            @Override
            public void numberPressed(int number) {
                currentNumber = number;
                
                if (decimalInputMode) {
                    decimalStackInsert(currentNumber);
                }
            }

            @Override
            public void pointPressed() {
                 /**
                * decimal point was pressed. If the decimal point is pressed more than once
                * during the input of a number the second and subsequent presses are
                * ignored
                */
                 
                if (!decimalInputMode) {
                    decimalInputMode = true;
                    decimalStackInsert(currentNumber);
                    
                } else {
                    System.out.println("ignored");
                }
            }

            @Override
            public void chsPressed() {
                float v = stack.pop();
                v *= -1;
                stack.push(v);
                currentNumber = v;
            }

            @Override
            public void clearPressed() {
                stack = new Hp11cListStack<>();
                currentNumber = 0;
                decimalInputMode = false;
                decimalStack = new Hp11cListStack<>();
            }

            @Override
            public void plusPressed() {
                /*
                    Pressing an operator key (+ - * or /) implicitly pushes the number that is being entered. 
                    Then two numbers are popped, the operation is carried out. 
                    The result is pushed back onto the stack
                */
                
                // TODO: Pushing currentnumber is not necessary! Fix this dummy!
                stack.push(currentNumber);
                
                float one = stack.pop();
                float two = stack.pop();
                
                float operation = one + two;
                stack.push(operation);
                
                currentNumber = operation;
            }

            @Override
            public void minusPressed() {
                /*
                    Pressing an operator key (+ - * or /) implicitly pushes the number that is being entered. 
                    Then two numbers are popped, the operation is carried out. 
                    The result is pushed back onto the stack
                */
                
                stack.push(currentNumber);
                
                float one = stack.pop();
                float two = stack.pop();
                
                float operation = one - two;
                stack.push(operation);
                
                currentNumber = operation;
            }

            @Override
            public void multiplyPressed() {
                /*
                    Pressing an operator key (+ - * or /) implicitly pushes the number that is being entered. 
                    Then two numbers are popped, the operation is carried out. 
                    The result is pushed back onto the stack
                */
                
                stack.push(currentNumber);
                
                 float one = stack.pop();
                float two = stack.pop();
                
                float operation = one * two;
                stack.push(operation);
                
                currentNumber = operation;
            }

            @Override
            public void dividePressed() {
                /*
                    Pressing an operator key (+ - * or /) implicitly pushes the number that is being entered. 
                    Then two numbers are popped, the operation is carried out. 
                    The result is pushed back onto the stack
                */
                
                stack.push(currentNumber);
                
                 float one = stack.pop();
                float two = stack.pop();
                
                float operation = two / one;
                stack.push(operation);
                
                currentNumber = operation;
            }

            @Override
            public void enterPressed() {
                
                if (decimalInputMode) {
                    currentNumber = decimalStackCalculation();
                    decimalInputMode = false;
                }
                
                stack.push(currentNumber);
            }

            @Override
            public String getAuthor() {
                return "Mathih13";
            }

            /**
             * 
             * @return current number to be displayed in the calculator
             */
            @Override
            public String getDisplayValue() {
                
                if (currentNumber % 1 == 0.0) {
                    // We have a whole number. No reason to print
                    // "1.0" when it's 1, so let's cast it to an int
                    // and return that instead.
                    
                    int i;
                    i = (int) currentNumber;
                    return "" + i;
                }
                
               return "" + currentNumber;
            }

            @Override
            public String getTitle() {
                return "RPN Calculator";
            }

            @Override
            public String getVersion() {
                return "1.0.0";
            }
        };
        
        
        gui = new Hp11cGui(engine);
        gui.setVisible(true);
        
    }
    
    void decimalStackInsert(float number) {
        int i = (int) number;
        decimalStack.push("" + i);
    }
    
    /**
     * Pops all the elements of decimal stack and boils them down to a
     * float containing all of them.
     * @return the float to be used.
     */
    float decimalStackCalculation() {
        
        ArrayList<String> list = decimalStack.popAll();
        Collections.reverse(list); // Reverse this to make it easier to work with.
        
        // Get the first element and give it
        // a "." point, then remove it from the list.
        String result = list.get(0) + "."; 
        list.remove(list.get(0));
        
        
        // Append the rest of the numbers behind the
        // "." and return it as a float.
        for (int i = 0; i < list.size(); i++) {
            result += "" + list.get(i);
        }
        
        return new Float(result);
        
    }

    public static void main(String[] args) {
        new Hp11C();
    }
}
