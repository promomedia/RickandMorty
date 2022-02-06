package ve.com.promomedia.www.rickandmorty.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ve.com.promomedia.www.rickandmorty.models.PersonajeRespuesta;

public interface ApiService {

    @GET("character")
    Call<PersonajeRespuesta> ObtenerListaPersonajes(@Query("page") int page);
}