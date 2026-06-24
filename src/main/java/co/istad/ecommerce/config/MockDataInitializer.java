package co.istad.ecommerce.config;

import co.istad.ecommerce.features.category.Category;
import co.istad.ecommerce.features.order.Order;
import co.istad.ecommerce.features.order.OrderLine;
import co.istad.ecommerce.features.product.Product;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.mock-data", name = "enabled", havingValue = "true")
public class MockDataInitializer implements CommandLineRunner {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(String @NonNull ... args) {
        synchronizeIdentitySequences();
        Map<String, Category> categories = seedCategories();
        Map<String, Product> products = seedProducts(categories);
        int orderCount = seedOrders(products);

        log.info("Mock data ready: {} categories, {} products, {} orders",
                categories.size(), products.size(), orderCount);
    }

    private void synchronizeIdentitySequences() {
        synchronizeIdentitySequence("categories");
        synchronizeIdentitySequence("order_lines");
    }

    private void synchronizeIdentitySequence(String table) {
        entityManager.createNativeQuery("""
                        select setval(
                            pg_get_serial_sequence(:table, :column),
                            coalesce((select max(%s) + 1 from %s), 1),
                            false
                        )
                        """.formatted("id", table), Long.class)
                .setParameter("table", table)
                .setParameter("column", "id")
                .getSingleResult();
    }

    private Map<String, Category> seedCategories() {
        List<CategorySeed> seeds = List.of(
                new CategorySeed("Electronics", "Electronic devices and accessories", "cpu", null, false),
                new CategorySeed("Fashion", "Clothing, shoes, and fashion accessories", "shirt", null, false),
                new CategorySeed("Home & Kitchen", "Furniture, appliances, and kitchen essentials", "house", null, false),
                new CategorySeed("Clearance", "Discontinued and clearance products", "tag", null, true),
                new CategorySeed("Phones", "Smartphones and mobile accessories", "smartphone", "Electronics", false),
                new CategorySeed("Computers", "Laptops, desktops, and computer accessories", "laptop", "Electronics", false),
                new CategorySeed("Audio", "Headphones, speakers, and audio equipment", "headphones", "Electronics", false),
                new CategorySeed("Men's Fashion", "Clothing and accessories for men", "user", "Fashion", false),
                new CategorySeed("Women's Fashion", "Clothing and accessories for women", "user", "Fashion", false),
                new CategorySeed("Kitchen Appliances", "Small appliances for everyday cooking", "cooking-pot", "Home & Kitchen", false)
        );

        Map<String, Category> categories = new HashMap<>();
        for (CategorySeed seed : seeds) {
            Category category = findCategory(seed.name());
            if (category == null) {
                category = new Category();
                category.setName(seed.name());
                category.setDescription(seed.description());
                category.setIcon(seed.icon());
                category.setIsDeleted(seed.deleted());
                category.setParentCategory(seed.parentName() == null ? null : categories.get(seed.parentName()));
                entityManager.persist(category);
            }
            categories.put(seed.name(), category);
        }
        return categories;
    }

    private Map<String, Product> seedProducts(Map<String, Category> categories) {
        List<ProductSeed> seeds = List.of(
                new ProductSeed("PHN-001", "iphone-16-pro", "iPhone 16 Pro", "Premium smartphone with pro camera system", "iphone-16-pro.jpg", "1199.00", 25, true, false, "Phones"),
                new ProductSeed("PHN-002", "samsung-galaxy-s25", "Samsung Galaxy S25", "Android flagship smartphone", "galaxy-s25.jpg", "999.00", 40, true, false, "Phones"),
                new ProductSeed("PHN-003", "google-pixel-9a", "Google Pixel 9a", "Mid-range phone with an excellent camera", "pixel-9a.jpg", "499.00", 0, false, false, "Phones"),
                new ProductSeed("CMP-001", "macbook-air-m4", "MacBook Air M4", "Lightweight laptop for work and study", "macbook-air-m4.jpg", "1299.00", 18, true, false, "Computers"),
                new ProductSeed("CMP-002", "lenovo-thinkpad-x1", "Lenovo ThinkPad X1", "Business laptop with durable construction", "thinkpad-x1.jpg", "1599.00", 8, true, false, "Computers"),
                new ProductSeed("CMP-003", "gaming-mouse-rgb", "RGB Gaming Mouse", "Programmable wired gaming mouse", "gaming-mouse.jpg", "39.99", 120, true, false, "Computers"),
                new ProductSeed("AUD-001", "sony-noise-canceling-headphones", "Sony Noise Canceling Headphones", "Wireless over-ear headphones", "sony-headphones.jpg", "349.99", 35, true, false, "Audio"),
                new ProductSeed("AUD-002", "portable-bluetooth-speaker", "Portable Bluetooth Speaker", null, "bluetooth-speaker.jpg", "79.50", 60, true, false, "Audio"),
                new ProductSeed("MEN-001", "classic-cotton-tshirt", "Classic Cotton T-Shirt", "Comfortable everyday cotton shirt", "cotton-tshirt.jpg", "19.99", 200, true, false, "Men's Fashion"),
                new ProductSeed("MEN-002", "slim-fit-jeans", "Slim Fit Jeans", "Dark blue stretch denim jeans", "slim-jeans.jpg", "59.99", 75, true, false, "Men's Fashion"),
                new ProductSeed("WMN-001", "summer-floral-dress", "Summer Floral Dress", "Lightweight floral dress", "floral-dress.jpg", "69.99", 45, true, false, "Women's Fashion"),
                new ProductSeed("WMN-002", "leather-handbag", "Leather Handbag", "Everyday genuine leather handbag", "leather-handbag.jpg", "149.00", 12, true, false, "Women's Fashion"),
                new ProductSeed("KIT-001", "digital-air-fryer", "Digital Air Fryer", "Large-capacity digital air fryer", "air-fryer.jpg", "129.99", 30, true, false, "Kitchen Appliances"),
                new ProductSeed("KIT-002", "espresso-coffee-machine", "Espresso Coffee Machine", "Compact espresso and cappuccino maker", "coffee-machine.jpg", "249.99", 15, true, false, "Kitchen Appliances"),
                new ProductSeed("KIT-003", "stainless-steel-blender", "Stainless Steel Blender", "High-speed kitchen blender", null, "89.99", 0, false, false, "Kitchen Appliances"),
                new ProductSeed("CLR-001", "legacy-mp3-player", "Legacy MP3 Player", "Discontinued portable music player", "mp3-player.jpg", "29.99", 5, false, true, "Clearance"),
                new ProductSeed("CLR-002", "old-model-keyboard", "Old Model Keyboard", null, null, "14.99", 0, false, true, "Clearance")
        );

        Map<String, Product> products = new HashMap<>();
        for (ProductSeed seed : seeds) {
            Product product = findProduct(seed.slug());
            if (product == null) {
                product = new Product();
                product.setCode(seed.code());
                product.setSlug(seed.slug());
                product.setName(seed.name());
                product.setDescription(seed.description());
                product.setThumbnail(seed.thumbnail());
                product.setUnitPrice(new BigDecimal(seed.unitPrice()));
                product.setQty(seed.qty());
                product.setIsAvailable(seed.available());
                product.setIsDeleted(seed.deleted());
                product.setCategory(categories.get(seed.categoryName()));
                entityManager.persist(product);
            }
            products.put(seed.slug(), product);
        }
        return products;
    }

