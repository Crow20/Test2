package com.sike.xv.engine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.sike.xv.R;

import java.util.ArrayList;

/**
 * Created by agritsenko on 25.07.2017.
 */

public class GameAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Button> objects;

    public GameAdapter(Context context, ArrayList<Button> buttons) {
        ctx = context;
        objects = buttons;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

//    // пункт списка
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // используем созданные, но не используемые view
//        View view = convertView;
//        if (view == null) {
//            view = lInflater.inflate(R.layout.item, parent, false);
//        }
//
//        Product p = getProduct(position);
//
//        // заполняем View в пункте списка данными из товаров: наименование, цена
//        // и картинка
//        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.name);
//        ((TextView) view.findViewById(R.id.tvPrice)).setText(p.price + "");
//        ((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);
//
//        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
//        // присваиваем чекбоксу обработчик
//        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
//        // пишем позицию
//        cbBuy.setTag(position);
//        // заполняем данными из товаров: в корзине или нет
//        cbBuy.setChecked(p.box);
//        return view;
//    }
//
//    // товар по позиции
//    Product getProduct(int position) {
//        return ((Product) getItem(position));
//    }
//
//    // содержимое корзины
//    ArrayList<Product> getBox() {
//        ArrayList<Product> box = new ArrayList<Product>();
//        for (Product p : objects) {
//            // если в корзине
//            if (p.box)
//                box.add(p);
//        }
//        return box;
//    }
//
//    // обработчик для чекбоксов
//    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
//        public void onCheckedChanged(CompoundButton buttonView,
//                                     boolean isChecked) {
//            // меняем данные товара (в корзине или нет)
//            getProduct((Integer) buttonView.getTag()).box = isChecked;
//        }
//    };

