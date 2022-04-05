package simulator.math2D;

import simulator.model.Hospital;

import java.util.ArrayList;
import java.util.List;

public class MapIdGenerator {
    private int idToAttachToCrossing;
    private int idToAttachToRoad;
    private List<Integer> hospitalsIds;


    public MapIdGenerator(List<Hospital> hospitals) {
        setHospitalsIds(hospitals);
        idToAttachToCrossing = 0;
        idToAttachToRoad = 0;
    }

    public int getIdToSetToRoad() {
        idToAttachToRoad += 1;
        return idToAttachToRoad - 1;
    }

    public int getIdToSetToCrossing() {
        while (true) {
            if (!hospitalsIds.contains(idToAttachToCrossing))
                return idToAttachToCrossing;
            idToAttachToCrossing++;
        }
    }

    private void setHospitalsIds(List<Hospital> hospitals) {
        hospitalsIds = new ArrayList<>();
        for (Hospital hospital: hospitals)
            hospitalsIds.add(hospital.getId());
    }
}
