package ve.com.promomedia.www.rickandmorty;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;
import ve.com.promomedia.www.rickandmorty.models.Personaje;
import static androidx.core.content.ContextCompat.startActivity;


public class ListaPersonajeAdapter extends RecyclerView.Adapter<ListaPersonajeAdapter.ViewHolder> {

    private ArrayList<Personaje> dataset;
    private Context context;
    private List<String> status = new ArrayList<>();
    private List<String> species = new ArrayList<>();
    private List<String> type = new ArrayList<>();
    private List<String> gender = new ArrayList<>();
    private List<String> created = new ArrayList<>();
    public MediaPlayer mediaPlayer;

    public ListaPersonajeAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personaje, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Personaje p = dataset.get(position);
        holder.nombreTextView.setText(p.getName());
        if (position == (status.size())) {
            status.add(p.getStatus());
            species.add(p.getSpecies());
            type.add(p.getType());
            gender.add(p.getGender());
            created.add(p.getCreated());
        }
        Glide.with(context)
                .load("https://rickandmortyapi.com/api/character/avatar/" + p.getNumber() + ".jpeg")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
        holder.idTextView.setText(Integer.toString(p.getNumber()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPersonaje(ArrayList<Personaje> listaPersonaje) {
        dataset.addAll(listaPersonaje);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView idTextView;
        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            idTextView = (TextView) itemView.findViewById(R.id.idTextView);
            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);
            fotoImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Biografia.class);
            intent.putExtra("id", idTextView.getText());
            intent.putExtra("nombre", nombreTextView.getText());
            intent.putExtra("status", status.get(Integer.parseInt(idTextView.getText().toString())-1));
            intent.putExtra("species", species.get(Integer.parseInt(idTextView.getText().toString())-1));
            intent.putExtra("type", type.get(Integer.parseInt(idTextView.getText().toString())-1));
            intent.putExtra("gender", gender.get(Integer.parseInt(idTextView.getText().toString())-1));
            intent.putExtra("created", created.get(Integer.parseInt(idTextView.getText().toString())-1));
            mediaPlayer = MediaPlayer.create(context, R.raw.touch);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            startActivity(v.getContext(), intent,null);
        }
    }
}