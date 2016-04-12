package com.megogrid.megoshop.share;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.megogrid.megoshop.R;

import java.util.ArrayList;
import java.util.List;

public class Help extends Activity {

    ListView listView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        listView = (ListView)findViewById(R.id.lv);
        DescriptionAdatper adatper = new DescriptionAdatper(context);
        listView.setAdapter(adatper);

    }



    private class DescriptionAdatper extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        List<Item> list;
        public DescriptionAdatper(Context context){
            this.context = context;
            inflater = LayoutInflater.from(context);
            list = new ArrayList<>();
            prepareList();
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Item getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_layout, null);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.desc = (TextView)convertView.findViewById(R.id.desc);
                holder.direction = (TextView)convertView.findViewById(R.id.direction);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            Item item = getItem(position);
            holder.title.setText(item.title);
            holder.desc.setText(item.description);
            if(!item.direction.equalsIgnoreCase("")){
                holder.direction.setVisibility(View.VISIBLE);
                holder.direction.setText(item.direction);
            }

            return convertView;
        }

        private void prepareList(){
            Item item1 = new Item();
            item1.title = getString(R.string.title);
            item1.direction = "";
            item1.description = getString(R.string.description_1)+"\n\n"+getString(R.string.description_2);
            Item item2 = new Item();
            item2.title = getString(R.string.title_permanent);
            item2.direction = "This purchase type applied on- Take Photo & Apply Images";
            item2.description = getString(R.string.permanent_description);
            Item item3 = new Item();
            item3.title = getString(R.string.title_charge);
            item3.direction = "This purchase type applied on- Choose Image from Gallery";
            item3.description = getString(R.string.partial_description);
            Item item4 = new Item();
            item4.title = getString(R.string.title_subscription);
            item4.direction = "This purchase type applied on - Share under navigation drawer";
            item4.description = getString(R.string.subscription_description);
            list.add(item1);
            list.add(item2);
            list.add(item3);
            list.add(item4);

        }

        private class Item {
            String title;
            String description;
            String direction;
        }

        private class ViewHolder {
            TextView title, desc, direction;
        }
    }

}
