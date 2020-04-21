package kitchenpos.menus.tobe.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Arrays;
import kitchenpos.menus.tobe.Fixtures;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    @InjectMocks
    private MenuGroupService menuGroupService;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @DisplayName("메뉴 그룹을 생성한다.")
    @Test
    void create() {
        given(menuGroupRepository.save(any())).willReturn(Fixtures.twoChickens());

        menuGroupService.create(Fixtures.twoChickens().getName());

        then(menuGroupRepository)
            .should()
            .save(new MenuGroup(Fixtures.twoChickens().getName()));
    }

    @DisplayName("메뉴그룹 리스트를 가져온다.")
    @Test
    void list() {
        given(menuGroupRepository.findAll()).willReturn(Arrays.asList(Fixtures.twoChickens()));

        menuGroupService.list();

        then(menuGroupRepository)
            .should()
            .findAll();
    }
}
