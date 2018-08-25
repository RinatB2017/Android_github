package multiplelay.android.stanislav.com.cloudtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.realm.Realm;
import multiplelay.android.stanislav.com.cloudtasks.db.DBservise;
import multiplelay.android.stanislav.com.cloudtasks.db.models.TaskRealModel;
import multiplelay.android.stanislav.com.cloudtasks.history.HistoryActivity;

public class ListTasks extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    //FirebaseListAdapter mAdapter;

    private EditText ET_new_task;
    private Button Btn_new_task;

    //ListView ListUserTasks;

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView mTitleTask;
        Button mDel;
        View item;

        public TaskViewHolder(View itemView) {
            super(itemView);
            mTitleTask = (TextView) itemView.findViewById(R.id.tv_title_task);
            mDel = (Button) itemView.findViewById(R.id.btn_del);
            item = itemView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks2);

        Realm.init(this);

        DBservise dBservise = new DBservise();

        //ListUserTasks = (ListView) findViewById(R.id.discr_for_task);

        myRef = FirebaseDatabase.getInstance().getReference();

        /*mAdapter = new FirebaseListAdapter <String>(this,
                String.class,
                android.R.layout.simple_list_item_1,myRef.child(user.getUid()).child("Tasks")) {
            @Override
            protected void populateView(View v, String s, int position) {
                TextView text = (TextView) v.findViewById(android.R.id.text1);
                text.setText(s);
            }

        };
        ListUserTasks.setAdapter(mAdapter);

        ListUserTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    myRef.child(user.getUid()).child("Tasks").child("" + position).removeValue();
                    DatabaseReference itemRef = mAdapter.getRef(position);
                    itemRef.removeValue();
            }
        });*/

        //Добавление данных в firebase database
        Btn_new_task = (Button) findViewById(R.id.btn_add);
        ET_new_task = (EditText) findViewById(R.id.et_new_tasks);

        Btn_new_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(
                        user.getUid()).child("Tasks").
                        push().
                        setValue(ET_new_task.getText().toString());

                TaskRealModel taskRealModel = new TaskRealModel();

                taskRealModel.setTitle(ET_new_task.getText().toString());

                dBservise.save(taskRealModel, TaskRealModel.class)
                          .subscribe(taskRealModel1 -> {
                              Toast.makeText(getBaseContext(),"Сохранено в бд",Toast.LENGTH_SHORT).show();
                          });
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_list_tasks);

        FirebaseRecyclerAdapter <String,TaskViewHolder> adapter;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new FirebaseRecyclerAdapter<String, TaskViewHolder>(
                String.class,
                R.layout.task_layout,
                TaskViewHolder.class,
                myRef.child(user.getUid()).child("Tasks")
        ) {
            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, String title,final int position) {

                viewHolder.mTitleTask.setText(title);
                viewHolder.mDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference itemRef = getRef(position);
                        itemRef.removeValue();
                    }
                });
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListTasks.this,DeatailTaskActivity.class);
                        intent.putExtra("Reference",getRef(position).getKey().toString());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_history:
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
