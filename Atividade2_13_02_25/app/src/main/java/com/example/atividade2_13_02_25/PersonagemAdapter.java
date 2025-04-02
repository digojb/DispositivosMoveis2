package com.example.atividade2_13_02_25;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class PersonagemAdapter extends BaseAdapter {

    private Context context;
    private List<Personagem> personagens;

    public PersonagemAdapter(Context context, List<Personagem> personagens) {
        this.context = context;
        this.personagens = personagens;
    }

    @Override
    public int getCount() {
        return personagens.size();
    }

    @Override
    public Object getItem(int position) {
        return personagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_personagem, parent, false);
        }

        TextView txtNome = convertView.findViewById(R.id.txtNome);
        ImageButton btnEditar = convertView.findViewById(R.id.btnEditar);
        ImageButton btnExcluir = convertView.findViewById(R.id.btnExcluir);

        Personagem personagem = personagens.get(position);
        txtNome.setText(personagem.getNome());

        btnEditar.setOnClickListener(v -> {
        });

        btnExcluir.setOnClickListener(v -> {
        });

        return convertView;
    }
}
