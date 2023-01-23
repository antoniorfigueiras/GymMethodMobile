package pt.ipleiria.estg.dei.gymmethodmobile.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Consulta;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;

public class ListaConsultasAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Consulta> consultas;

    public ListaConsultasAdaptador(Context context, ArrayList<Consulta> consultas) {
        this.context = context;
        this.consultas = consultas;
    }

    @Override
    public int getCount() {
        return consultas.size();
    }

    @Override
    public Object getItem(int i) {
        return consultas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return consultas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.item_lista_consultas, null);

        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(consultas.get(i));
        return view;
    }

    private class ViewHolderLista {

        private TextView tvNome, tvNutricionista, tvData;

        public ViewHolderLista(View view) {
            tvNome = view.findViewById(R.id.tvNome);
            tvNutricionista = view.findViewById(R.id.tvNutricionista);
            tvData = view.findViewById(R.id.tvData);
        }

        public void update(Consulta consulta) {
            tvNome.setText(consulta.getNome());
            tvNutricionista.setText(consulta.getNutricionista());
            tvData.setText(consulta.getData());


        }
    }
}
