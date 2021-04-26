import java.util.List;

public class PatientHistory {
    String patientId;
    String medicalCause;
}


class Patient {
    BasicPatient basicPatient;
    List<PatientHistory> histories;
}