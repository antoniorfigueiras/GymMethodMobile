package pt.ipleiria.estg.dei.gymmethodmobile.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Agua;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;


public class AguaAdapter extends ArrayAdapter<Agua> {


    public AguaAdapter(@NonNull Context context, ArrayList<Agua> aguas)
    {
        super(context, 0, aguas);
    }

    @Override
    public long getItemId(int position) {
        Agua agua = getItem(position);
        return agua.getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Agua agua = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_agua, parent, false);

        TextView tvDescricao = convertView.findViewById(R.id.tvDescricao);
        TextView tvValor = convertView.findViewById(R.id.tvValor);
        TextView tvData = convertView.findViewById(R.id.tvData);
        TextView tvHora = convertView.findViewById(R.id.tvHora);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        tvDescricao.setText(agua.getDescricao());
        tvValor.setText(String.format(agua.getValor().toString(), 1) + "L");
        tvData.setText(agua.getData().toString());
        tvHora.setText(formatter.format(agua.getHora()));
        return convertView;
    }

}
