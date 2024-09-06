-- 채소 및 과일류 (VEGETABLES_FRUITS)
INSERT INTO food (food_name, food_category) VALUES ('대파', 'VEGETABLES_FRUITS');
INSERT INTO food (food_name, food_category) VALUES ('당근', 'VEGETABLES_FRUITS');
INSERT INTO food (food_name, food_category) VALUES ('쪽파', 'VEGETABLES_FRUITS');
INSERT INTO food (food_name, food_category) VALUES ('시금치', 'VEGETABLES_FRUITS');
INSERT INTO food (food_name, food_category) VALUES ('양파', 'VEGETABLES_FRUITS');
INSERT INTO food (food_name, food_category) VALUES ('양배추', 'VEGETABLES_FRUITS');
INSERT INTO food (food_name, food_category) VALUES ('청양고추', 'VEGETABLES_FRUITS');

-- 육류 (MEAT)
INSERT INTO food (food_name, food_category) VALUES ('돼지고기', 'MEAT');

-- 어류 및 해산물 (FISH_SEAFOOD)
INSERT INTO food (food_name, food_category) VALUES ('어묵', 'FISH_SEAFOOD');

-- 달걀 및 유제품 (EGGS_DAIRY)
INSERT INTO food (food_name, food_category) VALUES ('달걀', 'EGGS_DAIRY');
INSERT INTO food (food_name, food_category) VALUES ('마요네즈', 'EGGS_DAIRY');

-- 소스류 (SAUCES)
INSERT INTO food (food_name, food_category) VALUES ('고추장', 'SAUCES');
INSERT INTO food (food_name, food_category) VALUES ('된장', 'SAUCES');
INSERT INTO food (food_name, food_category) VALUES ('진간장', 'SAUCES');
INSERT INTO food (food_name, food_category) VALUES ('참기름', 'SAUCES');
INSERT INTO food (food_name, food_category) VALUES ('밤소스', 'SAUCES');

-- 기타 (OTHERS)
INSERT INTO food (food_name, food_category) VALUES ('MSG', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('간마늘', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('고춧가루', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('떡', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('라면', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('멸치가루', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('물', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('밤', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('소금', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('식용유', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('식초', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('우동면', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('황설탕', 'OTHERS');
INSERT INTO food (food_name, food_category) VALUES ('후춧가루', 'OTHERS');


-- Insert a single menu item (떡볶이) into the menu table
-- menu 테이블에 데이터 삽입
INSERT INTO menu (menu_title, menu_description, menu_category, recipes, cooking_time, serving_size, difficulty, image_url, menu_like_count)
VALUES
    ('떡볶이',
     '매운 떡볶이로, 다양한 재료와 함께 조리된 인기 있는 한국의 길거리 음식.',
     'MENU_CATEGORY_SIDE',
     '["대파는 어슷 썰거나 반으로 갈라 길게 썰어 준비한다.", "양배추, 어묵은 먹기 좋은 크기로 썰어 준비한다.", "냄비에 물, 진간장, 황설탕, 고추장, 굵은고춧가루, 고운고춧가루, 대파, 양배추를 넣어 끓인다.", "떡볶이떡은 흐르는 물에 가볍게 세척한다.", "육수가 끓으면 삶은달걀, 떡을 넣고 함께 끓여준다.", "기호에 맞게 MSG를 넣는다.", "떡을 넣고 육수가 끓어오르면 어묵을 넣어준다.", "양념장이 걸쭉하게 졸아들 때까지 끓여 완성한다."]',
     40,
     2,
     'DIFFICULTY_MEDIUM',
     'https://example.com/images/tteokbokki.jpg',
     0);


-- Insert into food_menu table
INSERT INTO food_menu
    (food_menu_id, food_quantity, food_id, menu_id) VALUES
      (1, '800g', 1, 1), -- 떡 800g
      (2, '160g', 2, 1), -- 어묵 160g
      (3, '160g', 3, 1), -- 양배추 160g
      (4, '240g', 4, 1), -- 대파 240g
      (5, '1L', 5, 1),  -- 물 1L
      (6, '80g', 6, 1), -- 달걀 80g
      (7, '50g', 7, 1), -- 고추장 50g
      (8, '50g', 8, 1), -- 진간장 50g
      (9, '30g', 9, 1), -- 고춧가루 30g
      (10, '20g', 10, 1), -- 황설탕 20g
      (11, '50g', 11, 1); -- MSG 50g

