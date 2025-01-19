package com.wms.wms.service.partner;

import com.wms.wms.dto.request.partner.PartnerRequest;
import com.wms.wms.dto.request.partner.PartnerUpdateRequest;
import com.wms.wms.dto.response.partner.PartnerResponse;
import com.wms.wms.entity.Order;
import com.wms.wms.entity.Partner;
import com.wms.wms.entity.PartnerAddress;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.partner.PartnerRequestMapper;
import com.wms.wms.mapper.partner.PartnerResponseMapper;
import com.wms.wms.repository.OrderRepository;
import com.wms.wms.repository.PartnerAddressRepository;
import com.wms.wms.repository.PartnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartnerServiceImpl implements PartnerService{
    private final PartnerRepository partnerRepository;
    private final PartnerAddressRepository partnerAddressRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<PartnerResponse> findAll() {
        List<Partner> partners = partnerRepository.findAll();
        log.info("Service Get all partners successfully");
        List<PartnerResponse> responses = PartnerResponseMapper.INSTANCE.toDtoList(partners);
        for (PartnerResponse response : responses) {
            response.setAddress(response.getPartnerAddresses().get(0).getAddress());
        }
        return responses;
    }

    @Override
    public PartnerResponse findById(Long entityId) {
        Partner partner = getPartnerById(entityId);
        log.info("Service Get partner detail id: {} successfully", entityId);
        return PartnerResponseMapper.INSTANCE.toDto(partner);
    }

    @Transactional
    @Override
    public PartnerResponse save(PartnerRequest partnerRequest) {
        validateRequest(partnerRequest);

        Partner partner = PartnerRequestMapper.INSTANCE.toEntity(partnerRequest);
        Partner dbPartner = partnerRepository.save(partner);

        // Save partner address
        if (StringUtils.hasText(partnerRequest.getAddressName())
            && StringUtils.hasText(partnerRequest.getAddress())) {

            PartnerAddress address1 = new PartnerAddress();
            address1.setPartnerId(dbPartner.getId());
            address1.setName(partnerRequest.getAddressName());
            address1.setAddress(partnerRequest.getAddress());
            address1.setLongitude(partnerRequest.getLongitude());
            address1.setLatitude(partnerRequest.getLatitude());

            PartnerAddress dbAddress = partnerAddressRepository.save(address1);
            log.info("Service Save partner address successfully");
            partner.getPartnerAddresses().add(dbAddress);
        }

        log.info("Service Save partner successfully");
        return PartnerResponseMapper.INSTANCE.toDto(dbPartner);
    }

    @Override
    @Transactional
    public PartnerResponse update(PartnerUpdateRequest request) {
        Partner dbPartner = getPartnerById(request.getId());
        dbPartner.setName(request.getName());
        dbPartner.setType(request.getType());
        dbPartner.setDescription(request.getDescription());
        dbPartner.setEmail(request.getEmail());
        dbPartner.setPhoneNumber(request.getPhoneNumber());
        partnerRepository.save(dbPartner);
        log.info("Service Partner - update partner Id: {} successfully", request.getId());
        return PartnerResponseMapper.INSTANCE.toDto(dbPartner);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Partner partner = getPartnerById(id);
        List<Order> orders = orderRepository.findAllByPartnerId(partner.getId());
        if (!orders.isEmpty()) {
            throw new ConstraintViolationException("Cannot delete a partner that already has associated orders");
        }
        partnerRepository.delete(partner);
        log.info("Service Delete partner by Id: {} successfully", id);
    }

    private Partner getPartnerById(Long partnerId) {
        return partnerRepository.findById(partnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id : " + partnerId));
    }

    private void validateRequest(PartnerRequest request) {
        if (request.getId() != 0) {
            getPartnerById(request.getId());
        }
    }
}
