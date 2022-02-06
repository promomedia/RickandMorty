package ve.com.promomedia.www.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class Biografia extends AppCompatActivity {

    public MediaPlayer mediaPlayer;

    public void buttomBack (View v) {
        Button button = findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
        mediaPlayer = MediaPlayer.create(this, R.raw.touch);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biografia);
        Intent intent = getIntent();
        TextView idBio = findViewById(R.id.idTextView);
        ImageView foto = findViewById(R.id.fotoImageView);
        TextView nombreBio = findViewById(R.id.nombreTextView);
        TextView statusBio = findViewById(R.id.statusTextView);
        TextView speciesBio = findViewById(R.id.speciesTextView);
        TextView typeBio = findViewById(R.id.typeTextView);
        TextView genderBio = findViewById(R.id.genderTextView);
        TextView createdBio = findViewById(R.id.createdTextView);
        Glide.with(this)
                .load("https://rickandmortyapi.com/api/character/avatar/" + intent.getStringExtra("id") + ".jpeg")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(foto);
        idBio.setText(intent.getStringExtra("id"));
        nombreBio.setText(intent.getStringExtra("nombre"));
        statusBio.setText("\n" + getResources().getString(R.string.status) + "\n" + intent.getStringExtra("status"));
        speciesBio.setText("\n" + getResources().getString(R.string.species) + "\n" + intent.getStringExtra("species"));
        if (intent.getStringExtra("type").equals("")) {
            typeBio.setText("\n" + getResources().getString(R.string.type) + "\n" + getResources().getString(R.string.available));
        } else{
            typeBio.setText("\n" + getResources().getString(R.string.type) + "\n" + intent.getStringExtra("type"));
        }
        genderBio.setText("\n" + getResources().getString(R.string.gender) + "\n" + intent.getStringExtra("gender"));
        createdBio.setText("\n" + getResources().getString(R.string.created) + "\n" + intent.getStringExtra("created"));
    }

    @Override
    public void onBackPressed() {
        mediaPlayer = MediaPlayer.create(this, R.raw.touch);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        super.onBackPressed();
    }
}