    private int seedOrders(Map<String, Product> products) {
        List<OrderSeed> seeds = List.of(
                new OrderSeed("MOCK-CUST-001", "Phnom Penh", true, 0.0, "Delivered", 45, false,
                        List.of(new LineSeed("iphone-16-pro", 1), new LineSeed("sony-noise-canceling-headphones", 1))),
                new OrderSeed("MOCK-CUST-002", "Siem Reap", true, 10.0, "Paid by card", 30, false,
                        List.of(new LineSeed("macbook-air-m4", 1), new LineSeed("gaming-mouse-rgb", 2))),
                new OrderSeed("MOCK-CUST-003", "Battambang", false, 5.0, "Waiting for payment", 14, false,
                        List.of(new LineSeed("digital-air-fryer", 1), new LineSeed("espresso-coffee-machine", 1))),
                new OrderSeed("MOCK-CUST-004", "Kampot", false, 0.0, null, 7, false,
                        List.of(new LineSeed("classic-cotton-tshirt", 3), new LineSeed("slim-fit-jeans", 1))),
                new OrderSeed("MOCK-CUST-005", "Phnom Penh", true, 15.0, "VIP customer", 3, false,
                        List.of(new LineSeed("samsung-galaxy-s25", 1), new LineSeed("leather-handbag", 1))),
                new OrderSeed("MOCK-CUST-006", "Kandal", false, 25.0, "Bulk test order", 1, false,
                        List.of(new LineSeed("portable-bluetooth-speaker", 5), new LineSeed("summer-floral-dress", 2))),
                new OrderSeed("MOCK-CUST-007", "Phnom Penh", false, 0.0, "Deleted test order", 90, true,
                        List.of(new LineSeed("legacy-mp3-player", 1), new LineSeed("old-model-keyboard", 2)))
        );

        for (OrderSeed seed : seeds) {
            Order order = findOrder(seed.customerId());
            if (order == null) {
                order = new Order();
                order.setCustomerId(seed.customerId());
                order.setAddress(seed.address());
                order.setStatus(seed.status());
                order.setDiscount(seed.discount());
                order.setRemark(seed.remark());
                order.setOrderedAt(LocalDateTime.now().minusDays(seed.daysAgo()));
                order.setIsDelete(seed.deleted());
                entityManager.persist(order);
            }

            for (LineSeed lineSeed : seed.lines()) {
                Product product = products.get(lineSeed.productSlug());
                if (!orderLineExists(order, product)) {
                    OrderLine line = new OrderLine();
                    line.setOrder(order);
                    line.setProduct(product);
                    line.setQty(lineSeed.qty());
                    line.setUnitPrice(product.getUnitPrice());
                    entityManager.persist(line);
                }
            }
        }
        return seeds.size();
    }

    private Category findCategory(String name) {
        return entityManager.createQuery("select c from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    private Product findProduct(String slug) {
        return entityManager.createQuery("select p from Product p where p.slug = :slug", Product.class)
                .setParameter("slug", slug)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    private Order findOrder(String customerId) {
        return entityManager.createQuery("select o from Order o where o.customerId = :customerId", Order.class)
                .setParameter("customerId", customerId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    private boolean orderLineExists(Order order, Product product) {
        return entityManager.createQuery("""
                        select count(ol) from OrderLine ol
                        where ol.order = :order and ol.product = :product
                        """, Long.class)
                .setParameter("order", order)
                .setParameter("product", product)
                .getSingleResult() > 0;
    }

    private record CategorySeed(String name, String description, String icon, String parentName, boolean deleted) {
    }

    private record ProductSeed(String code, String slug, String name, String description, String thumbnail,
                               String unitPrice, int qty, boolean available, boolean deleted, String categoryName) {
    }

    private record OrderSeed(String customerId, String address, boolean status, double discount, String remark,
                             int daysAgo, boolean deleted, List<LineSeed> lines) {
    }

    private record LineSeed(String productSlug, int qty) {
    }
}
