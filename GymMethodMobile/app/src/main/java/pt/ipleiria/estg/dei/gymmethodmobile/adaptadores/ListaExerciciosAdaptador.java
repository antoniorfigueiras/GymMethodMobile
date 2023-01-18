package pt.ipleiria.estg.dei.gymmethodmobile.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;

public class ListaExerciciosAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Exercicio> exercicios;

    public ListaExerciciosAdaptador(Context context, ArrayList<Exercicio> exercicios) {
        this.context = context;
        this.exercicios = exercicios;
    }

    @Override
    public int getCount() {
        return exercicios.size();
    }

    @Override
    public Object getItem(int i) {
        return exercicios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return exercicios.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.item_lista_exercicios_plano, null);

        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(exercicios.get(i));
        return view;
    }

    private class ViewHolderLista{

        private TextView tvNome, tvEquipamento, tvTipo;

        public ViewHolderLista(View view){
            tvNome = view.findViewById(R.id.tvNome);
            tvEquipamento = view.findViewById(R.id.tvEquipamento);
            tvTipo = view.findViewById(R.id.tvTipo);
        }

        public void update(Exercicio exercicio){
            tvNome.setText(exercicio.getNome());
            tvEquipamento.setText(exercicio.getEquipamento());
            tvTipo.setText(exercicio.getTipo_exercicio());
        }
    }
}
