package com.ancafra.ifoodclone.activity.fragment.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ancafra.ifoodclone.R;
import com.ancafra.ifoodclone.activity.autentication.CriarContaActivity;
import com.ancafra.ifoodclone.activity.autentication.LoginActivity;
import com.ancafra.ifoodclone.activity.helper.FirebaseHelper;
import com.ancafra.ifoodclone.activity.usuario.UsuarioAjudaActivity;
import com.ancafra.ifoodclone.activity.usuario.UsuarioCuponsActivity;
import com.ancafra.ifoodclone.activity.usuario.UsuarioEnderecosActivity;
import com.ancafra.ifoodclone.activity.usuario.UsuarioFavoritosActivity;
import com.ancafra.ifoodclone.activity.usuario.UsuarioPerfilActivity;


public class UsuarioPerfilFragment extends Fragment {

    private Button btn_entrar;
    private Button btn_cadastrar;
    private ConstraintLayout l_logado;
    private ConstraintLayout l_deslogado;
    private TextView text_usuario;
    private LinearLayout menu_perfil;
    private LinearLayout menu_favoritos;
    private LinearLayout menu_enderecos;
    private LinearLayout menu_cupons;
    private LinearLayout menu_ajuda;
    private LinearLayout menu_deslogar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuario_perfil, container, false);

        iniciaComponentes(view);
        configCliques();

        return (view);
    }

    @Override
    public void onStart() {
        super.onStart();
        verificaAcesso();
    }

    private void configCliques() {
        btn_entrar.setOnClickListener(v -> startActivity(new Intent(requireActivity(), LoginActivity.class)));
        btn_cadastrar.setOnClickListener(v -> startActivity(new Intent(requireActivity(), CriarContaActivity.class)));
        menu_deslogar.setOnClickListener(v -> deslogar());
        menu_perfil.setOnClickListener(v -> startActivity(new Intent(requireActivity(), UsuarioPerfilActivity.class)));
        menu_favoritos.setOnClickListener(v -> startActivity(new Intent(requireActivity(), UsuarioFavoritosActivity.class)));
        menu_enderecos.setOnClickListener(v -> startActivity(new Intent(requireActivity(), UsuarioEnderecosActivity.class)));
        menu_cupons.setOnClickListener(v -> startActivity(new Intent(requireActivity(), UsuarioCuponsActivity.class)));
        menu_ajuda.setOnClickListener(v -> startActivity(new Intent(requireActivity(), UsuarioAjudaActivity.class)));
    }
    private void deslogar() {
        FirebaseHelper.getAuth().signOut();
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.menu_home);
    }

    private void verificaAcesso() {
        if(FirebaseHelper.getAutenticado()){
            l_deslogado.setVisibility(View.GONE);
            l_logado.setVisibility(View.VISIBLE);
            menu_deslogar.setVisibility(View.VISIBLE);
            text_usuario.setText(FirebaseHelper.getAuth().getCurrentUser().getDisplayName());
        }else{
            l_deslogado.setVisibility(View.VISIBLE);
            l_logado.setVisibility(View.GONE);
            menu_deslogar.setVisibility(View.GONE);
        }
    }

    private void iniciaComponentes(View view) {
        text_usuario = view.findViewById(R.id.text_usuario);
        l_logado = view.findViewById(R.id.l_logado);
        l_deslogado = view.findViewById(R.id.l_deslogado);
        menu_perfil = view.findViewById(R.id.menu_perfil);
        menu_favoritos = view.findViewById(R.id.menu_favoritos);
        menu_enderecos = view.findViewById(R.id.menu_enderecos);
        menu_cupons = view.findViewById(R.id.menu_cupons);
        menu_ajuda = view.findViewById(R.id.menu_ajuda);
        menu_deslogar = view.findViewById(R.id.menu_deslogar);
        btn_entrar = view.findViewById(R.id.btn_entrar);
        btn_cadastrar = view.findViewById(R.id.btn_cadastrar);
    }
}