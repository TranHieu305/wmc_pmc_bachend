package com.wms.wms.service.vrpsolver;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VrpSolverServiceImpl implements VrpSolverService {
    static class VehicleVrp {
        Long id; // Vehicle ID
        BigDecimal capacity;
        BigDecimal remainingCapacity;
        List<Long> route;

        public VehicleVrp(Long id, BigDecimal capacity) {
            this.id = id;
            this.capacity = capacity;
            this.remainingCapacity = capacity;
            this.route = new ArrayList<>();
            this.route.add(0L); // Start at depot
        }
    }

    @Override
    public String solveVrp(
            Long[] vehicleIds, BigDecimal[] vehicleCapacities,
            Long[] shipmentIds, BigDecimal[] shipmentWeights,
            BigDecimal[][] distanceMatrix) {

        int numVehicles = vehicleCapacities.length;
        int numShipments = shipmentWeights.length;

        // Mapping shipment IDs to their indices
        Map<Long, Integer> shipmentIndexMap = new HashMap<>();
        for (int i = 0; i < shipmentIds.length; i++) {
            shipmentIndexMap.put(shipmentIds[i], i);
        }

        boolean[] visited = new boolean[numShipments];
        visited[0] = true; // Depot is always visited

        // Initialize vehicles
        List<VehicleVrp> vehicleVrpList = new ArrayList<>();
        for (int i = 0; i < numVehicles; i++) {
            vehicleVrpList.add(new VehicleVrp(vehicleIds[i], vehicleCapacities[i]));
        }

        // Step 1: Assign shipments to vehicles (Greedy)
        for (int i = 1; i < numShipments; i++) {
            boolean assigned = false;
            for (VehicleVrp vehicle : vehicleVrpList) {
                if (vehicle.remainingCapacity.compareTo(shipmentWeights[i]) >= 0) {
                    vehicle.remainingCapacity = vehicle.remainingCapacity.subtract(shipmentWeights[i]);
                    vehicle.route.add(shipmentIds[i]);
                    assigned = true;
                    break;
                }
            }
            if (!assigned) {
                throw new RuntimeException("Cannot assign shipment " + i + " due to insufficient vehicle capacity.");
            }
        }

        // Step 2: Optimize routes for each vehicle using Nearest Neighbor
        for (VehicleVrp vehicle : vehicleVrpList) {
            optimizeRoute(vehicle, shipmentIndexMap, distanceMatrix, visited);
        }

        // Step 3: Generate output as a string
        return generateOutput(vehicleVrpList, distanceMatrix, shipmentIndexMap);
    }

    private void optimizeRoute(VehicleVrp vehicle, Map<Long, Integer> shipmentIndexMap, BigDecimal[][] distanceMatrix, boolean[] visited) {
        List<Long> optimizedRoute = new ArrayList<>();
        optimizedRoute.add(0L); // Start at depot

        int currentLocation = 0;
        while (optimizedRoute.size() < vehicle.route.size()) {
            int nearestLocation = -1;
            BigDecimal minDistance = BigDecimal.valueOf(Long.MAX_VALUE);

            for (int i = 1; i < distanceMatrix.length; i++) {
                if (!visited[i] && vehicle.route.contains(shipmentIndexMap.keySet().toArray()[i])) {
                    BigDecimal distance = distanceMatrix[currentLocation][i];
                    if (distance.compareTo(minDistance) < 0) {
                        nearestLocation = i;
                        minDistance = distance;
                    }
                }
            }

            if (nearestLocation == -1) break;

            Long nearestShipment = shipmentIndexMap.keySet().toArray(new Long[0])[nearestLocation];
            optimizedRoute.add(nearestShipment);
            visited[nearestLocation] = true;
            currentLocation = nearestLocation;
        }

        optimizedRoute.add(0L); // Return to depot
        vehicle.route = optimizedRoute;
    }

    private String generateOutput(List<VehicleVrp> vehicles, BigDecimal[][] distanceMatrix, Map<Long, Integer> shipmentIndexMap) {
        StringBuilder result = new StringBuilder();
        BigDecimal totalDistance = BigDecimal.ZERO;

        for (VehicleVrp vehicle : vehicles) {
            result.append("Vehicle ").append(vehicle.id).append(" route: ").append(vehicle.route).append("\n");
            BigDecimal routeDistance = calculateRouteDistance(vehicle.route, distanceMatrix, shipmentIndexMap);
            totalDistance = totalDistance.add(routeDistance);
            result.append("Distance for Vehicle ").append(vehicle.id).append(": ").append(routeDistance).append("\n");
        }

        result.append("Total Distance: ").append(totalDistance).append("\n");
        return result.toString();
    }

    private BigDecimal calculateRouteDistance(List<Long> route, BigDecimal[][] distanceMatrix, Map<Long, Integer> shipmentIndexMap) {
        BigDecimal distance = BigDecimal.ZERO;
        for (int i = 0; i < route.size() - 1; i++) {
            int fromIndex = route.get(i).equals(0L) ? 0 : shipmentIndexMap.get(route.get(i));
            int toIndex = route.get(i + 1).equals(0L) ? 0 : shipmentIndexMap.get(route.get(i + 1));
            distance = distance.add(distanceMatrix[fromIndex][toIndex]);
        }
        return distance;
    }
}
