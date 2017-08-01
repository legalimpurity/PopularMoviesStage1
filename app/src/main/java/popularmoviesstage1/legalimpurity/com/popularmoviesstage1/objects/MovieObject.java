package popularmoviesstage1.legalimpurity.com.popularmoviesstage1.objects;

/**
 * Created by rajatkhanna on 01/08/17.
 */

public class MovieObject {
    private String OrignalTitle;
    private String MoviePosterImageThumbnailUrl;
    private String PlotSynopsis;
    private String UserRating;
    private Long ReleaseDate;

    public MovieObject(String orignalTitle, String moviePosterImageThumbnailUrl, String plotSynopsis, String userRating, Long releaseDate) {
        OrignalTitle = orignalTitle;
        MoviePosterImageThumbnailUrl = moviePosterImageThumbnailUrl;
        PlotSynopsis = plotSynopsis;
        UserRating = userRating;
        ReleaseDate = releaseDate;
    }

    public String getOrignalTitle() {
        return OrignalTitle;
    }

    public void setOrignalTitle(String orignalTitle) {
        OrignalTitle = orignalTitle;
    }

    public String getMoviePosterImageThumbnailUrl() {
        return MoviePosterImageThumbnailUrl;
    }

    public void setMoviePosterImageThumbnailUrl(String moviePosterImageThumbnailUrl) {
        MoviePosterImageThumbnailUrl = moviePosterImageThumbnailUrl;
    }

    public String getPlotSynopsis() {
        return PlotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        PlotSynopsis = plotSynopsis;
    }

    public String getUserRating() {
        return UserRating;
    }

    public void setUserRating(String userRating) {
        UserRating = userRating;
    }

    public Long getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(Long releaseDate) {
        ReleaseDate = releaseDate;
    }
}
