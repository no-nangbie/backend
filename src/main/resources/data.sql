-- 채소 및 과일류 (VEGETABLES_FRUITS)
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (1, '대파', 'VEGETABLES_FRUITS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (2, '당근', 'VEGETABLES_FRUITS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (3, '쪽파', 'VEGETABLES_FRUITS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (4, '시금치', 'VEGETABLES_FRUITS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (5, '양파', 'VEGETABLES_FRUITS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (6, '양배추', 'VEGETABLES_FRUITS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (7, '청양고추', 'VEGETABLES_FRUITS');

-- 육류 (MEAT)
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (8, '돼지고기', 'MEAT');

-- 어류 및 해산물 (FISH_SEAFOOD)
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (9, '어묵', 'FISH_SEAFOOD');

-- 달걀 및 유제품 (EGGS_DAIRY)
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (10, '달걀', 'EGGS_DAIRY');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (11, '마요네즈', 'EGGS_DAIRY');

-- 소스류 (SAUCES)
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (12, '고추장', 'SAUCES');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (13, '된장', 'SAUCES');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (14, '진간장', 'SAUCES');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (15, '참기름', 'SAUCES');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (16, '밤소스', 'SAUCES');

-- 기타 (OTHERS)
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (17, 'MSG', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (18, '간마늘', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (19, '고춧가루', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (20, '떡', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (21, '라면', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (22, '멸치가루', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (23, '물', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (24, '밤', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (25, '소금', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (26, '식용유', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (27, '식초', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (28, '우동면', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (29, '황설탕', 'OTHERS');
INSERT IGNORE INTO food (food_id, food_name, food_category) VALUES (30, '후춧가루', 'OTHERS');



-- Insert a single menu item (떡볶이) into the menu table
-- menu 테이블에 데이터 삽입
INSERT IGNORE INTO menu (menu_id, menu_title, menu_description, menu_category, recipes, cooking_time, serving_size, difficulty, image_url, menu_like_count, created_at)
VALUES
    (1, '떡볶이',
     '매운 떡볶이로, 다양한 재료와 함께 조리된 인기 있는 한국의 길거리 음식.',
     'MENU_CATEGORY_SIDE',
     '["1. 대파는 어슷 썰거나 반으로 갈라 길게 썰어 준비한다.", "2. 양배추, 어묵은 먹기 좋은 크기로 썰어 준비한다.", "3. 냄비에 물, 진간장, 황설탕, 고추장, 굵은고춧가루, 고운고춧가루, 대파, 양배추를 넣어 끓인다.", "4. 떡볶이떡은 흐르는 물에 가볍게 세척한다.", "5. 육수가 끓으면 삶은달걀, 떡을 넣고 함께 끓여준다.", "6. 기호에 맞게 MSG를 넣는다.", "7. 떡을 넣고 육수가 끓어오르면 어묵을 넣어준다.", "8. 양념장이 걸쭉하게 졸아들 때까지 끓여 완성한다."]',
     40,
     2,
     'DIFFICULTY_MEDIUM',
     'https://example.com/images/tteokbokki.jpg',
     0,
     NOW()  -- 현재 시각으로 설정
     );

INSERT IGNORE INTO menu (menu_id, menu_title, menu_description, menu_category, recipes, cooking_time, serving_size, difficulty, image_url, menu_like_count, created_at)
VALUES
    (
        2, '중화 제육면',
        '중화 제육면은 돼지고기와 채소를 볶은 후 면과 함께 조리한 중화식 면 요리입니다.',
        'MENU_CATEGORY_NOODLE',
        '["1. 대파는 송송 썰어 준비한다.", "2. 프라이팬에 식용유를 두르고 대파를 넣어 파기름을 낸다.", "3. 파기름 향이 올라오면 돼지고기를 넣고 뭉치지 않게 풀어가며 볶는다.", "4. 고기 노릇하게 익으면 황설탕을 넣어 볶는다.", "5. 진간장을 넣어 향을 내준다.", "6. 된장, 고추장, 간마늘을 넣어 중약불에서 풀어가며 볶는다.", "7. 고춧가루를 넣어 가볍게 섞고 물, 후춧가루를 넣는다.", "8. 기호에 따라 미원을 넣고 농도나게 졸인다.", "9. 소금을 넣어 간을 맞춘다.", "10. 끓는 물에 칼국수면을 넣어 삶아낸다.", "11. 삶은 칼국수면은 찬물에 헹궈 준비한다.", "12. 헹궈 낸 면은 뜨거운 물에 토렴하여 준다.", "13. 프라이팬에 식용유를 두르고 달궈준다.", "14. 프라이팬에 달궈지면 양파, 대파를 넣고 그을려 지도록 볶는다.", "15. 애호박, 양배추를 넣어 볶아준다.", "16. 만들어 둔 중화제육소스를 넣고 중약불로 줄여 채소가 숨이 죽을 정도까지 볶아준다.", "17. 완성 그릇에 면을 담고 볶아 둔 중화제육소스를 끼얹어 완성한다."]',
        30, -- cooking_time (예: 30분)
        2,  -- serving_size (예: 2인분)
        'DIFFICULTY_MEDIUM',
        'https://example.com/images/jjajangmyeon.jpg',
        0,
        NOW()  -- 현재 시각으로 설정
    );

INSERT IGNORE INTO menu (menu_id, menu_title, menu_description, menu_category, recipes, cooking_time, serving_size, difficulty, image_url, menu_like_count, created_at)
VALUES
    (
     3, '밤라면',
     '밤 소스와 함께 만드는 고소한 라면.',
     'MENU_CATEGORY_NOODLE',
     '["1. 양파는 채 썰고 대파는 송송 썰어 준비한다.", "2. 냄비에 식용유, 양파, 간 돼지고기를 넣어 볶는다.", "3. 고기가 익으면 간장을 넣어 볶아주고 물을 넣어 끓인다.", "4. 믹서에 깐 밤, 물을 넣어 갈아준다.", "5. 라면 물이 끓어오르면 라면스프, 면을 넣고 80% 정도 삶아 면을 건져낸다.", "6. 달걀을 반 정도만 풀어 준비한다.", "7. 라면 육수에 갈아 둔 밤 소스를 넣어 섞어준다.", "8. 밤 소스가 골고루 섞이면 달걀을 넣고 저어준다.", "9. 참기름, 썰어 둔 대파를 1/2 양 정도 넣어 면 위에 부어준다.", "10. 고명용 대파를 올려 마무리한다."]',
     20,
     2,
     'DIFFICULTY_MEDIUM',
     'https://example.com/images/chestnut-ramen.jpg',
     0,
     NOW()  -- 현재 시각으로 설정
    );

INSERT IGNORE INTO menu (menu_id, menu_title, menu_description, menu_category, recipes, cooking_time, serving_size, difficulty, image_url, menu_like_count, created_at)
VALUES
    (4, '볶음우동',
     '양파와 채소를 볶아 만든 맛있는 볶음우동.',
     'MENU_CATEGORY_NOODLE',
     '["1. 양파, 당근은 채 썰고 대파, 쪽파는 송송 썰어 준비한다.", "2. 양배추는 1cm 정도 두께로 먹기 좋게 썬다.", "3. 훈연멸치는 내장을 제거하고 마른 팬에 볶아 믹서에 갈아준다.", "4. 팬에 기름, 대파를 넣고 열이 오르면 고기를 넣어 볶아준다.", "5. 고기가 노릇하게 익으면 양파, 당근, 양배추를 넣어 볶는다.", "6. 진간장, 황설탕, 식초, 물을 섞어 양념장을 만든다.", "7. 채소가 볶아지면 가운데에 면을 넣고 가장자리에 양념장을 둘러 중불에서 끓인다.", "8. 면이 풀어지면 불을 세게 켜고 볶아준다.", "9. 완성그릇에 볶음우동을 담고 마요네즈를 뿌려준다.", "10. 훈연멸치가루, 쪽파를 올려 마무리한다."]',
     30,  -- 예시로 30분
     2,   -- 예시로 2인분
     'DIFFICULTY_MEDIUM',
     'https://example.com/images/stir-fried-udon.jpg',
     0,
     NOW()  -- 현재 시각으로 설정
    );




-- Insert into food_menu table
INSERT IGNORE INTO food_menu (food_menu_id, food_quantity, food_id, menu_id) VALUES
                                                            (1, '800g', 20, 1), -- 떡 800g
                                                            (2, '160g', 9, 1),  -- 어묵 160g
                                                            (3, '160g', 6, 1),  -- 양배추 160g
                                                            (4, '240g', 1, 1),  -- 대파 240g
                                                            (5, '1L', 23, 1),   -- 물 1L
                                                            (6, '80g', 10, 1),  -- 달걀 80g
                                                            (7, '50g', 12, 1),  -- 고추장 50g
                                                            (8, '50g', 14, 1),  -- 진간장 50g
                                                            (9, '30g', 19, 1),  -- 고춧가루 30g
                                                            (10, '20g', 29, 1),  -- 황설탕 20g
                                                            (11, '50g', 17, 1),  -- MSG 50g
                                                            (12, '35g', 26, 2), -- 식용유 35g
                                                            (13, '150g', 8, 2), -- 돼지고기 150g
                                                            (14, '40g', 1, 2),  -- 대파 40g
                                                            (15, '15g', 18, 2), -- 간마늘 15g
                                                            (16, '10g', 14, 2), -- 진간장 10g
                                                            (17, '15g', 29, 2), -- 황설탕 15g
                                                            (18, '10g', 19, 2), -- 고춧가루 10g
                                                            (19, '15g', 13, 2), -- 된장 15g
                                                            (20, '60g', 12, 2), -- 고추장 60g
                                                            (21, '2g', 25, 2),  -- 소금 2g
                                                            (22, '3g', 17, 2),  -- MSG 3g
                                                            (23, '3g', 30, 2),  -- 후춧가루 3g
                                                            (24, '360g', 23, 2),-- 물 360g
                                                            (25, '50g', 24, 3),  -- 밤 50g
                                                            (26, '500g', 23, 3), -- 물 500g
                                                            (27, '1개', 21, 3),  -- 라면 1개
                                                            (28, '80g', 16, 3),  -- 밤소스 80g
                                                            (29, '40g', 8, 3),   -- 돼지고기 40g
                                                            (30, '1개', 10, 3),  -- 달걀 1개
                                                            (31, '50g', 5, 3),   -- 양파 50g
                                                            (32, '20g', 1, 3),   -- 대파 20g
                                                            (33, '14g', 26, 3),  -- 식용유 14g
                                                            (34, '10g', 14, 3),  -- 진간장 10g
                                                            (35, '7g', 15, 3),   -- 참기름 7g
                                                            (36, '20g', 14, 4), -- 진간장 20g
                                                            (37, '7g', 27, 4),  -- 식초 7g
                                                            (38, '10g', 29, 4), -- 황설탕 10g
                                                            (39, '90g', 23, 4), -- 물 90g
                                                            (40, '20g', 26, 4), -- 식용유 20g
                                                            (41, '40g', 6, 4),  -- 양배추 40g
                                                            (42, '40g', 5, 4),  -- 양파 40g
                                                            (43, '20g', 2, 4),  -- 당근 20g
                                                            (44, '50g', 8, 4),  -- 돼지고기 50g
                                                            (45, '30g', 1, 4),  -- 대파 30g
                                                            (46, '200g', 28, 4), -- 우동면 200g
                                                            (47, '적당량', 22, 4), -- 멸치가루 적당량
                                                            (48, '적당량', 3, 4), -- 쪽파 적당량
                                                            (49, '적당량', 11, 4); -- 마요네즈 적당량



