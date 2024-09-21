package com.nonangbie.menuRecommend.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menu.repository.MenuRepository;
import com.nonangbie.menuRecommend.entity.MenuRecommend;
import com.nonangbie.menuRecommend.repository.MenuRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuRecommendService {
    private final MenuRecommendRepository menuRecommendRepository;
    private final MenuRepository menuRepository;

    public void deleteAll(Member member) {
        menuRecommendRepository.deleteAllByMember(member);
    }

    public void saveAll(Member member, List<Menu> menus) {
        deleteAll(member);
        List<MenuRecommend> menuRecommendList = new ArrayList<>();
        for(Menu menu : menus){
            MenuRecommend menuRecommend = new MenuRecommend();
            menuRecommend.setMenu(menu);
            menuRecommend.setMember(member);
        }
        menuRecommendRepository.saveAll(menuRecommendList);
    }

    public void score(Member member, Menu menu, int score) {
        MenuRecommend menuRecommend = menuRecommendRepository.findByMemberAndMenu(member,menu)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_EXISTS));
        menuRecommend.setScore(score);
        menuRecommendRepository.save(menuRecommend);
    }
}
