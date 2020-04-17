package kitchenpos.menus.tobe.infrastructure;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.usecase.ProductService;
import org.springframework.stereotype.Component;

@Component
//TODO 부패방지계층의 정의, 위치 고민해보기
public class ProductPriceManager {

    private final ProductService productService;

    public ProductPriceManager(ProductService productService) {
        this.productService = productService;
    }

    public BigDecimal getPrice(Long id) {
        return productService.get(id).getPrice();
    }
}
