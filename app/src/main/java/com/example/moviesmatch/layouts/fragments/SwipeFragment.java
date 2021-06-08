package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moviesmatch.R;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IImageRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.layouts.adapters.SwipeAdapter;
import com.example.moviesmatch.models.Movie;
import com.example.moviesmatch.models.MoviesMatchURLS;
import com.example.moviesmatch.models.factory.MovieFactory;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentSwipeBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.requests.ImageRequest;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.validation.JSONManipulator;
import com.example.moviesmatch.validation.Loading;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class SwipeFragment extends Fragment implements IOnBackPressed, IGetActivity {
    private SwipeAdapter arrayAdapter;
    private SwipeFlingAdapterView flingContainer;
    private Button buttonLeft, buttonRight, buttonDismiss, buttonMoreInfo;
    private ImageView imageViewPoster;
    private GetRequest getRequest;
    private PostRequest postRequest;
    private ArrayList<Movie> movies;
    private FragmentSwipeBinding binding;
    private CertificateByPass certificat;
    private String getMovieURL, getMatchURL, token;
    private JSONManipulator jsonManipulator;
    private JSONObject account;
    private String groupId;
    private int index;
    private TextView textViewTitleMovie;
    private GifImageView loadingGif;
    private Loading loading;
    private Dialog match;
    long mLastClickTime = 0;

     private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSwipeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        getRequestListFilm();
        swipeButtons();
    }

    public void swipeButtons() {
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopButtonSpamming();
                flingContainer.getTopCardListener().selectLeft();
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopButtonSpamming();
                flingContainer.getTopCardListener().selectRight();
            }
        });
    }

    private void getRequestListFilm() {
        loading.loadingVisible(loadingGif, buttonLeft, buttonRight);
        account = jsonManipulator.newJSONObject(getArguments().getString("Account"));
        groupId = getArguments().getString("GroupId");

        setUserSwipeURL();

        getRequest.getRequestArray(getMovieURL, token, new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                index = 0;
                movies = new MovieFactory().createArrayMovies(jsonArray);
                setImage();
            }
        });
    }

    private void setUserSwipeURL() {
        getMatchURL = MoviesMatchURLS.getMatchURL;
        getMovieURL = MoviesMatchURLS.getMoviesURL;
        String  usrId = "&userId=" + jsonManipulator.getJSONObjectGetString(account, "userDB","usrId");
        String  grpId = "?groupId="+ groupId;
        token = jsonManipulator.getString(account,"token");

        getMovieURL += grpId + usrId;
    }

    private void postSwipe(Movie o, Boolean rightOrLeft) {
        JSONObject currentMovie = new JSONObject();
        jsonManipulator.put(currentMovie, "idUser", jsonManipulator.getJSONObjectGetString(account, "userDB", "usrId"));
        jsonManipulator.put(currentMovie, "idGroup", groupId);
        jsonManipulator.put(currentMovie, "idMovie", o.getId());
        jsonManipulator.put(currentMovie, "isLiked", rightOrLeft);
        postRequest.postRequest(currentMovie, getMatchURL, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) { ;
                if (jsonManipulator.getBoolean(jsonObject, "match")) {
                    match.setContentView(view);
                    textViewTitleMovie.setText(o.getTitle());
                    imageViewPoster.setImageBitmap(o.getImagePoster());
                    buttonDismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            match.dismiss();
                        }
                    });

                    buttonMoreInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            match.dismiss();
                            MovieInfosFragment movieInfosFragment = new MovieInfosFragment();
                            Bundle bundle = new Bundle();
                            Movie movie = (Movie) o;
                            bundle.putParcelable("Movie", movie);
                            movieInfosFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.frame, movieInfosFragment).addToBackStack(null).commit();
                        }
                    });
                    match.show();
                }
            }
        });
    }

    private void fling() {
        arrayAdapter = new SwipeAdapter(getContext(), movies);
        flingContainer.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                movies.remove(0);
                System.out.println(movies.size());
                if (movies.size() > 5) {
                    loadImage(5);
                } else if (movies.size() <= 5 && movies.size() > 0) {
                    loadImage(movies.size() - 1);
                } else {
                    getRequestListFilm();
                }
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                postSwipe((Movie) dataObject, false);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                postSwipe((Movie) dataObject, true);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
            }

            @Override
            public void onScroll(float v) {
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int i, Object o) {
                MovieInfosFragment movieInfosFragment = new MovieInfosFragment();
                Bundle bundle = new Bundle();
                Movie movie = (Movie) o;
                bundle.putParcelable("Movie", movie);
                movieInfosFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame, movieInfosFragment).addToBackStack(null).commit();
            }
        });
    }

    private void setImage() {
        for (int i = 0; i <= 5; i++) {
            new ImageRequest(movies.get(i).getImagePoster(), new IImageRequestCallback() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    movies.get(index).setImagePoster(bitmap);
                    if (index == 5) {
                        if (getContext() != null) {
                            fling();
                        }
                        loading.loadingGone(loadingGif, buttonLeft, buttonRight);
                    }
                    index++;
                }
            }).execute(movies.get(i).getPosterURL());
        }
    }

    private void loadImage(int position) {
        new ImageRequest(movies.get(position).getImagePoster(), new IImageRequestCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                movies.get(position).setImagePoster(bitmap);
                arrayAdapter.notifyDataSetChanged();
            }
        }).execute(movies.get(position).getPosterURL());
    }

    @Override
    public void onBackPressed() {
        ((MainActivity) getActivity()).replaceFrag(new GroupsFragment());
    }
    

    public void setUp() {
        buttonLeft = binding.buttonLeft;
        buttonRight = binding.buttonRight;
        flingContainer = binding.frame;
        loadingGif = binding.swipeLoadingGif;

        view = View.inflate(getContext(), R.layout.layout_match,null);
        buttonDismiss =  view.findViewById(R.id.buttonDismiss);
        buttonMoreInfo = view.findViewById(R.id.buttonMoreInfo);
        imageViewPoster = view.findViewById(R.id.imageViewPoster);
        textViewTitleMovie = view.findViewById(R.id.textViewTitleMovie);
        match = new Dialog(getContext());

        movies = new ArrayList<>();
        getRequest = new GetRequest((MainActivity) getActivity());
        postRequest = new PostRequest((MainActivity) getActivity());
        loading = new Loading();
        jsonManipulator = new JSONManipulator();
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();
    }

    private void stopButtonSpamming() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        loading.loadingGone(loadingGif);
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }
}