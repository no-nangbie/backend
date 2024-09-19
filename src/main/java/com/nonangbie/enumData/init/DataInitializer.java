package com.nonangbie.enumData.init;
import com.nonangbie.enumData.entity.MenuCategory;
import com.nonangbie.enumData.entity.MenuCookTime;
import com.nonangbie.enumData.entity.MenuDifficulty;
import com.nonangbie.enumData.repository.MenuCategoryRepository;
import com.nonangbie.enumData.repository.MenuCookTimeRepository;
import com.nonangbie.enumData.repository.MenuDifficultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuDifficultyRepository menuDifficultyRepository;
    private final MenuCookTimeRepository menuCookTimeRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (menuCategoryRepository.count() == 0) {
            menuCategoryRepository.save(new MenuCategory(1L,"MENU_CATEGORY_SIDE", "밑 반찬"));
            menuCategoryRepository.save(new MenuCategory(2L,"MENU_CATEGORY_SOUP", "국/찌개"));
            menuCategoryRepository.save(new MenuCategory(3L,"MENU_CATEGORY_DESSERT", "디저트"));
            menuCategoryRepository.save(new MenuCategory(4L,"MENU_CATEGORY_NOODLE", "면"));
            menuCategoryRepository.save(new MenuCategory(5L,"MENU_CATEGORY_RICE", "밥/죽/떡"));
            menuCategoryRepository.save(new MenuCategory(6L,"MENU_CATEGORY_KIMCHI", "김치"));
            menuCategoryRepository.save(new MenuCategory(7L,"MENU_CATEGORY_FUSION", "퓨전"));
            menuCategoryRepository.save(new MenuCategory(8L,"MENU_CATEGORY_SEASONING", "양념"));
            menuCategoryRepository.save(new MenuCategory(9L,"MENU_CATEGORY_WESTERN", "양식"));
            menuCategoryRepository.save(new MenuCategory(10L,"MENU_CATEGORY_ETC", "기타"));
        }
        if (menuDifficultyRepository.count() == 0){
            menuDifficultyRepository.save(new MenuDifficulty(1L,"DIFFICULTY_EASY","쉬움"));
            menuDifficultyRepository.save(new MenuDifficulty(2L,"DIFFICULTY_MEDIUM","보통"));
            menuDifficultyRepository.save(new MenuDifficulty(3L,"DIFFICULTY_HARD","어려움"));
        }
        if (menuCookTimeRepository.count() == 0) {
            menuCookTimeRepository.save(new MenuCookTime(1L, "0_TO_1HOURS", "1시간미만"));
            menuCookTimeRepository.save(new MenuCookTime(2L, "1HOURS_TO_2HOURS", "1시간이상2시간미만"));
            menuCookTimeRepository.save(new MenuCookTime(3L, "2HOURS_TO_4HOURS", "2시간이상4시간미만"));
            menuCookTimeRepository.save(new MenuCookTime(4L, "4HOURS_OVER", "4시간이상"));
        }
    }
}
