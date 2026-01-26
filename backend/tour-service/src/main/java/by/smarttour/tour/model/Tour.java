package by.smarttour.tour.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tours")
@Data // Если есть Lombok
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private Double price;
}