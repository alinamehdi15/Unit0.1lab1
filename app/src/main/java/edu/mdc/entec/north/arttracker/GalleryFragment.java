package edu.mdc.entec.north.arttracker;


import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment
        implements ArtPieceListFragment.OnArtPieceSelectedListener
        , GetNameDialogFragment.OnGetNameListener{
    private static final String TAG = "GalleryFragment";
    private boolean isLandscape;
    private List<ArtPiece> artPieces;
    private boolean showList;
    private int position;
    private SharedPreferences sharedPref;
    private static final String FILENAME = "secret";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        showList = true;
        super.onCreate(savedInstanceState);//other nested frags are attached and created
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        //Create and populate data list
        artPieces = new ArrayList<>();
        populateListOfArtPieces();

        if(view.findViewById(R.id.container2) != null){
            isLandscape = true;
            position = 0;
        } else {
            isLandscape = false;
        }

        if(savedInstanceState != null) {
            position = savedInstanceState.getInt("artPiece");
            showList = savedInstanceState.getBoolean("showList");
        }

        if(isLandscape) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ArtPieceListFragment listFragment = ArtPieceListFragment.newInstance(artPieces);
            ft.replace(R.id.container, listFragment, "listFragment");
            ft.addToBackStack("listFragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            ArtPieceFragment artPieceFragment = ArtPieceFragment.newInstance(artPieces.get(position));
            ft.replace(R.id.container2, artPieceFragment, "artPieceFragment");
            ft.addToBackStack("artPieceFragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        } else {
            if (showList) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                ArtPieceListFragment listFragment = ArtPieceListFragment.newInstance(artPieces);
                transaction.replace(R.id.container, listFragment, "listFragment");
                transaction.addToBackStack("listFragment");
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            } else {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                ArtPieceFragment artPieceFragment = ArtPieceFragment.newInstance(artPieces.get(position));
                transaction.replace(R.id.container, artPieceFragment, "artPieceFragment");
                transaction.addToBackStack("artPieceFragment");
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        }

        sharedPref = getContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        //Reading the username from the shared preferences file
        String  savedUserName = sharedPref.getString("userName", null);
        if(savedUserName == null){
            DialogFragment customFragment = GetNameDialogFragment.newInstance();
            customFragment.show(getChildFragmentManager(), "custom");
        } else {
            sayHello(savedUserName);
        }







        return view;

    }

    private void sayHello(String userName) {
        FileInputStream fin = null;
        StringBuilder temp = new StringBuilder();
        try {
            fin = getContext().openFileInput(FILENAME);

            int c;
            while( (c = fin.read()) != -1){
                temp.append(Character.toString((char)c));
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(getContext(), "Hello " + userName + "! Your password is " + temp.toString(), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 10, 10);
        toast.show();
    }

    private void populateListOfArtPieces() {
        ArtPiece ap1 = new ArtPiece("Mona Lisa", "Leonardo da Vinci", 1503, R.drawable.monalisa);
        artPieces.add(ap1);
        ArtPiece ap2 = new ArtPiece("Guernica", "Pablo Picasso", 1937,R.drawable.guernica);
        artPieces.add(ap2);
        ArtPiece ap3 = new ArtPiece("The Creation of Adam", "Michelangelo", 1508, R.drawable.thecreationofadam);
        artPieces.add(ap3);
        ArtPiece ap4 = new ArtPiece("The Birth of Venice", "Sandro Botticelli", 1486, R.drawable.thebirthofvenice);
        artPieces.add(ap4);
        ArtPiece ap5 = new ArtPiece("Girl with a Pearl Earring", "Johannes Vermeer", 1665,R.drawable.girlwithapearlearring);
        artPieces.add(ap5);
        ArtPiece ap6 = new ArtPiece("Campbell's Soup Cans", "Andy Warhol", 1962, R.drawable.campbellssoupcans);
        artPieces.add(ap6);
        ArtPiece ap7 = new ArtPiece("The Thinker", "Auguste Rodin", 1902, R.drawable.thethinker);
        artPieces.add(ap7);
        ArtPiece ap8 = new ArtPiece("No 1", "Jackson Pollock", 1950, R.drawable.no1);
        artPieces.add(ap8);
        ArtPiece ap9 = new ArtPiece("Starry Night", "Vincent Van Gogh", 1889, R.drawable.starrynight);
        artPieces.add(ap9);
        ArtPiece ap10 = new ArtPiece("American Gothic", "Grant Wood", 1930, R.drawable.americangothic);
        artPieces.add(ap10);
        ArtPiece ap11 = new ArtPiece("Water Lily Pond", "Claude Monet", 1899, R.drawable.waterlilypond);
        artPieces.add(ap11);
        ArtPiece ap12 = new ArtPiece("The Scream", "Edvard Munch", 1893, R.drawable.thescream);
        artPieces.add(ap12);
        ArtPiece ap13 = new ArtPiece("The Persistence of Memory", "Salvador Dali", 1931, R.drawable.thepersistenceofmemory);
        artPieces.add(ap13);
        ArtPiece ap14 = new ArtPiece("Dance at Le Moulin de la Galette", "Edward Renoir", 1876,R.drawable.danceatlemoulindelagalette);
        artPieces.add(ap14);
    }


    @Override
    public void onArtPieceSelected(int pos) {
        showList = false;
        position = pos;

        if(isLandscape){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            ArtPieceFragment artPieceFragment = ArtPieceFragment.newInstance(artPieces.get(position));
            transaction.replace(R.id.container2, artPieceFragment, "artPieceFragment");
            transaction.addToBackStack("artPieceFragment");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        } else{
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            ArtPieceFragment artPieceFragment = ArtPieceFragment.newInstance(artPieces.get(position));
            transaction.replace(R.id.container, artPieceFragment, "artPieceFragment");
            transaction.addToBackStack("artPieceFragment");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }

    }

    @Override
    public void onArtPieceLongSelected(int position) {
        DialogFragment newFragment = ConfirmDeleteDialogFragment.newInstance(position);
        newFragment.show(getChildFragmentManager(), "confirmDeleteArtPiece");


    }

    /**
     * @return true = if this fragment can handle the backPress
     */
    public boolean onBackPressed() {
        if(!showList && !isLandscape) {
            getChildFragmentManager().popBackStackImmediate("artPieceFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            showList = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("artPiece", position);
        outState.putBoolean("showList", showList);
    }

    @Override
    public void onGetName(String name) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userName", name);
        editor.commit();


        String fileContents = "password";
        FileOutputStream outputStream;
        try {
            outputStream = getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.w("WARNING", "Cannot save password");
        }

        sayHello(name);
    }
}