package pt.ipleiria.estg.dei.gymmethodmobile.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;

public interface ExerciciosPlanoListener {
    void onRefreshListaExercicios(ArrayList<Exercicio> listaExercicios);
}
