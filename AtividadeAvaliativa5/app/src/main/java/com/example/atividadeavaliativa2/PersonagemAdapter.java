package com.example.atividadeavaliativa2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PersonagemAdapter extends BaseAdapter {
    private Context context;
    private List<Personagem> personagens;
    private LayoutInflater inflater;

    public PersonagemAdapter(Context context, List<Personagem> personagens) {
        this.context = context;
        this.personagens = personagens;
        this.inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.list_item_personagem, parent, false);
        }

        TextView txtNome = convertView.findViewById(R.id.txtNome);
        ImageButton btnEditar = convertView.findViewById(R.id.btnEditar);
        ImageButton btnExcluir = convertView.findViewById(R.id.btnExcluir);

        txtNome.setText(personagens.get(position).getNome());

        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.gradient_red);
        } else {
            convertView.setBackgroundResource(R.drawable.gradient_blue);
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, Cadastro.class);
            intent.putExtra("personagem", personagens.get(position));
            context.startActivity(intent);
        });

        btnExcluir.setOnClickListener(v -> {
            BancoDados bancoDeDados = new BancoDados(context);
            bancoDeDados.excluirPersonagem(personagens.get(position).getId());
            personagens.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
