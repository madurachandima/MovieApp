import com.madura.movieapp.data.dto.movieListDto.Movie

data class MovieSuggestionState(
    val isLoading: Boolean = false,
    val suggestions: ArrayList<Movie> = arrayListOf(),
    val error: String = "",
)
