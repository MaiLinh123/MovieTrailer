package com.mlpopularmvs.movietrailer.data.remote;

import static com.mlpopularmvs.movietrailer.ui.movie.MovieActivity.idOfMovie;
import static com.mlpopularmvs.movietrailer.utils.Constant.API_KEY;

import com.mlpopularmvs.movietrailer.data.model.CastApiResponse;
import com.mlpopularmvs.movietrailer.data.model.MovieApiResponse;
import com.mlpopularmvs.movietrailer.data.model.ReviewApiResponse;
import com.mlpopularmvs.movietrailer.data.model.TrailerApiResponse;

import javax.inject.Inject;

import retrofit2.Call;

public class RemoteDataSource {

    private final MovieService movieService;

    @Inject
    public RemoteDataSource(MovieService movieService){
        this.movieService = movieService;
    }

    public MovieDataSourceFactory getRemoteMovies(String sort) {
        // Get our database source factory
        return new MovieDataSourceFactory(movieService, sort);
    }

    public Call<MovieApiResponse> getSearchedList(String query) {
        return movieService.searchForMovies(query, API_KEY);
    }

    public Call<TrailerApiResponse> getTrailerList() {
        return movieService.getTrailers((idOfMovie), API_KEY);
    }

    public Call<ReviewApiResponse> getReviewList() {
        return movieService.getReviews((idOfMovie), API_KEY);
    }

    public Call<CastApiResponse> getCastList() {
        return movieService.getCast((idOfMovie), API_KEY);
    }
}
