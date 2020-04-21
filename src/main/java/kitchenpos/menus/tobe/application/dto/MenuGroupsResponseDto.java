package kitchenpos.menus.tobe.application.dto;

import java.util.List;
import java.util.Objects;
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
            .collect(Collectors.toList())
            ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuGroupsResponseDto)) {
            return false;
        }
        MenuGroupsResponseDto that = (MenuGroupsResponseDto) o;
        return Objects.equals(menuGroups, that.menuGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuGroups);
    }
}
