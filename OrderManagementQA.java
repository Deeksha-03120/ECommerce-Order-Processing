public class OrderManagementQA {

    static int passed = 0;
    static int failed = 0;

    public static void runTest(int testNo, String description,
                               String[] products, int[] quantities,
                               String coupon, boolean shouldPass) {

        System.out.println("\nTest " + testNo + ": " + description);

        try {
            double result = OrderManagement.calculateOrder(
                    products, quantities, coupon);

            if (shouldPass) {
                System.out.println("Result: PASS");
                System.out.println("Final Amount: " + result);
                passed++;
            } else {
                System.out.println("Result: FAIL");
                failed++;
            }

        } catch (Exception e) {

            if (!shouldPass) {
                System.out.println("Result: PASS");
                System.out.println("Expected Error: " + e.getMessage());
                passed++;
            } else {
                System.out.println("Result: FAIL");
                System.out.println("Unexpected Error: " + e.getMessage());
                failed++;
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("E-COMMERCE QA TESTING");
        System.out.println("====================================");

        runTest(1, "Single Electronics Product",
                new String[]{"P101"}, new int[]{1}, "NONE", true);

        runTest(2, "Single Clothing Product",
                new String[]{"P102"}, new int[]{2}, "NONE", true);

        runTest(3, "Single Grocery Product",
                new String[]{"P103"}, new int[]{3}, "NONE", true);

        runTest(4, "Single Book Product",
                new String[]{"P104"}, new int[]{1}, "NONE", true);

        runTest(5, "Multiple Products",
                new String[]{"P101", "P102"},
                new int[]{2, 3}, "NONE", true);

        runTest(6, "Multiple Electronics Products",
                new String[]{"P101", "P105"},
                new int[]{2, 1}, "NONE", true);

        runTest(7, "SAVE10 Coupon",
                new String[]{"P101"},
                new int[]{2}, "SAVE10", true);

        runTest(8, "SAVE20 Coupon",
                new String[]{"P102"},
                new int[]{2}, "SAVE20", true);

        runTest(9, "Invalid Coupon",
                new String[]{"P101"},
                new int[]{1}, "INVALID", false);

        runTest(10, "Zero Quantity",
                new String[]{"P101"},
                new int[]{0}, "NONE", false);

        runTest(11, "Negative Quantity",
                new String[]{"P101"},
                new int[]{-2}, "NONE", false);

        runTest(12, "Invalid Product",
                new String[]{"P999"},
                new int[]{1}, "NONE", false);

        runTest(13, "Out of Stock",
                new String[]{"P105"},
                new int[]{10}, "NONE", false);

        runTest(14, "Maximum Discount Test",
                new String[]{"P101", "P102"},
                new int[]{5, 5}, "SAVE20", true);

        runTest(15, "Tax Calculation",
                new String[]{"P103"},
                new int[]{4}, "NONE", true);

        runTest(16, "Free Shipping",
                new String[]{"P101", "P105"},
                new int[]{3, 2}, "NONE", true);

        runTest(17, "Bulk Order",
                new String[]{"P103"},
                new int[]{10}, "NONE", true);

        runTest(18, "Bulk Order with Coupon",
                new String[]{"P102"},
                new int[]{10}, "SAVE10", true);

        runTest(19, "Multiple Categories",
                new String[]{"P101", "P102", "P103", "P104"},
                new int[]{1, 1, 1, 1}, "NONE", true);

        runTest(20, "Large Valid Order",
                new String[]{"P101", "P102", "P103"},
                new int[]{3, 4, 5}, "SAVE10", true);

        System.out.println("\n====================================");
        System.out.println("QA TEST SUMMARY");
        System.out.println("====================================");
        System.out.println("Total Tests  : 20");
        System.out.println("Passed       : " + passed);
        System.out.println("Failed       : " + failed);

        if (failed == 0) {
            System.out.println("ALL QA TESTS PASSED");
        } else {
            System.out.println("SOME QA TESTS FAILED");
        }
    }
}
