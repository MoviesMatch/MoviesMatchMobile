package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.layouts.activities.LoginActivity;
import com.example.moviesmatch.layouts.adapters.GenreCheckboxAdapter;
import com.example.moviesmatch.models.MoviesMatchURLS;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentGenresBinding;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.layouts.activities.CreateAccountActivity;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.models.Genre;
import com.example.moviesmatch.validation.JSONManipulator;
import com.example.moviesmatch.validation.Loading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;


public class GenresFragment extends Fragment implements IGetActivity, IPostActivity {

    private FragmentGenresBinding binding;
    private ArrayList<Genre> listGenres;
    private PostRequest postRequest;
    private GetRequest getRequest;
    private JSONObject account;
    private JSONObject jsonAccountWithGenres;
    private CertificateByPass certificateByPass;
    private GifImageView gifLoading;
    private Button button;
    private String parent;
    private ImageView imageLogo;
    private TextView step;
    private TextView selectGenre;
    private LinearLayout linearLayout;
    private GenreCheckboxAdapter genreCheckboxAdapter;
    private JSONManipulator jsonManipulator;
    private String token;
    private String usrId;
    private Loading loading;

    private final String getGenresURL = MoviesMatchURLS.getAllGenresURL;
    private final String postGenresURL = MoviesMatchURLS.addGenresToUserURL;
    private String getUserGenreURL = MoviesMatchURLS.getUserGenreURL;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentGenresBinding.inflate(getLayoutInflater());
        setUp();
        getGenres();
        savePrefs();
        return binding.getRoot();
    }

    public void savePrefs() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (genreCheckboxAdapter.isFiveChecked()) {
                    loading.loadingVisible(gifLoading, button);
                    jsonAccountWithGenres = jsonManipulator.put(jsonAccountWithGenres, "idUser", usrId);
                    jsonAccountWithGenres = jsonManipulator.put(jsonAccountWithGenres, "genreIds", genreCheckboxAdapter.getSelectedGenres());
                    postRequest.postRequest(jsonAccountWithGenres, postGenresURL, token, new IRequestCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Intent intent;
                            if (parent.equals("MainActivity")) {
                                intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra("Account", account.toString());
                                startActivity(intent);
                            } else {
                                intent = new Intent(getContext(), LoginActivity.class);
                                new AlertDialog.Builder(getContext()).setTitle("Email confirmation").setMessage("Your email must be confirmed. It may take some time to receive and be in your junk folder").show().setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                } else {
                    loading.loadingGone(gifLoading, button);
                    new AlertDialog.Builder(getContext()).setTitle("You didn't check 5 genres").setMessage("Please check exactly 5 genres of movies you like").show();
                }
            }
        });
    }

    /**
     * Get all available genres from the server
     */
    private void getGenres() {
        loading.loadingVisible(gifLoading, button);
        getRequest.getRequestArray(getGenresURL, token, new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                listGenres = new JSONManipulator().toGenreList(jsonArray);
                genreCheckboxAdapter = new GenreCheckboxAdapter(getContext(), listGenres);
                if (parent.equals("MainActivity")) {
                    getUserGenres();
                } else {
                    genreCheckboxAdapter.setCheckboxesGenres(linearLayout);
                }
                loading.loadingGone(gifLoading, button);
            }
        });
    }

    /**
     * Gets the loggedIn users selected Genres to be checked in the listView
     */
    private void getUserGenres() {
        getRequest.getRequestArray(getUserGenreURL, token, new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                ArrayList<Genre> userGenre = new JSONManipulator().toGenreList(jsonArray);
                for (Genre genres : listGenres) {
                    for (Genre userGenres : userGenre) {
                        if (genres.getId() == userGenres.getId()) {
                            genres.setChecked(true);
                        }
                    }
                }
                genreCheckboxAdapter.setCheckboxesGenres(linearLayout);
            }
        });
    }

    /**
     * Sets the URL with the loggedIn user id in parameter
     */
    private void setUserGenreURL() {
        usrId = jsonManipulator.getJSONObjectGetString(account, "userDB", "usrId");
        getUserGenreURL += usrId;
    }

    private void imageGone() {
        if (parent.equals("MainActivity")) {
            imageLogo.setVisibility(View.GONE);
        }
    }

    private void setParent() {
        //Gets the parent since this fragment is used in 2 activities
        if (this.getArguments().getString("Parent").equals("CreateAccountActivity")) {
            parent = "CreateAccountActivity";
            postRequest = new PostRequest((CreateAccountActivity) getActivity());
            getRequest = new GetRequest((CreateAccountActivity) getActivity());
            button.setText("Finish");
        } else {
            parent = "MainActivity";
            postRequest = new PostRequest((MainActivity) getActivity());
            getRequest = new GetRequest((MainActivity) getActivity());
            step.setVisibility(View.GONE);
            selectGenre.setTypeface(selectGenre.getTypeface(), Typeface.BOLD);
            selectGenre.setTextSize(24);
        }
    }

    private void setUp() {
        certificateByPass = new CertificateByPass();
        certificateByPass.IngoreCertificate();
        jsonManipulator = new JSONManipulator();
        loading = new Loading();
        account = jsonManipulator.newJSONObject(this.getArguments().getString("Account"));
        token = jsonManipulator.getString(account, "token");
        setUserGenreURL();
        jsonAccountWithGenres = new JSONObject();
        gifLoading = binding.genresLoadingGif;
        button = binding.buttonGenre;
        imageLogo = binding.imageViewLogo;
        linearLayout = binding.linearLayoutGenres;
        step = binding.textViewStep3of3;
        selectGenre = binding.textViewSelectGenre;
        setParent();
        imageGone();
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        loading.loadingGone(gifLoading, button);
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        loading.loadingGone(gifLoading, button);
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }
}