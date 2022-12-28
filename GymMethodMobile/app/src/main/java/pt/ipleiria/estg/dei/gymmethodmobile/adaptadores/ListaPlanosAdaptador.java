package pt.ipleiria.estg.dei.gymmethodmobile.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;

public class ListaPlanosAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Plano> planos;

    public ListaPlanosAdaptador(Context context, ArrayList<Plano> planos) {
        this.context = context;
        this.planos = planos;
    }

    @Override
    public int getCount() {
        return planos.size();
    }

    @Override
    public Object getItem(int i) {
        return planos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return planos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.item_lista_planos, null);

        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(planos.get(i));
        return view;
    }

    private class ViewHolderLista{

        private TextView tvNome, tvTreinador;

        public ViewHolderLista(View view){
            tvNome = view.findViewById(R.id.tvNome);
            tvTreinador = view.findViewById(R.id.tvTreinador);

        }

        public void update(Plano plano){
            tvNome.setText(plano.getNome());
            tvTreinador.setText(plano.getTreinador());


        }
    }
}
