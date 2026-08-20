import java.util.*;

class Product {
    String productId;
    String category;
    int quantity;
    double unitPrice;
    boolean inStock;

    Product(String id, String cat, int qty, double price, boolean stock) {
        productId = id;
        category = cat;
        quantity = qty;
        unitPrice = price;
        inStock = stock;
    }
}

public class OrderManagement {

    static double calculateDiscount(String category, double subtotal) {
        switch(category.toLowerCase()) {
            case "electronics": return subtotal * 0.10;
            case "clothing": return subtotal * 0.15;
            case "grocery": return subtotal * 0.05;
            default: return 0;
        }
    }

    static double calculateGST(String category, double amount) {
        switch(category.toLowerCase()) {
            case "electronics": return amount * 0.18;
            case "clothing": return amount * 0.12;
            case "grocery": return amount * 0.05;
            default: return amount * 0.10;
        }
    }

    static double couponDiscount(String coupon, double amount) {
        if(coupon.equalsIgnoreCase("SAVE10"))
            return Math.min(amount * 0.10, 500);
        return 0;
    }

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Number of Products: ");
        int n = sc.nextInt();

        Product[] products = new Product[n];

        double subtotal = 0;
        double categoryDiscount = 0;
        double gst = 0;

        for(int i=0;i<n;i++) {

            System.out.println("\nProduct " + (i+1));

            System.out.print("Product ID: ");
            String id = sc.next();

            System.out.print("Category: ");
            String cat = sc.next();

            System.out.print("Quantity: ");
            int qty = sc.nextInt();

            if(qty <= 0){
                System.out.println("Invalid Quantity!");
                return;
            }

            System.out.print("Unit Price: ");
            double price = sc.nextDouble();

            System.out.print("In Stock (true/false): ");
            boolean stock = sc.nextBoolean();

            if(!stock){
                System.out.println("Product Out of Stock!");
                return;
            }

            products[i] = new Product(id, cat, qty, price, stock);

            double amount = qty * price;
            subtotal += amount;
            categoryDiscount += calculateDiscount(cat, amount);
            gst += calculateGST(cat, amount - calculateDiscount(cat, amount));
        }

        System.out.print("\nEnter Coupon Code: ");
        String coupon = sc.next();

        double couponDisc = couponDiscount(coupon, subtotal);

        if(!coupon.equalsIgnoreCase("SAVE10"))
            System.out.println("Invalid Coupon!");

        double bulkDiscount = 0;

        int totalQty = 0;
        for(Product p : products)
            totalQty += p.quantity;

        if(totalQty >= 10)
            bulkDiscount = subtotal * 0.05;

        double shipping = (subtotal >= 5000) ? 0 : 100;

        double finalAmount = subtotal - categoryDiscount - couponDisc - bulkDiscount + gst + shipping;

        System.out.println("\n------- ORDER SUMMARY -------");
        System.out.println("Subtotal            : " + subtotal);
        System.out.println("Category Discount   : " + categoryDiscount);
        System.out.println("Coupon Discount     : " + couponDisc);
        System.out.println("Bulk Discount       : " + bulkDiscount);
        System.out.println("GST                 : " + gst);
        System.out.println("Shipping Charge     : " + shipping);
        System.out.println("-----------------------------");
        System.out.println("Final Amount        : " + finalAmount);

        sc.close();
    }
}
