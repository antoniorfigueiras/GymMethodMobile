package pt.ipleiria.estg.dei.gymmethodmobile.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.AulaInscrita;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Consulta;

public interface InscricoesListener {
    void onGetInscricoes(ArrayList<AulaInscrita> aulasInscritas);
    void onInscrever(int id);
    void onRemoverInscricao(int action, Boolean success);
}
