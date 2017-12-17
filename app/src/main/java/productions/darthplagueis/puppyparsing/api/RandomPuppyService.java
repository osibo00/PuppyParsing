package productions.darthplagueis.puppyparsing.api;

import productions.darthplagueis.puppyparsing.model.RandomPuppy;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by oleg on 12/17/17.
 */

public interface RandomPuppyService {

    @GET("api/breeds/image/random")
    Call<RandomPuppy> getPuppy();
}
