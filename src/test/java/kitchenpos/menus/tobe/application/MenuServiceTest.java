package kitchenpos.menus.tobe.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import kitchenpos.menus.tobe.Fixtures;
import kitchenpos.menus.tobe.application.dto.MenuRequestDto;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuManager menuManager;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @DisplayName("메뉴 생성한다.")
    @Test
    void create() {
        MenuRequestDto requestDto = new MenuRequestDto("후라이드 + 후라이드", BigDecimal.valueOf(19_000L),
            Fixtures.twoChickens().getId(),
            Arrays.asList(Fixtures.menuProduct()));

        given(menuGroupRepository.findById(any())).willReturn(Optional.of(Fixtures.twoChickens()));
        given(menuManager.create(any())).willReturn(Fixtures.twoFriedChickens());

        menuService.create(requestDto);

        then(menuGroupRepository)
            .should()
            .findById(requestDto.getMenuGroupId())
        ;

        then(menuManager)
            .should()
            .create(new Menu(requestDto.getName(), requestDto.getPrice(), Fixtures.twoChickens(),
                Arrays.asList(Fixtures.menuProduct())))
        ;
    }

    @DisplayName("메뉴 리스트를 가져온다.")
    @Test
    void list() {
        given(menuManager.list()).willReturn(Arrays.asList(Fixtures.twoFriedChickens()));

        menuService.list();

        then(menuManager)
            .should()
            .list()
        ;
    }
}
