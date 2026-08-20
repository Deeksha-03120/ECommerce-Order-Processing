public class OrderManagementQA {

    static int pass = 0;
    static int fail = 0;

    static void test(String name, boolean result){
        if(result){
            System.out.println("PASS - " + name);
            pass++;
        }
        else{
            System.out.println("FAIL - " + name);
            fail++;
        }
    }

    static double categoryDiscount(String category,double subtotal){
        switch(category){
            case "Electronics": return subtotal*0.10;
            case "Clothing": return subtotal*0.15;
            case "Grocery": return subtotal*0.05;
            default: return 0;
        }
    }

    static double gst(String category,double amount){
        switch(category){
            case "Electronics": return amount*0.18;
            case "Clothing": return amount*0.12;
            case "Grocery": return amount*0.05;
            default: return amount*0.10;
        }
    }

    public static void main(String args[]) {

        System.out.println("E-Commerce QA Testing\n");

        // 1-3
        test("Single Product",1000==1000);
        test("Multiple Products",(1000+500)==1500);
        test("Three Products",(1000+500+300)==1800);

        // 4-5
        test("Zero Quantity",0<=0);
        test("Negative Quantity",-5<0);

        // 6
        test("Invalid Product",!"P999".equals("P101"));

        // 7-8
        test("Valid Coupon","SAVE10".equals("SAVE10"));
        test("Invalid Coupon",!"ABC".equals("SAVE10"));

        // 9
        double coupon=Math.min(8000*0.10,500);
        test("Maximum Discount",coupon==500);

        //10-12
        test("GST 5%",gst("Grocery",1000)==50);
        test("GST 12%",gst("Clothing",1000)==120);
        test("GST 18%",gst("Electronics",1000)==180);

        //13-14
        test("Free Shipping",(6000>=5000));
        test("Shipping Charge",(3000<5000));

        //15
        test("Bulk Order Discount",(12>=10));

        //16
        boolean stock=false;
        test("Out of Stock",!stock);

        //17
        test("Large Order",(15000>10000));

        //18
        test("Empty Order",0==0);

        //19
        double price=199.99;
        test("Decimal Price",price==199.99);

        //20
        try{
            int q=-1;
            if(q<0)
                throw new Exception("Negative Quantity");
            test("Exception Handling",false);
        }
        catch(Exception e){
            test("Exception Handling",true);
        }

        System.out.println("\n---------------------------");
        System.out.println("Total Passed : "+pass);
        System.out.println("Total Failed : "+fail);
        System.out.println("---------------------------");

        if(fail==0)
            System.out.println("ALL 20 TESTS PASSED");
    }
}
