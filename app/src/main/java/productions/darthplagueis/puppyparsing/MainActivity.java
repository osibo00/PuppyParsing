package productions.darthplagueis.puppyparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import productions.darthplagueis.puppyparsing.api.RandomPuppyService;
import productions.darthplagueis.puppyparsing.model.RandomPuppy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "JSON?";
    private Button button;
    private ImageView imageView;
    private RandomPuppyService randomPuppyService;
    private String randomPuppyURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.new_puppy_button);
        imageView = (ImageView) findViewById(R.id.puppy_imageview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // creating the service so we can use it to make requests:
        randomPuppyService = retrofit.create(RandomPuppyService.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Call<RandomPuppy> randomPuppyCall = randomPuppyService.getPuppy();
                randomPuppyCall.enqueue(new Callback<RandomPuppy>() {
                    @Override
                    public void onResponse(Call<RandomPuppy> call, Response<RandomPuppy> response) {
                        Log.d(TAG, "onResponse: " + response.body().getMessage());
                        randomPuppyURL = response.body().getMessage();
                        Picasso.with(getApplicationContext())
                                .load(randomPuppyURL)
                                .into(imageView);
                    }

                    @Override
                    public void onFailure(Call<RandomPuppy> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        if(savedInstanceState != null) {
            String savedPuppy = savedInstanceState.getString("randomPuppyUrl");
            randomPuppyURL = savedPuppy;
            Picasso.with(getApplicationContext())
                    .load(savedPuppy)
                    .into(imageView);
        } else {
            button.callOnClick();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("randomPuppyUrl", randomPuppyURL);
    }
}
