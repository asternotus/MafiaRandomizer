package com.aleisterfly.mafiarandomsitting.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleisterfly.mafiarandomsitting.R;
import com.aleisterfly.mafiarandomsitting.models.Player;

import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayersViewHolder> {

    private List<Player> players;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onEditClick(int position);
        void onChangeRoleClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public PlayersAdapter(List<Player> players, Context context){
        this.players = players;
        this.context = context;
    }

    @NonNull
    @Override
    public PlayersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.player_item, viewGroup, false);
        PlayersViewHolder vh = new PlayersViewHolder(v, listener);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersViewHolder playersViewHolder, int i) {
            String text = (i+1) + ". " + players.get(i).getName();
            playersViewHolder.tv_name.setText(text);

            int role_image_id = R.drawable.question;

        switch (players.get(i).getRole()){
            case 0:
                role_image_id = R.drawable.citizen;
                break;
            case 1:
                role_image_id = R.drawable.mafia;
                break;
            case 2:
                role_image_id = R.drawable.cop;
                break;
            case 3:
                role_image_id = R.drawable.don;
                break;
                default:
                    role_image_id = R.drawable.citizen;
        }

            playersViewHolder.iv_change_role.setImageDrawable(context.getResources().getDrawable(role_image_id));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class PlayersViewHolder extends RecyclerView.ViewHolder{

        public CardView cv_player_card;
        public TextView tv_name;
        public ImageView iv_edit_name;
        public ImageView iv_change_role;

        public PlayersViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            cv_player_card = (CardView) itemView.findViewById(R.id.cv_player_card);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_edit_name = (ImageView) itemView.findViewById(R.id.iv_edit_name);
            iv_change_role = (ImageView) itemView.findViewById(R.id.iv_change_role);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            iv_edit_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            iv_change_role.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onChangeRoleClick(position);
                        }
                    }
                }
            });
        }
    }
}
