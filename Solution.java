import java.util.*;
public class Solution {
    public static int[] twoSum(int[] var0, int var1) {
        int[] var2 = new int[2];
        int var3 = var0.length;
        HashMap var4 = new HashMap();

        for(int var5 = 0; var5 < var3; ++var5) {
            int var6 = var1 - var0[var5];
            if (var4.get(var6) != null) {
                var2[1] = var5;
                int var7 = (Integer)var4.get(var6);
                var2[0] = (Integer)var4.get(var6);
                return var2;
            }

            var4.put(var0[var5], var5);
        }

        return null;
    }public static void main(String[] args) {
    int target = Integer.parseInt(args[1]);
    String[] strNumbers = args[0].split(" ");
    int[] nums = new int[strNumbers.length];
    for (int i = 0; i < strNumbers.length; i++) {
        nums[i] = Integer.parseInt(strNumbers[i].trim());
        //  System.out.println("nums: " + nums[i] );
    }
    int[] result = twoSum(nums,target);
    System.out.println("Indices: " + result[0] + ", " + result[1]);

}        }
