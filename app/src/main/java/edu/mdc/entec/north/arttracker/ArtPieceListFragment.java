package edu.mdc.entec.north.arttracker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ArtPieceListFragment extends Fragment
        implements ConfirmDeleteDialogFragment.OnDeleteConfirmedListener
        {
    private static final String ART_PIECES = "artPieces";
    private static final String TAG = "ArtPieceListFragment";
    private List<ArtPiece> artPieces;
    private ArtPieceAdapter adapter;
    private RecyclerView recyclerView;
    private OnArtPieceSelectedListener mListener;
    private Context context;

    public ArtPieceListFragment() {
        // Required empty public constructor
    }


    public static ArtPieceListFragment newInstance(List<ArtPiece> artPieces) {
        ArtPieceListFragment fragment = new ArtPieceListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ART_PIECES, (ArrayList<? extends Parcelable>) artPieces);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            artPieces = getArguments().getParcelableArrayList(ART_PIECES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_art_piece_list, container, false);


        //Lookup the recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_art_pieces);
        // Create adapter passing in the data
        adapter = new ArtPieceAdapter(artPieces, mListener);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Fragment galleryFragment = this.getParentFragment();
        if (galleryFragment instanceof OnArtPieceSelectedListener) {
            mListener = (OnArtPieceSelectedListener) galleryFragment;
        } else {
            Log.d(TAG, "The listener must be implemented in GalleryFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public interface OnArtPieceSelectedListener {
        void onArtPieceSelected(int position);
        void onArtPieceLongSelected(int position);
    }

    @Override
    public void onDeleteConfirmed(int position) {
        artPieces.remove(position);
        adapter.notifyDataSetChanged();

    }


}