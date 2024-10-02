package com.example.taskealo_definitivo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnCrearNota, btnConfig;
    private LinearLayout notesContainer;
    private Nota selectedNota; // Nota seleccionada para editar o eliminar
    private int selectedNotaIndex = -1; // Para saber qué nota se está editando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCrearNota = findViewById(R.id.btnCrearNota);
        btnConfig = findViewById(R.id.btn_config);  // Usamos el ID correcto
        notesContainer = findViewById(R.id.notes_container);

        btnCrearNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad para crear una nueva nota
                Intent intent = new Intent(MainActivity.this, Crear_Nota.class);
                startActivityForResult(intent, 1);
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el diálogo de configuración solo si hay una nota seleccionada
                if (selectedNota != null) {
                    showConfigOptions();
                } else {
                    // Mostrar un mensaje si no se seleccionó ninguna nota
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Por favor, selecciona una nota primero.");
                    builder.setPositiveButton("Aceptar", null);
                    builder.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Crear nota nueva
            String titulo = data.getStringExtra("titulo");
            String textoNota = data.getStringExtra("textoNota");

            final Nota nota = new Nota(titulo, textoNota);

            // Crear un TextView para mostrar el título de la nota
            TextView newNoteTitle = new TextView(this);
            newNoteTitle.setText(titulo);
            newNoteTitle.setPadding(10, 10, 10, 10);
            newNoteTitle.setTextSize(18);
            newNoteTitle.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

            // Cuando se hace clic en la nota, abrir la actividad de detalle
            newNoteTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedNota = nota;
                    selectedNotaIndex = notesContainer.indexOfChild(newNoteTitle); // Guardamos el índice
                    Intent intent = new Intent(MainActivity.this, DetalleNota.class);
                    intent.putExtra("titulo", nota.getTitulo());
                    intent.putExtra("contenido", nota.getCuerpo());
                    startActivityForResult(intent, 2);
                }
            });

            // Añadir el nuevo TextView (título) al contenedor de notas
            notesContainer.addView(newNoteTitle);

        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            // Actualizar nota existente
            String nuevoTitulo = data.getStringExtra("titulo");
            String nuevoTextoNota = data.getStringExtra("textoNota");

            if (selectedNota != null) {
                selectedNota.setTitulo(nuevoTitulo);
                selectedNota.setCuerpo(nuevoTextoNota);
                // Actualizar el TextView correspondiente
                updateNoteView(nuevoTitulo);
            }
        }
    }

    // Mostrar el diálogo con las opciones de configuración
    private void showConfigOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Opciones de Nota");
        builder.setMessage("Elige una acción");

        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Editar la nota seleccionada
                Intent intent = new Intent(MainActivity.this, DetalleNota.class);
                intent.putExtra("titulo", selectedNota.getTitulo());
                intent.putExtra("textoNota", selectedNota.getCuerpo());
                startActivityForResult(intent, 2);  // Código 2 para editar
            }
        });

        builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Eliminar la nota seleccionada
                eliminarNota(selectedNota);
                selectedNota = null;  // Desseleccionar la nota
            }
        });

        builder.setNeutralButton("Cancelar", null);
        builder.show();
    }

    // Método para eliminar la nota seleccionada
    private void eliminarNota(Nota nota) {
        int childCount = notesContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TextView noteView = (TextView) notesContainer.getChildAt(i);
            if (noteView.getText().toString().equals(nota.getTitulo())) {
                notesContainer.removeView(noteView);  // Eliminar el TextView correspondiente
                break;
            }
        }
    }

    // Método para actualizar el TextView correspondiente al editar la nota
    private void updateNoteView(String nuevoTitulo) {
        TextView noteView = (TextView) notesContainer.getChildAt(selectedNotaIndex);
        if (noteView != null) {
            noteView.setText(nuevoTitulo);  // Actualiza el texto del TextView
        }
    }
}