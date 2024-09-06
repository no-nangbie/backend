CREATE TABLE food (
    food_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    food_name VARCHAR(50) NOT NULL,
    food_category VARCHAR(50) NOT NULL
);

CREATE TABLE menu (
    menu_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_title VARCHAR(20) NOT NULL,
    menu_description VARCHAR(255) NOT NULL,
    menu_category VARCHAR(50) NOT NULL,
    recipes TEXT NOT NULL,  -- List<String>을 저장하기 위해 TEXT 사용
    cooking_time INT NOT NULL,
    serving_size INT NOT NULL,
    difficulty VARCHAR(50) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    menu_like_count INT DEFAULT 0
);

CREATE TABLE food_menu (
    food_menu_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    food_quantity VARCHAR(50) NOT NULL,
    food_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    FOREIGN KEY (food_id) REFERENCES food(food_id),
    FOREIGN KEY (menu_id) REFERENCES menu(menu_id)
);