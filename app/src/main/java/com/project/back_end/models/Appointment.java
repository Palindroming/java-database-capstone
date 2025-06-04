import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.annotation.processing.Generated;

@Getter
@Setter
public class Appointment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotNull
    @JoinColumn(name = "patient_id")
    @NotNull
    private Patient patient;
    
    @Future(message = "Appointment time must be in the future")   
    private LocalDateTime appointmentTime;




}