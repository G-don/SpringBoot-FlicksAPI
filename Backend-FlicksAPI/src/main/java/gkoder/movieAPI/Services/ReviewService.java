package gkoder.movieAPI.Services;

import gkoder.movieAPI.Movie;
import gkoder.movieAPI.Repository.ReviewRepository;
import gkoder.movieAPI.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId) {
        LocalDateTime now = LocalDateTime.now(); // Get the current date and time

        Review review = new Review(reviewBody, now, now); // Set the created and updated fields to the current date and time
        review = repository.insert(review);

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviews").value(review))
                .first();

        return review;
    }

    public List<Review> getReviewsForMovie(String imdbId) {
        Query query = new Query(Criteria.where("imdbId").is(imdbId));
        Movie movie = mongoTemplate.findOne(query, Movie.class);

        if (movie != null) {
            return movie.getReviews();
        } else {
            return null;
        }
    }
}
