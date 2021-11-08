package com.gk.design.arithmetic;

import com.sun.xml.internal.ws.util.StringUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/10/14 17:01
 * 描述
 * 请判定一个数独是否有效。
 *
 * 该数独可能只填充了部分数字，其中缺少的数字用 . 表示。
 *
 * 一个合法的数独（仅部分填充）并不一定是可解的。我们仅需使填充的空格有效即可。
 * 什么是 数独？
 *
 * http://sudoku.com.au/TheRules.aspx
 * http://baike.baidu.com/subview/961/10842669.htm
 */
public class Sudoku {
    /**
     * @param board: the board
     * @return: whether the Sudoku is valid
     */
    public static  boolean isValidSudoku(char[][] board) {
        // write your code here
        Map<Integer,String> ilist = new HashMap<>();
        Map<Integer,String> setj = new HashMap<>();
        Map<Integer,String> subMap = new HashMap<>();
        for (int i=0;i<board[1].length;i++){
            for (int j=0;j<board[1].length;j++){
                String d = String.valueOf(board[i][j]);
                String h=ilist.get(i);
                String l=setj.get(j);
                int su= (int) (Math.floor(Double.valueOf(i)/3)*3+Math.floor(Double.valueOf(j)/3));
                String sub =subMap.get(su);
                if (!d.equals(".")) {
                    if (h != null && h.contains(d)) {
                        System.out.println("h"+h+"  --d"+d);
                        System.out.println(ilist);
                        System.out.println(setj);
                        System.out.println(subMap);
                        return false;
                    }
                    if (l != null && l.contains(d)) {
                        System.out.println("l"+l+"  --d"+d);
                        System.out.println(ilist);
                        System.out.println(setj);
                        System.out.println(subMap);
                        return false;
                    }
                    if (sub != null && sub.contains(d)) {
                        System.out.println("sub"+sub+"  --d"+d);
                        System.out.println(ilist);
                        System.out.println(setj);
                        System.out.println(subMap);
                        return false;
                    }
                    if (h != null ) {
                        h += d;
                    }else{
                        h = d;
                    }
                    if (l != null ) {
                        l += d;
                    }else{
                        l = d;
                    }
                    if (sub != null ) {
                        sub += d;
                    }else{
                        sub = d;
                    }

                    setj.put(j, l);
                    ilist.put(i, h);
                    subMap.put(su, sub);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] board={"53..7....",
                        "6..195...",
                        ".98....6.",
                        "8...6...3",
                        "4..8.3..1",
                        "7...2...6",
                        ".6....28.",
                        "...419..5",
                        "....8..79"};
        //String[] board={"....5..1.",".4.3.....",".....3..1","8......2.","..2.7....",".15......",".....2...",".2.9.....","..4......"};
        //[". . . . 5 . . 1 .",
        //" . 4 . 3 . . . . .",
        // ". . . . . 3 . . 1",
        // "8 . . . . . . 2 .",
        // ". . 2 . 7 . . . .",
        // ". 1 5 . . . . . .",
        // ". . . . . 2 . . .",
        // ". 2 . 9 . . . . .",
        // ". . 4 . . . . . ."]
//["..4...63.",
// ".........",
// "5......9.",
// "...56....",
// "4.3.....1",
// "...7.....",
// "...5.....",
// ".........",
// "........."]
        char[][] a = new char[board.length][board.length];
        for (int i=0;i<board.length;i++){
            String b= board[i];
            char[] c= b.toCharArray();
            a[i] =c;
        }
        System.out.println(isValidSudoku(a));
    }
}