package com.inditex.rate.rate_service.application.ports;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inditex.rate.rate_service.domain.model.Rate;

@Repository
public interface RateRepositoryJpa extends JpaRepository<Rate, Long> {

        @Query(value = """
                        SELECT r.* FROM t_rates r WHERE
                                    :date BETWEEN r.start_date AND r.end_date
                                    AND r.product_id = :productId
                                    AND r.brand_id = :brandId
                        """, nativeQuery = true)
        Optional<Rate> findRateByDateAndProductIdAndBrandId(
                        @Param("date") LocalDate date,
                        @Param("productId") Long productId,
                        @Param("brandId") Long brandId);
}
