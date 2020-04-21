package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.menus.tobe.Fixtures;
import kitchenpos.menus.tobe.infrastructure.ProductPriceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuManagerTest {

    @InjectMocks
    private MenuManager menuManager;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuProductRepository menuProductRepository;

    @Mock
    private ProductPriceManager productPriceManager;

    @DisplayName("메뉴를 등록한다.")
    @Test
    void create() {
        Menu menu = new Menu("후라이드 + 후라이드", BigDecimal.valueOf(19_000L),
            Fixtures.twoChickens(), Arrays.asList(Fixtures.menuProduct()));

        given(productPriceManager.getPrice(any())).willReturn(BigDecimal.valueOf(9_500L));
        given(menuRepository.save(any())).willReturn(Fixtures.twoFriedChickens());

        menuManager.create(menu);

        then(productPriceManager)
            .should()
            .getPrice(Fixtures.menuProduct().getProductId())
        ;

        then(menuRepository)
            .should()
            .save(menu)
        ;
    }

    @DisplayName("가격이 유효하지 않은 메뉴는 등록할 수 없다.")
    @Test
    void canNotCreate() {
        Menu menu = new Menu("후라이드 + 후라이드", BigDecimal.valueOf(19_000L),
            Fixtures.twoChickens(), Arrays.asList(Fixtures.menuProduct()));

        when(productPriceManager.getPrice(any())).thenReturn(BigDecimal.valueOf(5_500L));

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> menuManager.create(menu));
    }

    @DisplayName("메뉴 리스트를 가져온다.")
    @Test
    void list() {
        given(menuRepository.findAll()).willReturn(Arrays.asList(Fixtures.twoFriedChickens()));
        given(menuProductRepository.findByMenuId(any()))
            .willReturn(Arrays.asList(Fixtures.menuProduct()));

        menuManager.list();

        then(menuRepository)
            .should()
            .findAll()
        ;

        then(menuProductRepository)
            .should()
            .findByMenuId(Fixtures.menuProduct().getMenu().getId())
        ;
    }
}
