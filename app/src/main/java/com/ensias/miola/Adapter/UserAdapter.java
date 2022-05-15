package com.ensias.miola.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ensias.miola.ContactActivity;
import com.ensias.miola.MainActivity;
import com.ensias.miola.Model.Etudiant;
import com.ensias.miola.ProfileActivity;
import com.ensias.miola.R;

import java.text.BreakIterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewHolder> {


    private Context context;
    private List<Etudiant> UserList;
    private FragmentActivity activity;

    private CircleImageView nav_profile_img;
    public TextView type,name,cne;
    public Button appel, sms, email;

    public UserAdapter(Context context, List<Etudiant> etudiantList) {
        this.context = context;
        UserList = etudiantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(
                R.layout.display_user_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final  Etudiant et= UserList.get(position);

        holder.type.setText(et.getType());
        holder.cne.setText(et.getCne());
        holder.name.setText(et.getFullname());
       /* holder.appel.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + prof.getTele()));
            startActivity(callIntent);
        });*/

        holder.appel.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + et.getPhone_nbr()));
                context.startActivity(intent);
        });


        holder.sms.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + et.getPhone_nbr()));
            context.startActivity(intent);
        });

        holder.email.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL,  new String[] {et.getMail()});
            context.startActivity(emailIntent);
        });

    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView userProfileImage;
        public TextView type,name,cne;
        public Button appel, sms, wtsp, email;
        private FragmentActivity activity;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userProfileImage= itemView.findViewById(R.id.userProfileImage);
            name= itemView.findViewById(R.id.name);
            cne=itemView.findViewById(R.id.cnee);
            type= itemView.findViewById(R.id.type);
         appel= itemView.findViewById(R.id.appel_btn);
            sms= itemView.findViewById(R.id.sms_btn);
            email= itemView.findViewById(R.id.email_btn);



        }



}
}
