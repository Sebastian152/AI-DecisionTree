/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sebastián G.
 */
import java.util.HashMap;
import java.util.Map;

public class Nodo {
    private String atributo;
    private Map<String, Nodo> map;
    private String etiqueta;

    public Nodo(String atributo) {
        this.atributo = atributo;
        this.map = new HashMap<>();
        this.etiqueta = null;
    }

    public void agregar(String valorAtributo, Nodo nodo) {
        map.put(valorAtributo, nodo);
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getAtributo() {
        return atributo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public Nodo getHijo(String valor) {
        return map.get(valor);
    }

    public String classify(Map<String, String> instancia) {
        if (etiqueta != null) {
            return etiqueta;
        }
        String valor = instancia.get(atributo);
        Nodo nodoHijo = map.get(valor);
        if (nodoHijo == null) {
            return (atributo.startsWith("ES") 
                    ? "No se pudo determinar el diagnóstico" 
                    : "Could not determine diagnosis");
        }
        return nodoHijo.classify(instancia);
    }
}