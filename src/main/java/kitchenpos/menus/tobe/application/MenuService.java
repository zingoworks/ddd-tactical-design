package kitchenpos.menus.tobe.application;

import java.util.List;
import kitchenpos.menus.tobe.application.dto.MenuRequestDto;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuManager;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MenuService {

    private final MenuManager menuManager;
    private final MenuRepository menuRepository;

    public MenuService(MenuManager menuManager, MenuRepository menuRepository) {
        this.menuManager = menuManager;
        this.menuRepository = menuRepository;
    }

    public Long create(MenuRequestDto requestDto) {
        //TODO 도메인서비스가 애플리케이션 레이어의 dto에 의존하지 않도록 개선해보자
        return menuManager.create(requestDto).getId();
    }

    public List<Menu> list() {
        return menuManager.list();
    }
}
