public static void main(String args[]) {

    Product[] products = {
        new Product("P101", "electronics", 2, 2000, true),
        new Product("P102", "clothing", 3, 1000, true),
        new Product("P103", "grocery", 5, 200, true)
    };

    String coupon = "SAVE10";

    double subtotal = 0;
    double categoryDiscount = 0;
    double gst = 0;

    for(int i = 0; i < products.length; i++) {

        Product p = products[i];

        if(p.quantity <= 0) {
            System.out.println("Invalid Quantity!");
            return;
        }

        if(!p.inStock) {
            System.out.println("Product Out of Stock!");
            return;
        }

        double amount = p.quantity * p.unitPrice;

        subtotal += amount;
        categoryDiscount += calculateDiscount(p.category, amount);
        gst += calculateGST(
            p.category,
            amount - calculateDiscount(p.category, amount)
        );
    }

    double couponDisc = couponDiscount(coupon, subtotal);

    if(!coupon.equalsIgnoreCase("SAVE10"))
        System.out.println("Invalid Coupon!");

    int totalQty = 0;

    for(Product p : products)
        totalQty += p.quantity;

    double bulkDiscount = 0;

    if(totalQty >= 10)
        bulkDiscount = subtotal * 0.05;

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
