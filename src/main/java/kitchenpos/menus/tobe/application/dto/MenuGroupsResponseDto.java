package kitchenpos.menus.tobe.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.MenuGroup;

public class MenuGroupsResponseDto {

    private List<MenuGroup> menuGroups;

    public MenuGroupsResponseDto(List<MenuGroup> menuGroups) {
        this.menuGroups = menuGroups;
    }

    public List<String> getMenuGroupNames() {
        return menuGroups.stream()
            .map(MenuGroup::getName)
            .collect(Collectors.toList());
    }
}
