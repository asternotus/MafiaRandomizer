package com.aleisterfly.mafiarandomsitting.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aleisterfly.mafiarandomsitting.MafiaApp;
import com.aleisterfly.mafiarandomsitting.R;
import com.aleisterfly.mafiarandomsitting.activities.interfaces.IGameScreenView;
import com.aleisterfly.mafiarandomsitting.adapters.PlayersAdapter;
import com.aleisterfly.mafiarandomsitting.db.PlayerDB;
import com.aleisterfly.mafiarandomsitting.models.Player;
import com.aleisterfly.mafiarandomsitting.presenters.GamePresenter;
import com.aleisterfly.mafiarandomsitting.presenters.factories.PresenterFactory;

import java.util.List;

public class GameActivity extends AppCompatActivity implements IGameScreenView {

    private RecyclerView rv_players;
    private RecyclerView.LayoutManager lv_players;
    private PlayersAdapter adapter_players;

    private Button btn_shuffle;

    private PlayerDB db;

    private GamePresenter gamePresenter;

    private List<Player> players;

    private final String LOG_TAG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PresenterFactory presenterFactory = new PresenterFactory(this);
        gamePresenter = ViewModelProviders.of(this, presenterFactory).get(GamePresenter.class);

        getLifecycle().addObserver(gamePresenter);

        db = MafiaApp.getInstance().getPlayerDB();

        players = gamePresenter.getPlayersFromDatabase();

        rv_players = (RecyclerView) findViewById(R.id.rv_players);
        rv_players.setHasFixedSize(true);

        lv_players = new LinearLayoutManager(this);
        rv_players.setLayoutManager(lv_players);

        adapter_players = new PlayersAdapter(players, this);
        rv_players.setAdapter(adapter_players);

        adapter_players.setOnItemClickListener(new PlayersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i("TAG", "click for " + position+ "item");
            }

            @Override
            public void onEditClick(final int position) {
                showEditPlayerDialog(players, position);
            }

            @Override
            public void onChangeRoleClick(int position) {
                showChangeRoleDialog(players, position);
            }
        });

        btn_shuffle = (Button) findViewById(R.id.btn_shuffle);
        btn_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.shufflePlayers();
                adapter_players.notifyDataSetChanged();
            }
        });
    }

    public void showEditPlayerDialog(final List<Player> players, final int position) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_name_dialog, null);
        final EditText et_name = (EditText) dialogView.findViewById(R.id.et_name);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GameActivity.this);

        dialogBuilder
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        players.get(position).setName(et_name.getText().toString());
                        gamePresenter.updatePlayerData(position);
                        adapter_players.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        dialogBuilder.setView(dialogView);

        et_name.setText(players.get(position).getName());
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void showChangeRoleDialog(final List<Player> players, final int position) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_role_dialog, null);

        final RadioGroup rbg_roles = (RadioGroup) dialogView.findViewById(R.id.rbg_roles);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GameActivity.this);
        ((RadioButton)rbg_roles.getChildAt(players.get(position).getRole())).setChecked(true);

        dialogBuilder
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        int radioButtonID = rbg_roles.getCheckedRadioButtonId();
                        View radioButton = rbg_roles.findViewById(radioButtonID);
                        int role_index = rbg_roles.indexOfChild(radioButton);

                        players.get(position).setRole(role_index);
                        gamePresenter.updatePlayerData(position);
                        adapter_players.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void updateAdapter(List<Player> players) {
        this.players = players;
        adapter_players.notifyDataSetChanged();
    }
}
