package com.nonangbie.memberMenuCategory.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.memberMenuCategory.entity.MemberMenuCategory;
import com.nonangbie.memberMenuCategory.repository.MemberMenuCategoryRepository;
import com.nonangbie.menuCategory.entity.MenuCategory;
import com.nonangbie.menuCategory.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberMenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MemberMenuCategoryRepository repository;

    public void createMemberMenuCategory(Member saveMember){
        List<MenuCategory> allCategories = menuCategoryRepository.findAll();

        for(MenuCategory menuCategory : allCategories){
            MemberMenuCategory mmc = new MemberMenuCategory();
            mmc.setMember(saveMember);
            mmc.setMenuCategory(menuCategory);
            mmc.setCount(0);
            repository.save(mmc);
        }
    }

    public void incrementCount(Member member, MenuCategory menuCategory) {
        MemberMenuCategory mmc = repository.findByMemberAndMenuCategory(member, menuCategory)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MMC_NOT_FOUND));
        mmc.setCount(mmc.getCount() + 1);
        repository.save(mmc);
    }
}
