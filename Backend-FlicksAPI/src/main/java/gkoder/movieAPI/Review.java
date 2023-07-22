package gkoder.movieAPI;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Review {
    private ObjectId id;
    private String body;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Review(String reviewBody, LocalDateTime created, LocalDateTime updated) {
        this.body = reviewBody;
        this.created = created;
        this.updated = updated;
    }
}