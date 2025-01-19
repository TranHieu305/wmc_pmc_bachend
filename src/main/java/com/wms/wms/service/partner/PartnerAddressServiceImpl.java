package com.wms.wms.service.partner;

import com.wms.wms.dto.request.partner.PartnerAddressRequest;
import com.wms.wms.entity.PartnerAddress;
import com.wms.wms.entity.ShipmentBatch;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.partner.PartnerAddressRequestMapper;
import com.wms.wms.repository.PartnerAddressRepository;
import com.wms.wms.repository.ShipmentBatchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartnerAddressServiceImpl implements PartnerAddressService{
    private final PartnerAddressRepository partnerAddressRepository;
    private final ShipmentBatchRepository shipmentBatchRepository;

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
    @Transactional
    public PartnerAddress save(PartnerAddressRequest partnerAddress) {
        if (partnerAddress.getId() != 0) {
            getById(partnerAddress.getId());
        }
        PartnerAddress address = PartnerAddressRequestMapper.INSTANCE.toEntity(partnerAddress);
        PartnerAddress dbAddress = partnerAddressRepository.save(address);
        log.info("Service Partner address - Save address ID:{} successfully", dbAddress.getId());
        return dbAddress;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        PartnerAddress address = getById(id);
        List<ShipmentBatch> shipmentBatchList = shipmentBatchRepository.findAllByPartnerAddressId(id);
        if (!shipmentBatchList.isEmpty()) {
            throw new ConstraintViolationException("Cannot delete address already had shipment");
        }
        partnerAddressRepository.delete(address);
        log.info("Service Partner address - Delete address ID:{} successfully", id);
    }

    private PartnerAddress getById(Long addressId) {
        return partnerAddressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Partner address not found with id : " + addressId));
    }
}
