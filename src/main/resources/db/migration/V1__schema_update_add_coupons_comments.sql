-- versioned migrations
ALTER TABLE categories MODIFY name VARCHAR(50) UNIQUE;
-- Chuyển trường price thành decimal
ALTER TABLE products MODIFY price DECIMAL(10,2);

-- Sửa trường thumbnail thành độ dài 255
ALTER TABLE products MODIFY thumbnail VARCHAR(255);

ALTER TABLE `users` MODIFY COLUMN `phone_number` VARCHAR(15);
ALTER TABLE `users` MODIFY COLUMN `password` CHAR(60) NOT NULL;
ALTER TABLE `users` ALTER COLUMN `role_id` SET DEFAULT 1;

ALTER TABLE `order_details` MODIFY COLUMN `price` DECIMAL(10, 2),
    MODIFY COLUMN `number_of_products` INT DEFAULT 1,
    MODIFY COLUMN `total_money` DECIMAL(10, 2) DEFAULT 0;

CREATE TABLE IF NOT EXISTS comments (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        product_id INT,
                                        user_id INT,
                                        content VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    );
CREATE TABLE IF NOT EXISTS coupons (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       code VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true
    );

ALTER TABLE orders
    ADD COLUMN coupon_id INT,
ADD CONSTRAINT fk_orders_coupon
FOREIGN KEY (coupon_id) REFERENCES coupons(id);

ALTER TABLE order_details
    ADD COLUMN coupon_id INT,
ADD CONSTRAINT fk_order_details_coupon
FOREIGN KEY (coupon_id) REFERENCES coupons(id);

CREATE TABLE IF NOT EXISTS coupon_conditions (
                                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                                 coupon_id INT NOT NULL,
                                                 attribute VARCHAR(255) NOT NULL,
    operator VARCHAR(10) NOT NULL,
    value VARCHAR(255) NOT NULL,
    discount_amount DECIMAL(5, 2) NOT NULL,
    FOREIGN KEY (coupon_id) REFERENCES coupons(id)
    );