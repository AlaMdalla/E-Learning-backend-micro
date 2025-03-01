public static void main(String[] args) {
    if (args.length < 2) {
        System.out.println("Usage: <numbers> <target>");
        return;
    }

    try {
        int target = Integer.parseInt(args[1]);
        String[] strNumbers = args[0].split(" ");
        int[] nums = new int[strNumbers.length];

        for (int i = 0; i < strNumbers.length; i++) {
            nums[i] = Integer.parseInt(strNumbers[i].trim());
        }

        int[] result = threeSum(nums, target);
        if (result.length == 3) {
            System.out.println("Indices: " + result[0] + ", " + result[1] + ", " + result[2]);
        } else {
            System.out.println("No solution found.");
        }
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter valid numbers.");
    }
}