import React, { useEffect, useRef, useState } from 'react';
import api from '../../api/axiosConfig';
import { useParams } from 'react-router-dom';
import { Container, Row, Col } from 'react-bootstrap';
import ReviewForm from '../reviewForm/ReviewForm';

const Reviews = ({ getMovieData, movie, reviews, setReviews }) => {
    const revText = useRef();
    const [movieId, setMovieId] = useState(null);
    const params = useParams();
    const { imdbId } = useParams();
  
    useEffect(() => {
      if (params.movieId !== movieId) {
        setMovieId(params.movieId);
        getMovieData(params.movieId);
      }
    }, [params.movieId, movieId, getMovieData]);
  
    useEffect(() => {
      if (movie && movie.imdbId) {
        getReviewsByImdbId(movie.imdbId);
      }
    }, [movie]);
  
    const getReviewsByImdbId = async (imdbId) => {
      try {
        const response = await api.get(`/api/v1/reviews/movies/${imdbId}`);
        const movieData = response.data;
        if (movieData && Array.isArray(movieData)) {
          setReviews(movieData); 
        } else {
          setReviews([]); 
        }
      } catch (error) {
        console.error(error);
      }
    };
    
    const addReview = async (e) => {
      e.preventDefault();
      const rev = revText.current;
      try {
        const response = await api.post("/api/v1/reviews/add", {
          reviewBody: rev.value, 
          imdbId: movieId,
        });
        const updatedReviews = [...reviews, { body: rev.value }];
        rev.value = '';
        setReviews(updatedReviews);
      } catch (err) {
        console.error(err);
      }
    };
    
    return (
    <Container>
      <Row>
        <Col>
          <h3 style={{"color":'#82d4f0'}}>Reviews</h3>
        </Col>
      </Row>
      <Row className="mt-2">
        <Col>
          <img src={movie?.poster} alt="" />
        </Col>
        <Col>
          <Row>
            <Col>
              <ReviewForm handleSubmit={addReview} revText={revText} labelText="Write a Review?"/>
            </Col>
          </Row>
          <Row>
            <Col>
              <hr />
            </Col>
          </Row>
            {reviews.map((review, index) => (
            <React.Fragment key={index}>
              <Row>
                <Col>{review.body}</Col>
              </Row>
              <Row>
                <Col>
                  <hr />
                </Col>
              </Row>
            </React.Fragment>
          ))}
        </Col>
      </Row>
    </Container>
  );
};

export default Reviews;
