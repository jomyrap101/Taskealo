package com.example.taskealo_definitivo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
                    showColorOptions();
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
            // Código para agregar una nueva nota
            String titulo = data.getStringExtra("titulo");
            String textoNota = data.getStringExtra("textoNota");

            final Nota nota = new Nota(titulo, textoNota);

            TextView newNoteTitle = new TextView(this);
            newNoteTitle.setText(titulo);
            newNoteTitle.setPadding(10, 10, 10, 10);
            newNoteTitle.setTextSize(18);
            newNoteTitle.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

            newNoteTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedNota = nota;
                    selectedNotaIndex = notesContainer.indexOfChild(newNoteTitle);
                    Intent intent = new Intent(MainActivity.this, DetalleNota.class);
                    intent.putExtra("titulo", nota.getTitulo());
                    intent.putExtra("contenido", nota.getCuerpo());
                    startActivityForResult(intent, 2);
                }
            });

            notesContainer.addView(newNoteTitle);

        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            if (data.getBooleanExtra("eliminar", false)) {
                // Si eliminar es verdadero, borra la nota
                eliminarNota(selectedNota);
                selectedNota = null;
                selectedNotaIndex = -1;
            } else {
                // Código para actualizar una nota existente
                String nuevoTitulo = data.getStringExtra("titulo");
                String nuevoTextoNota = data.getStringExtra("textoNota");

                if (selectedNota != null) {
                    selectedNota.setTitulo(nuevoTitulo);
                    selectedNota.setCuerpo(nuevoTextoNota);
                    updateNoteView(nuevoTitulo);
                }
            }
        }
    }

    // Mostrar el diálogo con las opciones de color
    private void showColorOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Opciones de Color");
        builder.setSingleChoiceItems(new String[]{"Normal", "Rojo", "Verde"}, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        setColor(selectedNotaIndex, android.R.color.holo_blue_light); // Normal
                        break;
                    case 1:
                        setColor(selectedNotaIndex, android.R.color.holo_red_light); // Rojo
                        break;
                    case 2:
                        setColor(selectedNotaIndex, android.R.color.holo_green_light); // Verde
                        break;
                }
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void setColor(int index, int colorResource) {
        if (index >= 0 && index < notesContainer.getChildCount()) {
            TextView noteView = (TextView) notesContainer.getChildAt(index);
            noteView.setBackgroundColor(getResources().getColor(colorResource));
        }
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