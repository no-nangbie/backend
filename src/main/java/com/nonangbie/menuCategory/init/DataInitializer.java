package com.nonangbie.menuCategory.init;
import com.nonangbie.menuCategory.entity.MenuCategory;
import com.nonangbie.menuCategory.repository.MenuCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Override
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
    }
}
