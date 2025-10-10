package com.hotelclover.hotelclover.Services.MGestionDeTarifas;

import com.hotelclover.hotelclover.Dto.MGestionDeTarifas.TarifasDTO;
import com.hotelclover.hotelclover.Models.MGestionDeTarifas.Tarifa;
import com.hotelclover.hotelclover.Repositories.MGestionDeTarifas.TarifasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TarifasService {

    private final TarifasRepository tarifasRepository;

    public TarifasDTO createTariff(TarifasDTO dto) {
        Tarifa entity = new Tarifa();
        BeanUtils.copyProperties(dto, entity);
        Tarifa saved = tarifasRepository.save(entity);
        TarifasDTO response = new TarifasDTO();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    public List<TarifasDTO> getAllTariffs() {
        return StreamSupport.stream(tarifasRepository.findAll().spliterator(), false)
                .map(t -> {
                    TarifasDTO dto = new TarifasDTO();
                    BeanUtils.copyProperties(t, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Optional<TarifasDTO> getTariffById(Long id) {
        return tarifasRepository.findById(id).map(t -> {
            TarifasDTO dto = new TarifasDTO();
            BeanUtils.copyProperties(t, dto);
            return dto;
        });
    }

    public Optional<TarifasDTO> updateTariff(Long id, TarifasDTO dto) {
        return tarifasRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(dto, existing, "idTarifa", "fechaCreacion");
            Tarifa updated = tarifasRepository.save(existing);
            TarifasDTO response = new TarifasDTO();
            BeanUtils.copyProperties(updated, response);
            return response;
        });
    }

    public boolean deleteTariff(Long id) {
        if (tarifasRepository.existsById(id)) {
            tarifasRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
