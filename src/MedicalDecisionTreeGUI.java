/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sebastián G.
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MedicalDecisionTreeGUI {
    private Nodo root;
    private String lang;
    private JFrame diagnosticFrame;
    private Map<String, String> respuestas;
    private Nodo currentNode;
    
    public MedicalDecisionTreeGUI(String language) {
        this.lang = language;
        this.respuestas = new HashMap<>();
        this.root = construirArbolCompleto();
        this.currentNode = root;
    }
    
    public void iniciarDiagnostico() {
        diagnosticFrame = new JFrame(lang.equals("ES_es") ? "Diagnóstico Médico" : "Medical Diagnosis");
        diagnosticFrame.setSize(500, 400);
        diagnosticFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        diagnosticFrame.setLayout(new BorderLayout());
        
        mostrarPreguntaActual();
    }
    
    private void mostrarPreguntaActual() {
        diagnosticFrame.getContentPane().removeAll();
        
        if (currentNode == null) {
            mostrarResultado();
            return;
        }
        
        if (currentNode.getEtiqueta() != null) {
            mostrarResultado();
            return;
        }
        
        String pregunta = obtenerPregunta(currentNode.getAtributo());
        JLabel preguntaLabel = new JLabel("<html><div style='text-align: center;'>" + pregunta + "</div></html>");
        preguntaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        preguntaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        JButton btnSi = new JButton(lang.equals("ES_es") ? "Sí" : "Yes");
        JButton btnNo = new JButton(lang.equals("ES_es") ? "No" : "No");
        
        btnSi.addActionListener(e -> {
            respuestas.put(currentNode.getAtributo(), "1");
            avanzarAlSiguienteNodo("1");
        });
        
        btnNo.addActionListener(e -> {
            respuestas.put(currentNode.getAtributo(), "2");
            avanzarAlSiguienteNodo("2");
        });
        
        buttonPanel.add(btnSi);
        buttonPanel.add(btnNo);
        
        diagnosticFrame.add(preguntaLabel, BorderLayout.CENTER);
        diagnosticFrame.add(buttonPanel, BorderLayout.SOUTH);
        
        diagnosticFrame.revalidate();
        diagnosticFrame.repaint();
        diagnosticFrame.setVisible(true);
    }
    
    private void avanzarAlSiguienteNodo(String respuesta) {
        Nodo siguienteNodo = currentNode.getHijo(respuesta);
        if (siguienteNodo == null) {
            mostrarResultado();
        } else {
            currentNode = siguienteNodo;
            mostrarPreguntaActual();
        }
    }
    
    private void mostrarResultado() {
        diagnosticFrame.getContentPane().removeAll();
        
        String diagnostico = root.classify(respuestas);
        JLabel resultadoLabel = new JLabel("<html><div style='text-align: center;'><h2>" + 
                (lang.equals("ES_es") ? "Resultado del Diagnóstico" : "Diagnosis Result") + 
                "</h2><br><p>" + diagnostico + "</p></div></html>");
        resultadoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        resultadoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton btnCerrar = new JButton(lang.equals("ES_es") ? "Cerrar" : "Close");
        btnCerrar.addActionListener(e -> diagnosticFrame.dispose());
        
        diagnosticFrame.add(resultadoLabel, BorderLayout.CENTER);
        diagnosticFrame.add(btnCerrar, BorderLayout.SOUTH);
        
        diagnosticFrame.revalidate();
        diagnosticFrame.repaint();
    }
    
    private String obtenerPregunta(String atributo) {
        if (lang.equals("ES_es")) {
            switch (atributo) {
                case "fiebre_alta": return "¿Tiene fiebre alta (más de 38°C)?";
                case "fiebre_prolongada": return "¿La fiebre ha durado más de 3 días?";
                case "tos_persistente": return "¿Tiene tos persistente?";
                case "tos_seca": return "¿La tos es seca (sin flemas)?";
                case "dolor_garganta": return "¿Tiene dolor de garganta intenso?";
                case "dificultad_respirar": return "¿Tiene dificultad para respirar?";
                case "fatiga_extrema": return "¿Tiene fatiga extrema?";
                case "perdida_olfato_gusto": return "¿Ha perdido el gusto o el olfato?";
                case "dolores_musculares": return "¿Tiene dolores musculares generalizados?";
                case "dolor_cabeza": return "¿Tiene dolor de cabeza intenso?";
                case "contacto_covid": return "¿Ha tenido contacto con alguien con COVID-19?";
                case "congestion_nasal": return "¿Tiene congestión nasal?";
                case "estornudos": return "¿Tiene estornudos frecuentes?";
                case "ojos_irritados": return "¿Tiene ojos llorosos o irritados?";
                default: return "Pregunta no definida";
            }
        } else {
            switch (atributo) {
                case "fiebre_alta": return "Do you have high fever (over 38°C)?";
                case "fiebre_prolongada": return "Has the fever lasted more than 3 days?";
                case "tos_persistente": return "Do you have persistent cough?";
                case "tos_seca": return "Is the cough dry (no phlegm)?";
                case "dolor_garganta": return "Do you have intense sore throat?";
                case "dificultad_respirar": return "Do you have difficulty breathing?";
                case "fatiga_extrema": return "Do you have extreme fatigue?";
                case "perdida_olfato_gusto": return "Have you lost taste or smell?";
                case "dolores_musculares": return "Do you have generalized muscle pain?";
                case "dolor_cabeza": return "Do you have intense headache?";
                case "contacto_covid": return "Have you been in contact with someone with COVID-19?";
                case "congestion_nasal": return "Do you have nasal congestion?";
                case "estornudos": return "Do you have frequent sneezing?";
                case "ojos_irritados": return "Do you have watery or irritated eyes?";
                default: return "Question not defined";
            }
        }
    }

    private Nodo construirArbolCompleto() {
        // Nodo raíz: fiebre alta
        Nodo root = new Nodo("fiebre_alta");

        // Crear nodos de diagnóstico
        Nodo diagnosticoCovid = new Nodo(null);
        Nodo diagnosticoGripe = new Nodo(null);
        Nodo diagnosticoResfriado = new Nodo(null);
        Nodo diagnosticoAlergia = new Nodo(null);
        Nodo diagnosticoFaringitis = new Nodo(null);
        Nodo diagnosticoSano = new Nodo(null); // Nuevo nodo para casos sin enfermedad

        // Configurar etiquetas según idioma
        if (lang.equals("ES_es")) {
            diagnosticoCovid.setEtiqueta("COVID-19 - Consulte urgentemente con un médico");
            diagnosticoGripe.setEtiqueta("Gripe - Descanse y tome líquidos. Consulte un médico si empeora");
            diagnosticoResfriado.setEtiqueta("Resfriado común - Descanse y tome líquidos");
            diagnosticoAlergia.setEtiqueta("Alergia estacional - Considere antihistamínicos");
            diagnosticoFaringitis.setEtiqueta("Faringitis bacteriana - Puede necesitar antibióticos");
            diagnosticoSano.setEtiqueta("No se detectaron condiciones graves - Monitorice sus síntomas");
        } else {
            diagnosticoCovid.setEtiqueta("COVID-19 - Urgently consult a doctor");
            diagnosticoGripe.setEtiqueta("Flu - Rest and drink fluids. Consult if it worsens");
            diagnosticoResfriado.setEtiqueta("Common cold - Rest and drink fluids");
            diagnosticoAlergia.setEtiqueta("Seasonal allergy - Consider antihistamines");
            diagnosticoFaringitis.setEtiqueta("Bacterial pharyngitis - You may need antibiotics");
            diagnosticoSano.setEtiqueta("No serious conditions detected - Monitor your symptoms");
        }

        // Construcción del árbol corregida
        Nodo fiebreProlongada = new Nodo("fiebre_prolongada");
        Nodo contactoCovid = new Nodo("contacto_covid");
        Nodo perdidaOlfatoGusto = new Nodo("perdida_olfato_gusto");
        Nodo dificultadRespirar = new Nodo("dificultad_respirar");
        Nodo dolorGarganta = new Nodo("dolor_garganta");
        Nodo doloresMusculares = new Nodo("dolores_musculares");
        Nodo dolorCabeza = new Nodo("dolor_cabeza");
        Nodo congestionNasal = new Nodo("congestion_nasal");
        Nodo estornudos = new Nodo("estornudos");
        Nodo ojosIrritados = new Nodo("ojos_irritados");
        Nodo fatigaExtrema = new Nodo("fatiga_extrema");

        // Estructura principal
        root.agregar("1", fiebreProlongada);
        root.agregar("2", congestionNasal);

        // Rama con fiebre alta
        fiebreProlongada.agregar("1", contactoCovid);
        fiebreProlongada.agregar("2", dolorGarganta);

        // Rama COVID-19
        contactoCovid.agregar("1", perdidaOlfatoGusto);
        contactoCovid.agregar("2", dificultadRespirar);

        perdidaOlfatoGusto.agregar("1", diagnosticoCovid);
        perdidaOlfatoGusto.agregar("2", dificultadRespirar);

        dificultadRespirar.agregar("1", diagnosticoCovid);
        dificultadRespirar.agregar("2", fatigaExtrema);

        fatigaExtrema.agregar("1", diagnosticoGripe);
        fatigaExtrema.agregar("2", diagnosticoSano);

        // Rama de dolor de garganta (faringitis/gripe)
        dolorGarganta.agregar("1", doloresMusculares);
        dolorGarganta.agregar("2", diagnosticoFaringitis);

        doloresMusculares.agregar("1", diagnosticoGripe);
        doloresMusculares.agregar("2", dolorCabeza);

        dolorCabeza.agregar("1", diagnosticoGripe);
        dolorCabeza.agregar("2", diagnosticoSano);

        // Rama sin fiebre alta
        congestionNasal.agregar("1", ojosIrritados);
        congestionNasal.agregar("2", diagnosticoSano);

        ojosIrritados.agregar("1", diagnosticoAlergia);
        ojosIrritados.agregar("2", estornudos);

        estornudos.agregar("1", diagnosticoResfriado);
        estornudos.agregar("2", diagnosticoSano);

        return root;
    }
}
