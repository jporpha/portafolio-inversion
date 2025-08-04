package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.TransactionDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import com.jp.orpha.portafolio_inversion.entities.PriceEntity;
import com.jp.orpha.portafolio_inversion.entities.QuantityEntity;
import com.jp.orpha.portafolio_inversion.enums.TransactionType;
import com.jp.orpha.portafolio_inversion.exceptions.NotFoundException;
import com.jp.orpha.portafolio_inversion.repositories.AssetRepository;
import com.jp.orpha.portafolio_inversion.repositories.PortfolioRepository;
import com.jp.orpha.portafolio_inversion.repositories.PriceRepository;
import com.jp.orpha.portafolio_inversion.repositories.QuantityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;
    private final PriceRepository priceRepository;
    private final QuantityRepository quantityRepository;

    @Override
    public void processTransaction(TransactionDto dto) {
        PortfolioEntity portfolio = portfolioRepository.findById(dto.getPortfolioId())
                .orElseThrow(() -> new NotFoundException("Portfolio", "id", dto.getPortfolioId()));

        AssetEntity asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new NotFoundException("Asset", "id", dto.getAssetId()));

        PriceEntity price = priceRepository.findTopByAssetIdAndDateLessThanEqualOrderByDateDesc(asset.getId(), dto.getDate())
                .orElseThrow(() -> new NotFoundException("Price", "date", dto.getDate()));

        Double priceValue = price.getValue();

        Double quantityChange = dto.getAmount() / priceValue;
        if (dto.getType() == TransactionType.SELL) {
            quantityChange = -quantityChange;
        }

        QuantityEntity quantity = quantityRepository.findByPortfolioIdAndAssetId(dto.getPortfolioId(), asset.getId())
                .orElse(null);

        if (quantity != null) {
            quantity.setQuantity(quantity.getQuantity() + quantityChange);
        } else {
            quantity = new QuantityEntity();
            quantity.setPortfolio(portfolio);
            quantity.setAsset(asset);
            quantity.setQuantity(quantityChange);
            quantity.setDate(dto.getDate());
        }

        quantityRepository.save(quantity);
    }
}

