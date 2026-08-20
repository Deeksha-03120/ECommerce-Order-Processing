class Product {
    String productId;
    String category;
    int quantity;
    double unitPrice;
    boolean inStock;

    Product(String productId, String category, int quantity,
            double unitPrice, boolean inStock) {

        this.productId = productId;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.inStock = inStock;
    }
}

public class OrderManagement {

    // Check whether product ID is valid
    static boolean isValidProduct(String productId) {
        return productId != null &&
               productId.matches("P[0-9]+");
    }

    // Category-specific discount
    static double calculateDiscount(String category, double amount) {

        switch(category.toLowerCase()) {

            case "electronics":
                return amount * 0.10;

            case "clothing":
                return amount * 0.15;

            case "grocery":
                return amount * 0.05;

            default:
                return 0;
        }
    }

    // GST calculation
    static double calculateGST(String category, double amount) {

        switch(category.toLowerCase()) {

            case "electronics":
                return amount * 0.18;

            case "clothing":
                return amount * 0.12;

            case "grocery":
                return amount * 0.05;

            default:
                return amount * 0.10;
        }
    }

    // Coupon discount
    // SAVE10 = 10%, maximum ₹500
    static double couponDiscount(String coupon, double amount) {

        if(coupon != null && coupon.equalsIgnoreCase("SAVE10")) {
            return Math.min(amount * 0.10, 500);
        }

        return 0;
    }

    // Calculate complete order
    static double calculateOrder(Product[] products, String coupon) {

        double subtotal = 0;
        double categoryDiscount = 0;
        double gst = 0;

        for(Product p : products) {

            if(!isValidProduct(p.productId)) {
                System.out.println("Invalid Product: " + p.productId);
                return -1;
            }

            if(p.quantity <= 0) {
                System.out.println("Invalid Quantity for " + p.productId);
                return -1;
            }

            if(p.unitPrice <= 0) {
                System.out.println("Invalid Price for " + p.productId);
                return -1;
            }

            if(!p.inStock) {
                System.out.println("Product Out of Stock: " + p.productId);
                return -1;
            }

            double amount = p.quantity * p.unitPrice;

            subtotal += amount;

            double discount = calculateDiscount(
                p.category, amount
            );

            categoryDiscount += discount;

            double taxableAmount = amount - discount;

            gst += calculateGST(
                p.category, taxableAmount
            );
        }

        // Coupon validation
        double couponDisc = couponDiscount(coupon, subtotal);

        if(coupon != null &&
           !coupon.equalsIgnoreCase("SAVE10") &&
           !coupon.equalsIgnoreCase("NONE")) {

            System.out.println("Invalid Coupon Code");
            return -1;
        }

        // Bulk discount
        int totalQuantity = 0;

        for(Product p : products) {
            totalQuantity += p.quantity;
        }

        double bulkDiscount = 0;

        if(totalQuantity >= 10) {
            bulkDiscount = subtotal * 0.05;
        }

        // Free shipping for subtotal >= ₹5000
        double shipping;

        if(subtotal >= 5000) {
            shipping = 0;
        } else {
            shipping = 100;
        }

        double finalAmount =
            subtotal
            - categoryDiscount
            - couponDisc
            - bulkDiscount
            + gst
            + shipping;

        return finalAmount;
    }

    // Display order summary
    static void displayOrder(Product[] products, String coupon) {

        double subtotal = 0;
        double categoryDiscount = 0;
        double gst = 0;

        int totalQuantity = 0;

        for(Product p : products) {

            double amount = p.quantity * p.unitPrice;

            subtotal += amount;

            categoryDiscount +=
                calculateDiscount(p.category, amount);

            gst += calculateGST(
                p.category,
                amount - calculateDiscount(p.category, amount)
            );

            totalQuantity += p.quantity;
        }

        double couponDisc = couponDiscount(coupon, subtotal);

        double bulkDiscount = 0;

        if(totalQuantity >= 10) {
            bulkDiscount = subtotal * 0.05;
        }

        double shipping = (subtotal >= 5000) ? 0 : 100;

        double finalAmount =
            subtotal
            - categoryDiscount
            - couponDisc
            - bulkDiscount
            + gst
            + shipping;

        System.out.println("\n------- ORDER SUMMARY -------");
        System.out.println("Subtotal            : " + subtotal);
        System.out.println("Category Discount   : " + categoryDiscount);
        System.out.println("Coupon Discount     : " + couponDisc);
        System.out.println("Bulk Discount       : " + bulkDiscount);
        System.out.println("GST                 : " + gst);
        System.out.println("Shipping Charge     : " + shipping);
        System.out.println("-----------------------------");
        System.out.println("Final Amount        : " + finalAmount);
    }

    public static void main(String[] args) {

        // Normal development test order
        Product[] products = {

            new Product(
                "P101",
                "electronics",
                2,
                2000,
                true
            ),

            new Product(
                "P102",
                "clothing",
                3,
                1000,
                true
            ),

            new Product(
                "P103",
                "grocery",
                5,
                200,
                true
            )
        };

        String coupon = "SAVE10";

        double result = calculateOrder(products, coupon);

        if(result != -1) {
            displayOrder(products, coupon);
        }
    }
}
