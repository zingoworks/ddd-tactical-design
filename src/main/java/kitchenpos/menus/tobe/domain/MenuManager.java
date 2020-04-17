package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.tobe.application.dto.MenuRequestDto;
import kitchenpos.menus.tobe.infrastructure.ProductPriceManager;
import org.springframework.stereotype.Component;

@Component
public class MenuManager {

    private final MenuRepository menuRepository;
    private final MenuProductRepository menuProductRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductPriceManager productPriceManager;

    public MenuManager(MenuRepository menuRepository, MenuProductRepository menuProductRepository,
        MenuGroupRepository menuGroupRepository,
        ProductPriceManager productPriceManager) {
        this.menuRepository = menuRepository;
        this.menuProductRepository = menuProductRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productPriceManager = productPriceManager;
    }

    //TODO MenuManager는 도메인 layer에 있고, MenuRequestDto는 애플리케이션 layer에 있는데요. 도메인 layer가 애플리케이션 layer를 의존하고 있는 부분을 변경해보는 건 어떨까요?
    public Menu create(MenuRequestDto requestDto) {
        MenuGroup menuGroup = menuGroupRepository.findById(requestDto.getMenuGroupId())
            .orElseThrow(IllegalArgumentException::new);

        BigDecimal sum = requestDto.getMenuProducts().stream()
            .map(p -> p.calculatePrice(productPriceManager.getPrice(p.getProductId())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (requestDto.getPrice().compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }

        Menu menu = menuRepository
            .save(new Menu(requestDto.getName(), requestDto.getPrice(), menuGroup));
        updateMenuProductsStatus(menu);
        return menu;
    }

    private void updateMenuProductsStatus(Menu menu) {
        for (MenuProduct menuProduct : menuProductRepository.findByMenuId(menu.getId())) {
            menuProduct.registeredOn(menu);
            menuProductRepository.save(menuProduct);
        }
    }

    public List<Menu> list() {
        List<Menu> menus = menuRepository.findAll();
        for (Menu menu : menus) {
            menu.injectMenuProducts(menuProductRepository.findByMenuId(menu.getId()));
        }
        return menus;
    }
}
