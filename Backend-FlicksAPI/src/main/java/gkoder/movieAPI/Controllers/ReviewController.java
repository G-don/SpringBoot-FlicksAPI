package gkoder.movieAPI.Controllers;


import gkoder.movieAPI.Review;
import gkoder.movieAPI.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/reviews")

public class ReviewController {
    @Autowired
    private ReviewService service;

    @PostMapping("/add")
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {

        return new ResponseEntity<Review>(service.createReview(payload.get("reviewBody"), payload.get("imdbId")), HttpStatus.OK);
    }
    @GetMapping("/movies/{imdbId}")
    public ResponseEntity<List<Review>> getReviewsForMovie(@PathVariable String imdbId) {
        List<Review> reviews = service.getReviewsForMovie(imdbId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}