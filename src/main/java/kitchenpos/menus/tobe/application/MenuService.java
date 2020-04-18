package kitchenpos.menus.tobe.application;

import java.util.List;
import kitchenpos.menus.tobe.application.dto.MenuRequestDto;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MenuService {

    private final MenuManager menuManager;

    private final MenuGroupRepository menuGroupRepository;

    public MenuService(MenuManager menuManager, MenuGroupRepository menuGroupRepository) {
        this.menuManager = menuManager;
        this.menuGroupRepository = menuGroupRepository;
    }

    public Long create(MenuRequestDto requestDto) {
        MenuGroup menuGroup = menuGroupRepository.findById(requestDto.getMenuGroupId())
            .orElseThrow(IllegalArgumentException::new);
        Menu menu = new Menu(requestDto.getName(), requestDto.getPrice(), menuGroup, requestDto.getMenuProducts());
        Menu created = menuManager.create(menu);
        return created.getId();
    }

    public List<Menu> list() {
        return menuManager.list();
    }
}
