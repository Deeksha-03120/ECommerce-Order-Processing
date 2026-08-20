public class OrderManagementQA {

    static int passed = 0;
    static int failed = 0;

    static void test(String testName, boolean condition) {

        if(condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        }
        else {
            System.out.println("[FAIL] " + testName);
            failed++;
        }
    }

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   ORDER MANAGEMENT QA TESTING");
        System.out.println("====================================\n");


        // 1. Single product
        Product[] t1 = {
            new Product("P101", "electronics", 1, 1000, true)
        };

        test(
            "TC01 - Single Product",
            OrderManagement.calculateOrder(t1, "NONE") > 0
        );


        // 2. Multiple products
        Product[] t2 = {
            new Product("P101", "electronics", 2, 1000, true),
            new Product("P102", "clothing", 2, 500, true)
        };

        test(
            "TC02 - Multiple Products",
            OrderManagement.calculateOrder(t2, "NONE") > 0
        );


        // 3. Zero quantity
        Product[] t3 = {
            new Product("P101", "electronics", 0, 1000, true)
        };

        test(
            "TC03 - Zero Quantity",
            OrderManagement.calculateOrder(t3, "NONE") == -1
        );


        // 4. Negative quantity
        Product[] t4 = {
            new Product("P101", "electronics", -2, 1000, true)
        };

        test(
            "TC04 - Negative Quantity",
            OrderManagement.calculateOrder(t4, "NONE") == -1
        );


        // 5. Invalid product ID
        Product[] t5 = {
            new Product("X999", "electronics", 1, 1000, true)
        };

        test(
            "TC05 - Invalid Product",
            OrderManagement.calculateOrder(t5, "NONE") == -1
        );


        // 6. Electronics discount
        Product[] t6 = {
            new Product("P101", "electronics", 1, 1000, true)
        };

        double expectedElectronicsDiscount = 100;

        test(
            "TC06 - Electronics Discount",
            OrderManagement.calculateDiscount(
                "electronics", 1000
            ) == expectedElectronicsDiscount
        );


        // 7. Clothing discount
        test(
            "TC07 - Clothing Discount",
            OrderManagement.calculateDiscount(
                "clothing", 1000
            ) == 150
        );


        // 8. Grocery discount
        test(
            "TC08 - Grocery Discount",
            OrderManagement.calculateDiscount(
                "grocery", 1000
            ) == 50
        );


        // 9. Valid coupon
        test(
            "TC09 - Valid Coupon",
            OrderManagement.couponDiscount(
                "SAVE10", 1000
            ) == 100
        );


        // 10. Invalid coupon
        test(
            "TC10 - Invalid Coupon",
            OrderManagement.couponDiscount(
                "WELCOME", 1000
            ) == 0
        );


        // 11. Maximum coupon discount
        test(
            "TC11 - Maximum Coupon Discount",
            OrderManagement.couponDiscount(
                "SAVE10", 10000
            ) == 500
        );


        // 12. Coupon below maximum
        test(
            "TC12 - Coupon Below Maximum",
            OrderManagement.couponDiscount(
                "SAVE10", 2000
            ) == 200
        );


        // 13. Electronics GST
        test(
            "TC13 - Electronics GST",
            OrderManagement.calculateGST(
                "electronics", 1000
            ) == 180
        );


        // 14. Clothing GST
        test(
            "TC14 - Clothing GST",
            OrderManagement.calculateGST(
                "clothing", 1000
            ) == 120
        );


        // 15. Grocery GST
        test(
            "TC15 - Grocery GST",
            OrderManagement.calculateGST(
                "grocery", 1000
            ) == 50
        );


        // 16. Out of stock
        Product[] t16 = {
            new Product("P101", "electronics", 1, 1000, false)
        };

        test(
            "TC16 - Out of Stock",
            OrderManagement.calculateOrder(t16, "NONE") == -1
        );


        // 17. Free shipping
        Product[] t17 = {
            new Product("P101", "electronics", 1, 6000, true)
        };

        test(
            "TC17 - Free Shipping Threshold",
            OrderManagement.calculateOrder(t17, "NONE") > 0
        );


        // 18. Bulk order
        Product[] t18 = {
            new Product("P101", "electronics", 10, 100, true)
        };

        test(
            "TC18 - Bulk Order",
            OrderManagement.calculateOrder(t18, "NONE") > 0
        );


        // 19. Normal shipping
        Product[] t19 = {
            new Product("P101", "electronics", 1, 1000, true)
        };

        test(
            "TC19 - Normal Shipping",
            OrderManagement.calculateOrder(t19, "NONE") > 0
        );


        // 20. Multiple products + coupon + bulk order
        Product[] t20 = {
            new Product("P101", "electronics", 5, 1000, true),
            new Product("P102", "clothing", 5, 1000, true)
        };

        test(
            "TC20 - Multiple Products + Coupon + Bulk",
            OrderManagement.calculateOrder(t20, "SAVE10") > 0
        );


        // Final result
        System.out.println("\n====================================");
        System.out.println("Total Tests : " + (passed + failed));
        System.out.println("Passed      : " + passed);
        System.out.println("Failed      : " + failed);
        System.out.println("====================================");

        if(failed > 0) {
            System.out.println("QA RESULT: FAILED");
            System.exit(1);
        }
        else {
            System.out.println("QA RESULT: PASSED");
            System.exit(0);
        }
    }
}
