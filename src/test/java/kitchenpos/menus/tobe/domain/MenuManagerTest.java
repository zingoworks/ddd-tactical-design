package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
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

        when(productPriceManager.getPrice(any())).thenReturn(BigDecimal.valueOf(9_500L));
        when(menuRepository.save(any())).thenReturn(Fixtures.twoFriedChickens());

        Menu created = menuManager.create(menu);

        assertMenu(created, Fixtures.twoFriedChickens());
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
        when(menuRepository.findAll()).thenReturn(Arrays.asList(Fixtures.twoFriedChickens()));
        when(menuProductRepository.findByMenuId(any()))
            .thenReturn(Arrays.asList(Fixtures.menuProduct()));

        assertThat(menuManager.list())
            .containsExactlyInAnyOrderElementsOf(Arrays.asList(Fixtures.twoFriedChickens()));
    }

    private void assertMenu(final Menu expected, final Menu actual) {
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getMenuGroup()).isEqualTo(expected.getMenuGroup()),
            () -> assertThat(actual.getMenuProducts())
                .containsExactlyInAnyOrderElementsOf(expected.getMenuProducts())
        );
    }

}
