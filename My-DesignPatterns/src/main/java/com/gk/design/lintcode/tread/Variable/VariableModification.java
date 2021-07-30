package com.gk.design.lintcode.tread.Variable;

public class VariableModification {
    private int i;
    // write your code

    public VariableModification() {
        this.i = 0;
        // write your code

    }

    public void add1() throws Exception{
        // modify the following code
        synchronized (VariableModification.class) {
            Main.increase(this.i) ;
            this.i += 1;
        }
    }

    public void add2() throws Exception{
        // modify the following code
        synchronized (VariableModification.class) {
            Main.increase(this.i) ;
            this.i += 1;
        }
    }

    public void sub1() throws Exception{
        // modify the following code
        synchronized (VariableModification.class) {
            Main.decrease(this.i) ;
            this.i -= 1;
        }
    }

    public void sub2() throws Exception{
        // modify the following code
        synchronized (VariableModification.class) {
            Main.decrease(this.i) ;
            this.i -= 1;
        }
    }

    public int checkI(){
        return i;
    }
}