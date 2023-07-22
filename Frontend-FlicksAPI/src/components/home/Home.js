import React from 'react';
import Hero from '../hero/Hero';

const Home = ({ movies, getMovieData }) => {
  return <Hero movies={movies} getMovieData={getMovieData} />;
};

export default Home;
