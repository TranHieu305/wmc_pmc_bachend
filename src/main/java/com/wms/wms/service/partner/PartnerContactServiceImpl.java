package com.wms.wms.service.partner;

import com.wms.wms.dto.request.partner.PartnerContactRequest;
import com.wms.wms.dto.response.partner.PartnerContactResponse;
import com.wms.wms.entity.PartnerContact;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.partner.PartnerContactRequestMapper;
import com.wms.wms.mapper.partner.PartnerContactResponseMapper;
import com.wms.wms.repository.PartnerContactRepository;
import com.wms.wms.repository.PartnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartnerContactServiceImpl implements PartnerContactService {
    private final PartnerContactRepository partnerContactRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public List<PartnerContactResponse> findAll() {
        List<PartnerContact> partnerContacts = partnerContactRepository.findAll();
        log.info("Get all partner contacts service successfully");
        return PartnerContactResponseMapper.INSTANCE.toDtoList(partnerContacts);
    }

    @Override
    public PartnerContactResponse findById(Long entityId) {
        PartnerContact contact = getPartnerContactById(entityId);
        log.info("Get partner contact detail service id: {} successfully", entityId);
        return PartnerContactResponseMapper.INSTANCE.toDto(contact);
    }

    @Transactional
    @Override
    public PartnerContactResponse save(PartnerContactRequest partnerContactRequest) {
        validateRequest(partnerContactRequest);

        PartnerContact contact = PartnerContactRequestMapper.INSTANCE.toEntity(partnerContactRequest);
        PartnerContact dbContact = partnerContactRepository.save(contact);
        log.info("Save contact service successfully");

        return PartnerContactResponseMapper.INSTANCE.toDto(dbContact);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        PartnerContact contact = getPartnerContactById(id);
        partnerContactRepository.delete(contact);
        log.info("Delete partner contact by Id: {} successfully", id);
    }

    private PartnerContact getPartnerContactById(Long contactId) {
        return partnerContactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Partner contact not found with id : " + contactId));
    }

    private void validateRequest(PartnerContactRequest request) {
        // validate contactId
        if (request.getId() != 0) {
            getPartnerContactById(request.getId());
        }
        // validate partner of contact
        partnerRepository.findById(request.getPartnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Partner of contact not found with partner id : " + request.getPartnerId()));
    }
}
