package simulator.math2D;

import simulator.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientIdGenerator {
    public static int generatePatientId(List<Patient> patients) {
        List<Integer> patientsIds = getPatientsIds(patients);
        int idToGenerate = 0;
        while (true) {
            if (!patientsIds.contains(idToGenerate))
                return idToGenerate;
            idToGenerate++;
        }
    }

    private static List<Integer> getPatientsIds(List<Patient> patients) {
        List<Integer> patientsIds = new ArrayList<>();
        for (Patient patient : patients)
            patientsIds.add(patient.getId());

        return patientsIds;
    }
}
