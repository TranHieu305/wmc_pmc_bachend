package com.wms.wms.service.partner;

import com.wms.wms.entity.PartnerAddress;
import com.wms.wms.repository.PartnerAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartnerAddressServiceImpl implements PartnerAddressService{
    private final PartnerAddressRepository partnerAddressRepository;

    @Override
    public List<PartnerAddress> findAll() {
        List<PartnerAddress> addresses = partnerAddressRepository.findAll();
        log.info("Service Partner address - Get all addresses successfully");
        return addresses;
    }

    @Override
    public PartnerAddress findById(Long entityId) {
        return null;
    }

    @Override
    public PartnerAddress save(PartnerAddress partnerAddress) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
