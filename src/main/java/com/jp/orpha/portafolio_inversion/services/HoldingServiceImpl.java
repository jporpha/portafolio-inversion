package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.HoldingDto;
import com.jp.orpha.portafolio_inversion.entities.HoldingEntity;
import com.jp.orpha.portafolio_inversion.exceptions.NotFoundException;
import com.jp.orpha.portafolio_inversion.mappers.HoldingMapper;
import com.jp.orpha.portafolio_inversion.repositories.HoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HoldingServiceImpl implements HoldingService {

    private final HoldingRepository holdingRepository;
    private final HoldingMapper holdingMapper;

    @Override
    public List<HoldingDto> getAllHoldings() {
        return holdingRepository.findAll()
                .stream()
                .map(holdingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public HoldingDto getHoldingById(Long id) {
        HoldingEntity entity = holdingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));
        return holdingMapper.toDto(entity);
    }

    @Override
    public HoldingDto createHolding(HoldingDto holdingDto) {
        HoldingEntity entity = holdingMapper.toEntity(holdingDto);
        return holdingMapper.toDto(holdingRepository.save(entity));
    }

    @Override
    public HoldingDto updateHolding(Long id, HoldingDto holdingDto) {
        HoldingEntity existing = holdingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));

        existing.setAsset(holdingMapper.toEntity(holdingDto).getAsset());
        existing.setPortfolio(holdingMapper.toEntity(holdingDto).getPortfolio());
        existing.setQuantity(holdingDto.getQuantity());

        return holdingMapper.toDto(holdingRepository.save(existing));
    }

    @Override
    public Boolean deleteHoldingById(Long id) {
        if (!holdingRepository.existsById(id)) {
            throw new NotFoundException(this, "id", id);
        }
        holdingRepository.deleteById(id);
        return true;
    }
}