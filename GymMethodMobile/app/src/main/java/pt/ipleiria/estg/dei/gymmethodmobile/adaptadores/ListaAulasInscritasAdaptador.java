package pt.ipleiria.estg.dei.gymmethodmobile.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.AulaInscrita;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;

public class ListaAulasInscritasAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<AulaInscrita> aulasInscritas;

    public ListaAulasInscritasAdaptador(Context context, ArrayList<AulaInscrita> aulasInscritas) {
        this.context = context;
        this.aulasInscritas = aulasInscritas;
    }

    @Override
    public int getCount() {
        return aulasInscritas.size();
    }

    @Override
    public Object getItem(int i) {
        return aulasInscritas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return aulasInscritas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.item_lista_aula, null);

        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(aulasInscritas.get(i));
        return view;
    }

    private  class ViewHolderLista{

        private TextView tvNomeAula, tvData,tvHoraInicio , tvHoraFim, tvStatus;

        public ViewHolderLista(View view){
             tvNomeAula = view.findViewById(R.id.tvNomeAula);
             tvData = view.findViewById(R.id.tvData);
             tvHoraInicio = view.findViewById(R.id.tvHoraInicio);
             tvHoraFim = view.findViewById(R.id.tvHoraFim);
             tvStatus = view.findViewById(R.id.tvStatus);


        }

        public void update(AulaInscrita aulaInscrita){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            tvNomeAula.setText(aulaInscrita.getNome());
            tvData.setText(aulaInscrita.getData().toString());
            tvHoraInicio.setText(formatter.format(aulaInscrita.getInicio()));
            tvHoraFim.setText(formatter.format(aulaInscrita.getFim()));
            tvStatus.setText(aulaInscrita.getStatus());

        }
    }
}
