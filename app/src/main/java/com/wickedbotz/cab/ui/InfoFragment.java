package com.wickedbotz.cab.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.wickedbotz.cab.R;

public class InfoFragment extends DialogFragment {
	Button googleBtn, googlePlayBtn, facebookBtn;
	

	//private static final String linkCatolica = "http://www.catolicasc.org.br/";
	private static final String linkFacebook = "https://www.facebook.com/Cat%C3%B3%B3lica-De-Santa_Catarina-330880706951833/";
	private static final String linkGmail= "https://plus.google.com/u/0/116474376621584890949/posts";
	private static final String linkGooglePlay = "https://www.catolicasc.org.br/";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_info_fragment, container, true);
		getDialog().setTitle(getResources().getString(R.string.sobre_app_botao));
		// Do something else

		facebookBtn =(Button)rootView.findViewById(R.id.facebookBtn);
		googleBtn =(Button)rootView.findViewById(R.id.googleBtn);
		googlePlayBtn =(Button)rootView.findViewById(R.id.googlePlayBtn);
		
		facebookBtn.setOnClickListener(new chamarPagina(linkFacebook,getResources().getString(R.string.grupoCatolicaFace)));
		googleBtn.setOnClickListener(new chamarPagina(linkGmail,getResources().getString(R.string.desenvolvedorApp)));
		googlePlayBtn.setOnClickListener(new chamarPagina(linkGooglePlay,getResources().getString(R.string.paginaSiteDaCatolica)));
			
		
		


		return rootView;
	}
	
	
	
	
	
	public class chamarPagina implements OnClickListener{

		String link;
		String mensagem;
		
		public  chamarPagina(String link, String mensagem) {
			this.link = link;
			this.mensagem = mensagem;
		}
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
			startActivity(Intent.createChooser(intent, getResources().getString(R.string.escolha) + mensagem));
		}
		
	}

	
	 

}
