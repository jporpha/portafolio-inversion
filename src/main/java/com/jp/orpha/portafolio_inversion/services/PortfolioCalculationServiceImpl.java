package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.AssetStatusDto;
import com.jp.orpha.portafolio_inversion.dtos.AssetWeightDto;
import com.jp.orpha.portafolio_inversion.dtos.InitialQuantityDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioPerformanceDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioStatusDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioDailyStatusDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioEvolutionDto;
import com.jp.orpha.portafolio_inversion.dtos.RebalanceActionDto;
import com.jp.orpha.portafolio_inversion.enums.RebalanceActionType;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.HoldingEntity;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import com.jp.orpha.portafolio_inversion.entities.PriceEntity;
import com.jp.orpha.portafolio_inversion.entities.WeightEntity;
import com.jp.orpha.portafolio_inversion.exceptions.NotFoundException;
import com.jp.orpha.portafolio_inversion.repositories.HoldingRepository;
import com.jp.orpha.portafolio_inversion.repositories.PortfolioRepository;
import com.jp.orpha.portafolio_inversion.repositories.PriceRepository;
import com.jp.orpha.portafolio_inversion.repositories.QuantityRepository;
import com.jp.orpha.portafolio_inversion.repositories.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioCalculationServiceImpl implements PortfolioCalculationService {

    private final PortfolioRepository portfolioRepository;
    private final QuantityRepository quantityRepository;
    private final PriceRepository priceRepository;
    private final WeightRepository weightRepository;
    private final HoldingRepository holdingRepository;

    @Override
    public PortfolioStatusDto getPortfolioStatus(Long portfolioId) {

        PortfolioEntity portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NotFoundException(this, "id", portfolioId));

        List<HoldingEntity> holdings = holdingRepository.findByPortfolioId(portfolioId);

        List<AssetStatusDto> assets = new ArrayList<>();
        double totalValue = 0.0;

        for (HoldingEntity holding : holdings) {
            AssetEntity asset = holding.getAsset();
            Double quantity = holding.getQuantity();

            PriceEntity latestPrice = priceRepository.findTopByAssetIdOrderByDateDesc(asset.getId())
                    .orElseThrow(() -> new NotFoundException("Price", "assetId", asset.getId()));

            Double price = latestPrice.getValue();
            Double value = quantity * price;

            AssetStatusDto assetStatus = new AssetStatusDto();
            assetStatus.setAssetId(asset.getId());
            assetStatus.setAssetName(asset.getName());
            assetStatus.setQuantity(quantity);
            assetStatus.setPrice(price);
            assetStatus.setValue(value);

            assets.add(assetStatus);
            totalValue += value;
        }

        for (AssetStatusDto asset : assets) {
            asset.setWeight(asset.getValue() / totalValue * 100);
        }

        PortfolioStatusDto dto = new PortfolioStatusDto();
        dto.setPortfolioId(portfolioId);
        dto.setPortfolioName(portfolio.getName());
        dto.setAssets(assets);
        dto.setTotalValue(totalValue);

        return dto;
    }
    @Override
    public List<AssetWeightDto> calculatePortfolioWeights(Long portfolioId) {
        PortfolioEntity portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NotFoundException(this, "id", portfolioId));

        List<HoldingEntity> holdings = holdingRepository.findByPortfolioId(portfolioId);

        List<AssetWeightDto> weights = new ArrayList<>();
        double totalValue = 0.0;

        Map<Long, Double> assetValues = new HashMap<>();

        for (HoldingEntity holding : holdings) {
            AssetEntity asset = holding.getAsset();
            Double quantity = holding.getQuantity();

            PriceEntity latestPrice = priceRepository.findTopByAssetIdOrderByDateDesc(asset.getId())
                    .orElseThrow(() -> new NotFoundException("Price", "assetId", asset.getId()));

            Double price = latestPrice.getValue();
            Double value = quantity * price;

            assetValues.put(asset.getId(), value);
            totalValue += value;
        }

        for (HoldingEntity holding : holdings) {
            AssetEntity asset = holding.getAsset();
            Double value = assetValues.get(asset.getId());

            Double weight = totalValue != 0 ? (value / totalValue * 100) : 0.0;

            AssetWeightDto dto = new AssetWeightDto();
            dto.setAssetId(asset.getId());
            dto.setAssetName(asset.getName());
            dto.setWeight(weight);

            weights.add(dto);
        }


        return weights;
    }

    @Override
    public List<RebalanceActionDto> rebalancePortfolio(Long portfolioId) {

        List<HoldingEntity> holdings = holdingRepository.findByPortfolioId(portfolioId);

        double totalValue = 0.0;
        Map<Long, Double> assetValues = new HashMap<>();
        Map<Long, Double> currentQuantities = new HashMap<>();

        for (HoldingEntity holding : holdings) {
            AssetEntity asset = holding.getAsset();
            Double quantity = holding.getQuantity();

            PriceEntity latestPrice = priceRepository.findTopByAssetIdOrderByDateDesc(asset.getId())
                    .orElseThrow(() -> new NotFoundException("Price", "assetId", asset.getId()));

            double price = latestPrice.getValue();
            double value = quantity * price;

            totalValue += value;
            assetValues.put(asset.getId(), value);
            currentQuantities.put(asset.getId(), quantity);
        }

        List<WeightEntity> targetWeights = weightRepository.findByPortfolioId(portfolioId);
        Map<Long, Double> targetWeightMap = new HashMap<>();
        for (WeightEntity weight : targetWeights) {
            targetWeightMap.put(weight.getAsset().getId(), weight.getWeight());
        }

        List<RebalanceActionDto> actions = new ArrayList<>();

        for (HoldingEntity holding : holdings) {
            Long assetId = holding.getAsset().getId();
            String assetName = holding.getAsset().getName();

            double currentValue = assetValues.get(assetId);
            double currentWeight = (currentValue / totalValue) * 100;

            Double targetWeight = targetWeightMap.get(assetId);
            if (targetWeight == null) {
                continue;
            }

            double deltaPercentage = targetWeight - currentWeight;
            if (Math.abs(deltaPercentage) < 0.01) {
                continue;
            }

            PriceEntity latestPrice = priceRepository.findTopByAssetIdOrderByDateDesc(assetId)
                    .orElseThrow(() -> new NotFoundException("Price", "assetId", assetId));
            double price = latestPrice.getValue();

            double targetValue = (targetWeight / 100) * totalValue;
            double valueDifference = targetValue - currentValue;

            double quantityDifference = valueDifference / price;

            RebalanceActionType action = quantityDifference > 0 ? RebalanceActionType.BUY : RebalanceActionType.SELL;

            RebalanceActionDto dto = new RebalanceActionDto();
            dto.setAssetId(assetId);
            dto.setAssetName(assetName);
            dto.setAction(action);
            dto.setQuantity(Math.abs(quantityDifference));

            dto.setCurrentValue(currentValue);
            dto.setTargetWeight(targetWeight);
            dto.setTargetValue(targetValue);
            dto.setDifference(valueDifference);

            actions.add(dto);
        }

        return actions;
    }

    @Override
    public List<PortfolioPerformanceDto> getPortfolioPerformance(Long portfolioId){

        List<HoldingEntity> holdings = holdingRepository.findByPortfolioId(portfolioId);

        Set<Long> assetIds = holdings.stream()
                .map(h -> h.getAsset().getId())
                .collect(Collectors.toSet());

        List<PriceEntity> allPrices = priceRepository.findByAssetIdIn(assetIds);

        Map<LocalDate, List<PriceEntity>> pricesByDate = allPrices.stream()
                .collect(Collectors.groupingBy(PriceEntity::getDate));

        List<PortfolioPerformanceDto> performance = pricesByDate.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<PriceEntity> prices = entry.getValue();

                    double dailyValue = 0.0;

                    for (HoldingEntity holding : holdings) {
                        Long assetId = holding.getAsset().getId();
                        Optional<PriceEntity> priceOpt = prices.stream()
                                .filter(p -> p.getAsset().getId().equals(assetId))
                                .findFirst();

                        if (priceOpt.isPresent()) {
                            double quantity = holding.getQuantity();
                            double price = priceOpt.get().getValue();
                            dailyValue += quantity * price;
                        }
                    }

                    PortfolioPerformanceDto dto = new PortfolioPerformanceDto();
                    dto.setDate(date);
                    dto.setTotalValue(dailyValue);
                    return dto;
                })
                .sorted(Comparator.comparing(PortfolioPerformanceDto::getDate))
                .collect(Collectors.toList());

        return performance;
    }

    @Override
    public List<InitialQuantityDto> calculateInitialQuantities(Long portfolioId, LocalDate initialDate, Double initialPortfolioValue) {

        List<WeightEntity> weights = weightRepository.findByPortfolioIdAndDate(portfolioId, initialDate);

        if (weights.isEmpty()) {
            throw new NotFoundException("Weights", "portfolioId or date", portfolioId + " - " + initialDate);
        }

        List<InitialQuantityDto> result = new ArrayList<>();

        for (WeightEntity weight : weights) {
            AssetEntity asset = weight.getAsset();

            PriceEntity price = priceRepository.findByAssetIdAndDate(asset.getId(), initialDate)
                    .orElseThrow(() -> new NotFoundException("Price", "assetId/date", asset.getId() + " - " + initialDate));

            double quantity = (weight.getWeight() * initialPortfolioValue) / price.getValue();

            InitialQuantityDto dto = new InitialQuantityDto();
            dto.setAssetId(asset.getId());
            dto.setAssetName(asset.getName());
            dto.setWeight(weight.getWeight());
            dto.setPrice(price.getValue());
            dto.setQuantity(quantity);

            result.add(dto);
        }

        return result;
    }

    @Override
    public PortfolioEvolutionDto getPortfolioEvolution(Long portfolioId, LocalDate startDate, LocalDate endDate) {

        PortfolioEntity portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NotFoundException(this, "id", portfolioId));

        List<HoldingEntity> holdings = holdingRepository.findByPortfolioId(portfolioId);

        if (holdings.isEmpty()) {
            throw new IllegalStateException("El portafolio no contiene activos.");
        }

        Map<Long, Double> quantities = new HashMap<>();
        for (HoldingEntity holding : holdings) {
            quantities.put(holding.getAsset().getId(), holding.getQuantity());
        }

        List<PriceEntity> allPrices = priceRepository.findByDateBetween(startDate, endDate);

        Map<LocalDate, List<PriceEntity>> pricesByDate = allPrices.stream()
                .collect(Collectors.groupingBy(PriceEntity::getDate));

        List<PortfolioDailyStatusDto> evolution = new ArrayList<>();

        for (LocalDate date : pricesByDate.keySet().stream().sorted().toList()) {
            List<PriceEntity> pricesForDate = pricesByDate.get(date);

            double totalValue = 0.0;
            Map<Long, Double> valuesByAsset = new HashMap<>();

            for (PriceEntity price : pricesForDate) {
                Long assetId = price.getAsset().getId();
                if (!quantities.containsKey(assetId)) continue;

                Double quantity = quantities.get(assetId);
                Double value = quantity * price.getValue();

                valuesByAsset.put(assetId, value);
                totalValue += value;
            }

            List<AssetWeightDto> weights = new ArrayList<>();
            for (Map.Entry<Long, Double> entry : valuesByAsset.entrySet()) {
                Long assetId = entry.getKey();
                Double value = entry.getValue();
                Double weight = totalValue == 0.0 ? 0.0 : (value / totalValue) * 100;

                AssetWeightDto assetWeight = new AssetWeightDto();
                assetWeight.setAssetId(assetId);
                assetWeight.setValue(value);
                assetWeight.setWeight(weight);
                weights.add(assetWeight);
            }

            PortfolioDailyStatusDto dailyStatus = new PortfolioDailyStatusDto();
            dailyStatus.setDate(date);
            dailyStatus.setTotalValue(totalValue);
            dailyStatus.setAssetsWeight(weights);

            evolution.add(dailyStatus);
        }

        PortfolioEvolutionDto result = new PortfolioEvolutionDto();
        result.setPortfolioId(portfolioId);
        result.setPortfolioName(portfolio.getName());
        result.setEvolution(evolution);

        return result;
    }

}

