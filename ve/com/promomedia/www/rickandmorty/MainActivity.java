package ve.com.promomedia.www.rickandmorty;

import android.media.MediaPlayer;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ve.com.promomedia.www.rickandmorty.interfaces.ApiService;
import ve.com.promomedia.www.rickandmorty.models.Personaje;
import ve.com.promomedia.www.rickandmorty.models.PersonajeRespuesta;


public class MainActivity extends AppCompatActivity{

    private boolean retroceso;
    private static final String TAG = "Frank: ";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaPersonajeAdapter listaPersonajeAdapter;
    private int page;
    private Boolean aptoParaCargar;
    public MediaPlayer mediaPlayer;
    public MediaPlayer musicaFondo;


    private void sonidos(int sonido) {
        int maxVolume = 100;
        int soundVolume = 20;
        final float volume = (float) (1 - (Math.log(maxVolume-soundVolume) / Math.log(maxVolume)));
        if (sonido==0){
            musicaFondo = MediaPlayer.create(this, R.raw.intro);
            musicaFondo.setVolume(volume, volume);
            musicaFondo.start();
            musicaFondo.setLooping(true);
        }
        if (sonido==1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.touch);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }

    public void pantPrincipal (View view) {
        sonidos(1);
        Button pantInicio = findViewById(R.id.btnPantInicio);
        pantInicio.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        listaPersonajeAdapter = new ListaPersonajeAdapter(this);
        recyclerView.setAdapter(listaPersonajeAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "Mostré todo y cargo otro lote");
                            aptoParaCargar = false;
                            page += 1;
                            ObtenerDatos(page);
                        }
                    }
                }
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        aptoParaCargar = true;
        page = 1;
        ObtenerDatos(page);
    }

    private void ObtenerDatos(int page) {
        ApiService service = retrofit.create(ApiService.class);
        Call<PersonajeRespuesta> personajeRespuestaCall = service.ObtenerListaPersonajes(page);
        personajeRespuestaCall.enqueue(new Callback<PersonajeRespuesta>() {
            @Override
            public void onResponse(Call<PersonajeRespuesta> call, Response<PersonajeRespuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {
                    PersonajeRespuesta personajeRespuesta = response.body();
                    assert personajeRespuesta != null;
                    ArrayList<Personaje> listaPersonaje = personajeRespuesta.getResults();
                    listaPersonajeAdapter.adicionarListaPersonaje(listaPersonaje);
                } else {
                    Log.e(TAG, "Respuesta: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PersonajeRespuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sonidos(0);
    }

    @Override
    public void onBackPressed() {
        sonidos(1);
        if (!retroceso) {
            Toast.makeText(MainActivity.this, R.string.confirmarSalida, Toast.LENGTH_SHORT).show();
            retroceso = true;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    retroceso = false;
                }
            }, 5000);
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }
}