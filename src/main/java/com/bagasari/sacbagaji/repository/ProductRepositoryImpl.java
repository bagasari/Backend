package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.entity.Product;
import com.bagasari.sacbagaji.model.entity.QProduct;
import com.bagasari.sacbagaji.util.QueryDslUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.bagasari.sacbagaji.model.entity.QProduct.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //추천순, 최근순
    public Slice<ProductDTO> findAllByNameOrderBySort(String keyword, Pageable pageable, Long lastId) {

        List<OrderSpecifier> orderSpecifiers = getAllOrderSpecifiers(pageable);

        List<Product> productList = queryFactory.selectFrom(product)
                .where(
                        ltId(lastId),
                        product.name.contains(keyword)
                )
                .limit(pageable.getPageSize()+1) //요청 크기보다 +1로 조회하여 다음 페이지가 존재하는지 확인
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .fetch();

        List<ProductDTO> productDTOList = new ArrayList<>();

        for(Product p: productList) {
            productDTOList.add(new ProductDTO(
                    p.getId(),
                    p.getAccountBook().getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getPurchaseDate(),
                    p.getDetail(),
                    p.getCountry(),
                    p.getCity(),
                    p.getLikeCount()
            ));
        }

        //무한스크롤을 위한 길이 확인
        boolean hasNext = false;
        if(productList.size() > pageable.getPageSize()) {
            productDTOList.remove(pageable.getPageSize()); //확인했으면 삭제
            hasNext = true;
        }

        return new SliceImpl<>(productDTOList, pageable, hasNext);
    }

    // no-offset 방식 처리하는 메서드
    private BooleanExpression ltId(Long id) {
        if (id == null) {
            return null;
        }

        return product.id.lt(id);
    }

    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "id":
                        OrderSpecifier<?> orderId = QueryDslUtil.getSortedColumn(direction, product, "id");
                        orderSpecifierList.add(orderId);
                        break;
                    case "like":
                        OrderSpecifier<?> orderLike = QueryDslUtil.getSortedColumn(direction, product, "likeCount");
                        OrderSpecifier<?> orderLike_Id = QueryDslUtil.getSortedColumn(direction, product, "id");
                        orderSpecifierList.add(orderLike);
                        orderSpecifierList.add(orderLike_Id);
                    default:
                        break;
                }
            }
        }

        return orderSpecifierList;
    }

    public List<String> findNameListOrderByProductNameAsc(String word) {

        QProduct qp = product;

        return queryFactory
                .select(qp.name)
                .from(qp)
                .where(qp.name.contains(word))
                .orderBy(qp.name.asc())
                .distinct()
                .limit(5)
                .fetch();
    }
}
