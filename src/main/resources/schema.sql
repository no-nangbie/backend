-- index 적용
CREATE INDEX idx_food_isk ON member(email);
CREATE INDEX idx_food_isa ON food(food_name);
CREATE INDEX idx_food_isb ON menu(menu_title, menu_category);
CREATE INDEX idx_food_isq ON food_menu(food_id);
CREATE INDEX idx_food_isw ON food_menu(menu_id);
CREATE INDEX idx_food_isc ON food_menu(food_id, menu_id);
CREATE INDEX idx_food_iqw ON member_food(member_id);
CREATE INDEX idx_food_isd ON menu_like(menu_id);
CREATE INDEX idx_food_ise ON menu_like(member_id);