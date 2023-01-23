package pt.ipleiria.estg.dei.gymmethodmobile.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Consulta;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;

public interface ConsultasListener {
    void onRefreshListaConsultas(ArrayList<Consulta> listaConsultas);
}
