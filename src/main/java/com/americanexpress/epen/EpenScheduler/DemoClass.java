package com.americanexpress.epen.EpenScheduler;

import java.util.ArrayList;

public class DemoClass {

    public static void main(String[] args){

    }

    public static String nextPermutation(int[] nums, int index, String perm){


        for (int i = index+1; i < nums.length; i++){
            String temp = perm + nums[i];
            nextPermutation(nums, i, temp);
        }

        return null;
    }
}
