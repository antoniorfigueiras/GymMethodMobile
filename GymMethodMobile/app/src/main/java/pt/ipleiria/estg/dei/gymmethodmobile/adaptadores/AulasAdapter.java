package pt.ipleiria.estg.dei.gymmethodmobile.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;


public class AulasAdapter extends ArrayAdapter<Aula> {


    public AulasAdapter(@NonNull Context context, ArrayList<Aula> aulas)
    {
        super(context, 0, aulas);
    }

    @Override
    public long getItemId(int position) {
        Aula aula = getItem(position);
        return aula.getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Aula aula = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_aula, parent, false);

        TextView tvNomeAula = convertView.findViewById(R.id.tvNomeAula);
        TextView tvData = convertView.findViewById(R.id.tvData);
        TextView tvHoraInicio = convertView.findViewById(R.id.tvHoraInicio);
        TextView tvHoraFim = convertView.findViewById(R.id.tvHoraFim);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        tvNomeAula.setText(aula.getNome());
        tvData.setText(aula.getData().toString());
        tvHoraInicio.setText(formatter.format(aula.getInicio()));
        tvHoraFim.setText(formatter.format(aula.getFim()));
        tvStatus.setText(aula.getStatus());
        return convertView;
    }

}
