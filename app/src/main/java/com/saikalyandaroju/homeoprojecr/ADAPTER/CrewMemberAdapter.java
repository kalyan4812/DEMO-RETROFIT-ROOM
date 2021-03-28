package com.saikalyandaroju.homeoprojecr.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saikalyandaroju.homeoprojecr.POJOS.CrewResponse;
import com.saikalyandaroju.homeoprojecr.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CrewMemberAdapter extends RecyclerView.Adapter<CrewMemberAdapter.CrewViewHolder> {
    private Context context;
    private List<CrewResponse> responses;

    public CrewMemberAdapter(Context context, List<CrewResponse> responses) {
        this.context = context;
        this.responses = responses;

    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_item, parent, false);
        return new CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        CrewResponse crewResponse = responses.get(position);
        holder.name.setText("Name :"+crewResponse.getName());
        Picasso.get().load(crewResponse.getImage()).placeholder(R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(holder.imageView);
        holder.agency.setText("Agency :"+crewResponse.getAgency());
        holder.wikipedia.append(crewResponse.getWikipedia());
        holder.status.setText("Status :"+crewResponse.getStatus());

    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class CrewViewHolder extends RecyclerView.ViewHolder {
        TextView name, agency, status, wikipedia;
        ImageView imageView;

        public CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            agency = itemView.findViewById(R.id.agency);
            status = itemView.findViewById(R.id.status);
            wikipedia = itemView.findViewById(R.id.wekipedia);
            imageView = itemView.findViewById(R.id.image);
            //wikipedia.setMovementMethod(LinkMovementMethod.getInstance());
            //wikipedia.setLinkTextColor(Color.BLACK);

        }
    }
}
