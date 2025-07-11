package com.shop.services;

import com.shop.model.location.LocationEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Component
public class RouteCalculator {
    public List<LocationEntity> findBestRoute(List<LocationEntity> locations) {
        boolean[] visited = new boolean[locations.size()];
        List<LocationEntity> result = new ArrayList<>();
        LocationEntity curr = LocationEntity.builder().x(0).y(0).build();
        LocationEntity next = LocationEntity.builder().x(0).y(0).build();
        LocationEntity prev;
        int minDist = Integer.MAX_VALUE;
        int currDist;
        result.add(curr);

        for (int j = 1; j <= locations.size(); j++) {
            for (int i = 0; i < locations.size(); i++) {
                if (visited[i]) {
                    continue;
                }
                currDist = distance(curr, locations.get(i));
                if (currDist < minDist) {
                    next = locations.get(i);
                    minDist = currDist;
                }
            }
            visited[locations.indexOf(next)] = true;
            prev = curr;
            curr = next;
            List<LocationEntity> intermediatePoints = generateIntermediatePoints(prev, next);
            result.addAll(intermediatePoints);
            minDist = Integer.MAX_VALUE;
        }
        result.addAll(generateIntermediatePoints(result.getLast(), LocationEntity.builder().x(0).y(0).build()));

        return result;
    }

    private List<LocationEntity> generateIntermediatePoints(LocationEntity from, LocationEntity to) {
        List<LocationEntity> result = new ArrayList<>();
        if (from.getX() < to.getX()) {
            for (int i = from.getX() + 1; i <= to.getX(); i++) {
                result.add(LocationEntity.builder().x(i).y(from.getY()).build());
            }
        } else {
            for (int i = from.getX() - 1; i > to.getX(); i--) {
                result.add(LocationEntity.builder().x(i).y(from.getY()).build());
            }
        }
        if (from.getY() < to.getY()) {
            for (int i = from.getY() + 1; i <= to.getY(); i++) {
                result.add(LocationEntity.builder().x(to.getX()).y(i).build());
            }
        } else {
            for (int i = from.getY() - 1; i >= to.getY(); i--) {
                result.add(LocationEntity.builder().x(to.getX()).y(i).build());
            }
        }
        return result;
    }

    private int distance(LocationEntity location1, LocationEntity location2) {
        return Math.abs(location1.getX() - location2.getX()) + Math.abs(location1.getY() - location2.getY());
    }
}
