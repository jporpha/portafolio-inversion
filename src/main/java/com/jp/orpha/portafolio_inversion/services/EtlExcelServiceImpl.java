package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.InitialQuantityDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.HoldingEntity;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import com.jp.orpha.portafolio_inversion.entities.PriceEntity;
import com.jp.orpha.portafolio_inversion.entities.WeightEntity;
import com.jp.orpha.portafolio_inversion.repositories.AssetRepository;
import com.jp.orpha.portafolio_inversion.repositories.HoldingRepository;
import com.jp.orpha.portafolio_inversion.repositories.PortfolioRepository;
import com.jp.orpha.portafolio_inversion.repositories.PriceRepository;
import com.jp.orpha.portafolio_inversion.repositories.WeightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtlExcelServiceImpl implements EtlExcelService {

    private final AssetRepository assetRepository;
    private final HoldingRepository holdingRepository;
    private final PriceRepository priceRepository;
    private final WeightRepository weightRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioCalculationService portfolioCalculationService;
    private final HoldingService holdingService;
    private LocalDate baseDate;
    private static final Double DEFAULT_INITIAL_PORTFOLIO_VALUE = 1_000_000_000.0;

    @Override
    public void processExcelFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet pricesSheet = workbook.getSheet("Precios");
            Sheet weightsSheet = workbook.getSheet("Weights");

            processPricesSheet(pricesSheet);

            LocalDate baseDate = extractBaseDateFromWeights(weightsSheet);

            processWeightsSheet(weightsSheet);
            generateInitialHoldingsFromExcel(baseDate, DEFAULT_INITIAL_PORTFOLIO_VALUE);

        } catch (Exception e) {
            log.error("Error processing Excel file", e);
            throw new RuntimeException("Error processing Excel file", e);
        }
    }

    private LocalDate extractBaseDateFromWeights(Sheet weightsSheet) {
        try {
            Row row1 = weightsSheet.getRow(1);
            Cell dateCell = row1.getCell(0);

            if (dateCell == null || dateCell.getCellType() != CellType.NUMERIC) {
                throw new RuntimeException("Invalid date cell in row 1");
            }

            return dateCell.getLocalDateTimeCellValue().toLocalDate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract base date from Excel", e);
        }
    }


    private void processPricesSheet(Sheet sheet) {
        Map<Integer, AssetEntity> columnToAsset = new HashMap<>();

        Row headerRow = sheet.getRow(0);
        for (int col = 1; col < headerRow.getPhysicalNumberOfCells(); col++) {
            String assetName = headerRow.getCell(col).getStringCellValue();
            AssetEntity asset = assetRepository.findByName(assetName)
                    .orElseGet(() -> {
                        AssetEntity newAsset = new AssetEntity();
                        newAsset.setName(assetName);
                        return assetRepository.save(newAsset);
                    });
            columnToAsset.put(col, asset);
        }

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;

            LocalDate date = row.getCell(0).getDateCellValue().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            for (int col = 1; col < row.getPhysicalNumberOfCells(); col++) {
                double price = row.getCell(col).getNumericCellValue();
                AssetEntity asset = columnToAsset.get(col);

                PriceEntity priceEntity = new PriceEntity();
                priceEntity.setDate(date);
                priceEntity.setValue(price);
                priceEntity.setAsset(asset);

                priceRepository.save(priceEntity);
            }
        }
    }

    private void processWeightsSheet(Sheet sheet) {
        Map<Integer, PortfolioEntity> columnToPortfolioMap = new HashMap<>();

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) throw new RuntimeException("No header on Weights sheet");

        for (int col = 2; col < headerRow.getLastCellNum(); col++) {
            String portfolioName = headerRow.getCell(col).getStringCellValue().trim();

            PortfolioEntity portfolio = portfolioRepository.findByName(portfolioName)
                    .orElseGet(() -> {
                        PortfolioEntity newPortfolio = new PortfolioEntity();
                        newPortfolio.setName(portfolioName);
                        return portfolioRepository.save(newPortfolio);
                    });

            columnToPortfolioMap.put(col, portfolio);
        }

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) break;

            Cell dateCell = row.getCell(0);
            if (dateCell == null || dateCell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(dateCell)) {
                break;
            }
            LocalDate baseDate = dateCell.getLocalDateTimeCellValue().toLocalDate();

            String assetName = row.getCell(1).getStringCellValue().trim();
            AssetEntity asset = assetRepository.findByName(assetName)
                    .orElseGet(() -> {
                        AssetEntity newAsset = new AssetEntity();
                        newAsset.setName(assetName);
                        return assetRepository.save(newAsset);
                    });

            for (int col = 2; col < row.getLastCellNum(); col++) {
                double weight = row.getCell(col).getNumericCellValue();
                PortfolioEntity portfolio = columnToPortfolioMap.get(col);

                WeightEntity weightEntity = new WeightEntity();
                weightEntity.setDate(baseDate);
                weightEntity.setAsset(asset);
                weightEntity.setPortfolio(portfolio);
                weightEntity.setWeight(weight);

                weightRepository.save(weightEntity);
            }
        }
    }

    private void generateInitialHoldingsFromExcel(LocalDate baseDate, double initialValue) {
        List<PortfolioEntity> portfolios = portfolioRepository.findAll();

        for (PortfolioEntity portfolio : portfolios) {
            List<InitialQuantityDto> quantities = portfolioCalculationService
                    .calculateInitialQuantities(portfolio.getId(), baseDate, initialValue);

            holdingService.deleteByPortfolioId(portfolio.getId());

            List<HoldingEntity> holdings = new ArrayList<>();
            for (InitialQuantityDto dto : quantities) {
                HoldingEntity holding = new HoldingEntity();
                holding.setAsset(assetRepository.findById(dto.getAssetId()).orElseThrow());
                holding.setPortfolio(portfolio);
                holding.setQuantity(dto.getQuantity());
                holdings.add(holding);
            }
            holdingRepository.saveAll(holdings);
        }
    }

}

