package com.wms.wms.service.shipment;

import com.wms.wms.entity.Batch;
import com.wms.wms.entity.Shipment;
import com.wms.wms.entity.ShipmentBatch;
import com.wms.wms.entity.enumentity.status.ShipmentBatchStatus;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.ShipmentBatchRepository;
import com.wms.wms.service.batch.BatchService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShipmentBatchServiceImpl implements ShipmentBatchService{
    private final ShipmentBatchRepository shipmentBatchRepository;
    private final BatchService batchService;

    @Override
    @Transactional
    public void markAsDelivered(Long shipmentBatchId) {
        ShipmentBatch shipmentBatch = getById(shipmentBatchId);
        shipmentBatch.setStatus(ShipmentBatchStatus.DELIVERED);
        shipmentBatchRepository.save(shipmentBatch);
        log.info("Service Shipment batch - mark as delivered shipment batch ID: {} successfully", shipmentBatchId);

        //Process delivered batch
        Batch batch = shipmentBatch.getBatch();
        batchService.processDelivered(batch);
    }

    @Override
    @Transactional
    public void processInTransitShipment(Shipment shipment) {
        List<ShipmentBatch> shipmentBatchList = shipment.getShipmentBatches();
        for (ShipmentBatch shipmentBatch : shipmentBatchList) {
            shipmentBatch.setStatus(ShipmentBatchStatus.IN_TRANSIT);
        }
        shipmentBatchRepository.saveAll(shipmentBatchList);
        log.info("Service Shipment batch - change all shipment batches of shipment ID: {} to IN_TRANSIT successfully", shipment.getId());
    }

    private ShipmentBatch getById(Long id) {
        return shipmentBatchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment batch not found with id : " + id));
    }
}
