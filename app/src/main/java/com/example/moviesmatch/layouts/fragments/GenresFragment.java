package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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
import com.example.moviesmatch.layouts.adapters.GenreCheckboxAdapter;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentGenresBinding;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.layouts.activities.CreateAccountActivity;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.models.Genre;
import com.example.moviesmatch.validation.JSONManipulator;

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
    String usrId;

    private final String getGenresURL = "/api/genre/getAllGenres";
    private final String postGenresURL = "/api/genre/addGenresToUser";
    private String getUserGenreURL = "/api/genre/getUserGenres";

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
                    loading();
                    jsonAccountWithGenres = jsonManipulator.put(jsonAccountWithGenres, "idUser", usrId);
                    jsonAccountWithGenres = jsonManipulator.put(jsonAccountWithGenres, "genreIds", genreCheckboxAdapter.getSelectedGenres());
                    postRequest.postRequest(jsonAccountWithGenres, postGenresURL, token, new IRequestCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.putExtra("Account", account.toString());
                            startActivity(intent);
                        }
                    });
                } else {
                    loadingGone();
                    new AlertDialog.Builder(getContext()).setTitle("You didn't check 5 genres").setMessage("Please check exactly 5 genres of movies you like").show();
                }
            }
        });
    }

    /**
     * Get all available genres from the server
     */
    private void getGenres() {
        loading();
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
                loadingGone();
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
        usrId = jsonManipulator.getJSONObjectGetString(account,"userDB", "usrId");
        getUserGenreURL += "?idUser=" + usrId;
    }

    private void loading() {
        gifLoading.setVisibility(View.VISIBLE);
        button.setEnabled(false);
    }

    private void loadingGone() {
        gifLoading.setVisibility(View.GONE);
        button.setEnabled(true);
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
            underline();
        } else {
            parent = "MainActivity";
            postRequest = new PostRequest((MainActivity) getActivity());
            getRequest = new GetRequest((MainActivity) getActivity());
            step.setVisibility(View.GONE);
            selectGenre.setTypeface(selectGenre.getTypeface(), Typeface.BOLD);
            selectGenre.setTextSize(24);
        }
    }

    private void underline(){
        SpannableString content = new SpannableString("Step 3 of 3");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        step.setText(content);
    }

    private void setUp() {
        certificateByPass = new CertificateByPass();
        certificateByPass.IngoreCertificate();
        jsonManipulator = new JSONManipulator();
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
        loadingGone();
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        loadingGone();
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }
}