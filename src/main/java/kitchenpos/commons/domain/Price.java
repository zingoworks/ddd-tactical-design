package kitchenpos.commons.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
//TODO 일부 중복이 발생하더라도(의도적인 중복) 컨텍스트마다 Price를 둔다면, 추후 특정 컨텍스트를 떼어내서 독립적으로 운영해야 할 때 분리시키는 작업이 수월하지 않을까요?
public class Price {

    @Column(name = "price")
    private BigDecimal price;

    public Price() {
    }

    private Price(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public static Price valueOf(BigDecimal price) {
        return new Price(price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price)) {
            return false;
        }
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
