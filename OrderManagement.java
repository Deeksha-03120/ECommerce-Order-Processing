import java.util.*;

public class OrderManagement {

    static class Product {
        String id;
        String category;
        double price;
        int stock;

        Product(String id, String category, double price, int stock) {
            this.id = id;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }
    }

    static Map<String, Product> products = new HashMap<>();

    static {
        products.put("P101", new Product("P101", "Electronics", 1000, 20));
        products.put("P102", new Product("P102", "Clothing", 800, 30));
        products.put("P103", new Product("P103", "Grocery", 500, 50));
        products.put("P104", new Product("P104", "Books", 400, 25));
        products.put("P105", new Product("P105", "Electronics", 2000, 5));
    }

    public static double calculateOrder(String[] productIds, int[] quantities,
                                        String coupon) {

        double subtotal = 0;
        int totalQuantity = 0;

        for (int i = 0; i < productIds.length; i++) {

            if (!products.containsKey(productIds[i])) {
                throw new IllegalArgumentException(
                        "Invalid product: " + productIds[i]);
            }

            if (quantities[i] <= 0) {
                throw new IllegalArgumentException(
                        "Quantity must be greater than zero");
            }

            Product p = products.get(productIds[i]);

            if (quantities[i] > p.stock) {
                throw new IllegalArgumentException(
                        "Out of stock: " + productIds[i]);
            }

            subtotal += p.price * quantities[i];
            totalQuantity += quantities[i];
        }

        double categoryDiscount = 0;

        for (int i = 0; i < productIds.length; i++) {

            Product p = products.get(productIds[i]);
            double amount = p.price * quantities[i];

            if (p.category.equalsIgnoreCase("Electronics")) {
                categoryDiscount += amount * 0.10;
            } else if (p.category.equalsIgnoreCase("Clothing")) {
                categoryDiscount += amount * 0.15;
            } else if (p.category.equalsIgnoreCase("Books")) {
                categoryDiscount += amount * 0.05;
            }
        }

        double bulkDiscount = 0;

        if (totalQuantity >= 10) {
            bulkDiscount = subtotal * 0.05;
        }

        double couponDiscount = 0;

        if (coupon.equalsIgnoreCase("SAVE10")) {
            couponDiscount = subtotal * 0.10;
        } else if (coupon.equalsIgnoreCase("SAVE20")) {
            couponDiscount = subtotal * 0.20;
        } else if (!coupon.equalsIgnoreCase("NONE")) {
            throw new IllegalArgumentException("Invalid coupon code");
        }

        double totalDiscount =
                categoryDiscount + bulkDiscount + couponDiscount;

        double maximumDiscount = subtotal * 0.30;

        if (totalDiscount > maximumDiscount) {
            totalDiscount = maximumDiscount;
        }

        double amountAfterDiscount = subtotal - totalDiscount;

        double gst = amountAfterDiscount * 0.18;

        double shipping;

        if (amountAfterDiscount >= 5000) {
            shipping = 0;
        } else {
            shipping = 100;
        }

        double finalAmount =
                amountAfterDiscount + gst + shipping;

        System.out.println("----------------------------------");
        System.out.println("E-COMMERCE ORDER");
        System.out.println("----------------------------------");
        System.out.println("Products         : " +
                Arrays.toString(productIds));
        System.out.println("Quantities       : " +
                Arrays.toString(quantities));
        System.out.println("Coupon           : " + coupon);
        System.out.println("Subtotal         : " + subtotal);
        System.out.println("Category Discount: " + categoryDiscount);
        System.out.println("Bulk Discount    : " + bulkDiscount);
        System.out.println("Coupon Discount  : " + couponDiscount);
        System.out.println("Total Discount   : " + totalDiscount);
        System.out.println("GST (18%)        : " + gst);
        System.out.println("Shipping Charge  : " + shipping);
        System.out.println("Final Amount     : " + finalAmount);
        System.out.println("----------------------------------");

        return finalAmount;
    }

    public static void main(String[] args) {

        String[] productIds = {"P101", "P102", "P104"};
        int[] quantities = {2, 3, 2};
        String coupon = "SAVE10";

        try {

            double amount = calculateOrder(
                    productIds,
                    quantities,
                    coupon);

            System.out.println("ORDER PROCESSED SUCCESSFULLY");
            System.out.println("Amount to Pay: " + amount);

        } catch (Exception e) {

            System.out.println("ORDER FAILED");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}
