package com.studycode.store.resources;

import com.studycode.store.R;
import com.studycode.store.models.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Products {
    public HashMap<String, Product[]> PRODUCT_MAP = new HashMap<>();

    public Products() {
        PRODUCT_MAP.put("Phone Case", PHONE_CASES);
        PRODUCT_MAP.put("Hoody", HOODIES);
        PRODUCT_MAP.put("T-Shirt", T_SHIRTS);
        PRODUCT_MAP.put("Snapback", SNAPBACKS);
        PRODUCT_MAP.put("Trucker Hat", TRUCKER_HATS);
    }

    public static final Product T_SHIRT_WHITE = new Product("White T-Shirt", R.drawable.t_shirt_white, "T-Shirt",
            new BigDecimal(23.99), 2141515);

    public static final Product T_SHIRT_GREY = new Product("Grey T-Shirt", R.drawable.t_shirt_grey, "T-Shirt",
            new BigDecimal(23.99), 2141517);
    public static final Product T_SHIRT_NAVY = new Product("Navy T-Shirt", R.drawable.t_shirt_navy, "T-Shirt",
            new BigDecimal(23.99), 2141518);



    public static final Product CELL_PHONE_CASE_1 = new Product("Cell Phone Case 1", R.drawable.cell_phone_case_1, "Phone Case",
            new BigDecimal(10.99), 1515611);


    public static final Product HOODY_NAVY = new Product("Navy Hoody", R.drawable.hoody_navy, "Hoody", new BigDecimal(34.99)
            , 7725277);

    public static final Product HOODY_BLACK = new Product("Black Hoody", R.drawable.hoody_black, "Hoody",
            new BigDecimal(34.99), 7725279);
    public static final Product HOODY_GREY = new Product("Grey Hoody", R.drawable.hoody_grey, "Hoody",
            new BigDecimal(34.99), 7725280);
    public static final Product HOODY_PURPLE = new Product("Purple Hoody", R.drawable.hoody_purple, "Hoody",
            new BigDecimal(34.99), 7725281);




    public static final Product TRUCKER_HAT_NAVY = new Product("Navy Trucker Hat", R.drawable.tucker_hat_baige,
            "Trucker Hat", new BigDecimal(25.99), 783736);
    public static final Product TRUCKER_HAT_WHITE = new Product("White Trucker Hat", R.drawable.tucker_hat_black,
            "Trucker Hat", new BigDecimal(25.99), 783737);
    public static final Product TRUCKER_HAT_BLACK = new Product("Black Trucker Hat", R.drawable.tucker_hat_navy,
            "Trucker Hat", new BigDecimal(25.99), 783738);
    public static final Product TRUCKER_HAT_BAIGE = new Product("Baige Trucker Hat", R.drawable.tucker_hat_white,
            "Trucker Hat", new BigDecimal(25.99), 783739);

    public static final Product SNAPBACK_BLACK = new Product("Black Snapback", R.drawable.snapback_black,
            "Snapback", new BigDecimal(20.99), 9377376);
    public static final Product SNAPBACK_CAMO = new Product("Camo Snapback", R.drawable.snapback_camo,
            "Snapback", new BigDecimal(20.99), 9377377);
    public static final Product SNAPBACK_GREY = new Product("Grey Snapback", R.drawable.snapback_grey,
            "Snapback", new BigDecimal(20.99), 9377378);
    public static final Product SNAPBACK_NAVY = new Product("Navy Snapback", R.drawable.snapback_navy,
            "Snapback", new BigDecimal(20.99), 9377379);
    public static final Product SNAPBACK_RED = new Product("Red Snapback", R.drawable.snapback_red,
            "Snapback", new BigDecimal(20.99), 9377380);
    public static final Product SNAPBACK_TEAL = new Product("Teal Snapback", R.drawable.snapback_teal,
            "Snapback", new BigDecimal(20.99), 9377381);



    public static final Product[] T_SHIRTS = {T_SHIRT_WHITE, T_SHIRT_GREY, T_SHIRT_NAVY};



    public static final Product[] TRUCKER_HATS = {TRUCKER_HAT_NAVY, TRUCKER_HAT_BLACK, TRUCKER_HAT_WHITE, TRUCKER_HAT_BAIGE};

    public static final Product[] SNAPBACKS = {SNAPBACK_NAVY, SNAPBACK_BLACK, SNAPBACK_CAMO, SNAPBACK_GREY, SNAPBACK_RED, SNAPBACK_TEAL};

    public static final Product[] PHONE_CASES = {CELL_PHONE_CASE_1};



    public static final Product[] HOODIES = { HOODY_BLACK, HOODY_GREY, HOODY_NAVY, HOODY_PURPLE};


    public static final Product[] FEATURED_PRODUCTS = {CELL_PHONE_CASE_1, HOODY_NAVY, T_SHIRT_WHITE,
             SNAPBACK_GREY,  TRUCKER_HAT_NAVY, SNAPBACK_CAMO,
            };


    public static HashMap<String, Product> getProducts() {
        return PRODUCTS;
    }

    private static final HashMap<String, Product> PRODUCTS;
    static {
        PRODUCTS = new HashMap<String, Product>();
        Products products = new Products();
        Iterator it = products.PRODUCT_MAP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            for (Product product : (Product[]) pair.getValue()) {
                PRODUCTS.put(String.valueOf(product.getSerial_number()), product);
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

    }
}
