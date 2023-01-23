package pt.ipleiria.estg.dei.gymmethodmobile.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.AulaInscrita;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Consulta;

public interface AulasInscritasListener {
    void onRefreshListaAulasInscritas(ArrayList<AulaInscrita> aulasInscritas);
}
