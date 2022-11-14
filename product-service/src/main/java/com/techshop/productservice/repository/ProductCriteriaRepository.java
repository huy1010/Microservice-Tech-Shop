package com.techshop.productservice.repository;

import com.techshop.productservice.entity.Variant;
import com.techshop.productservice.search.ProductSearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProductCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<Variant> findAllWithFilters( ProductSearchCriteria productSearchCriteria) {
        CriteriaQuery<Variant> criteriaQuery = criteriaBuilder.createQuery(Variant.class);

        Root<Variant> variantRoot = criteriaQuery.from(Variant.class);


        Predicate predicate = getPredicate(productSearchCriteria, variantRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Variant> typedQuery = entityManager.createQuery(criteriaQuery);
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery cq = cb.createQuery(Variant.class);
//        Root root = cq.from(Variant.class);
//
//        //
//        Subquery subProduct = cq.subquery(Long.class);
//        Root productRoot = subProduct.from(Product.class);
//        SetJoin<Product, Variant> subAuthors = productRoot.join(Variant_.product);


        return typedQuery.getResultList();
    }

    private Predicate getPredicate(ProductSearchCriteria productSearchCriteria, Root<Variant> variantRoot) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.notEqual(variantRoot.get("activeFlag"), "D"));

        if (Objects.nonNull(productSearchCriteria.getProductName())) {
            predicates.add(criteriaBuilder.like(variantRoot.get("product").get("productName"), "%" + productSearchCriteria.getProductName() + "%"));
        }

        if (Objects.nonNull(productSearchCriteria.getCategoryId())) {
            if (!productSearchCriteria.getCategoryId().isEmpty()) {
                variantRoot.fetch("product", JoinType.LEFT);
                predicates.add(criteriaBuilder.in(variantRoot.get("product").get("category").get("categoryId")).value(productSearchCriteria.getCategoryId()));
            }
        }

        if (Objects.nonNull(productSearchCriteria.getBrandId())) {
            if (!productSearchCriteria.getBrandId().isEmpty()) {
                variantRoot.fetch("product", JoinType.LEFT);
                predicates.add(criteriaBuilder.in(variantRoot.get("product").get("brand").get("brandId")).value(productSearchCriteria.getBrandId()));
            }
        }

        if (Objects.nonNull(productSearchCriteria.getTagId())) {
            if (!productSearchCriteria.getTagId().isEmpty()) {
                variantRoot.fetch("attributes", JoinType.LEFT);
                predicates.add(criteriaBuilder.in(variantRoot.get("attributes").get("tagId")).value(productSearchCriteria.getTagId()));
            }
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
