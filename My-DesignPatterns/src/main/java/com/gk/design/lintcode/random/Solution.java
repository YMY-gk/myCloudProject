package com.gk.design.lintcode.random;

import java.util.*;

public class Solution {

    int random1 ;
    int random2 ;
    static Integer  random3=null ;
    static Integer  random4=null ;
    public Solution(){
        if(random3==null){
            random3 =new Random().nextInt();
        }
        if(random4==null){
            random4 =new Random().nextInt();
        }
        random1 =random3;
        random2 =random4;
    }
}
