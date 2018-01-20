package il.ac.jct.michaelzalman.takeandgoclient.fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import il.ac.jct.michaelzalman.takeandgoclient.R;
import il.ac.jct.michaelzalman.takeandgoclient.controller.backgroundProcess;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.DBFactory;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.IDBManager;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.TakeAndGoConsts;
import il.ac.jct.michaelzalman.takeandgoclient.model.entities.Address;
import il.ac.jct.michaelzalman.takeandgoclient.model.entities.Branch;
import il.ac.jct.michaelzalman.takeandgoclient.model.entities.Car;
import il.ac.jct.michaelzalman.takeandgoclient.model.entities.CarModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class showBranches extends Fragment {

    private SearchView searchFrag;
    private Spinner listFrag;
    private ListView carsFrag;
    private IDBManager manager;

    private ArrayAdapter carsAdapter;
    private ArrayAdapter branchesAdapter;

    private backgroundProcess<Void,Void,Void> getBranchesBgp;
    private backgroundProcess.backgroundProcessActions<Void,Void,Void> getBranchesAction;

    private backgroundProcess<Void,Void,Void> getBranchesCars;
    private backgroundProcess.backgroundProcessActions<Void,Void,Void> getCarsAction;

    public showBranches() {
        // Required empty public constructor
        manager = DBFactory.getIdbManager();
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_branches, container, false);

    }



    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-20 20:36:49 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View v) {
        searchFrag = (SearchView)v.findViewById( R.id.search_frag );
        listFrag = (Spinner)v.findViewById( R.id.list_frag );
        carsFrag = (ListView)v.findViewById( R.id.cars_frag );

        searchFrag.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                branchesAdapter.getFilter().filter(query);
                branchesAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                branchesAdapter.getFilter().filter(newText);
                branchesAdapter.notifyDataSetChanged();
                return false;
            }
        });
        carsFrag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                carsFrag.getSelectedItem();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        getBranchesAction = new backgroundProcess.backgroundProcessActions<Void, Void, Void>() {

            List<Branch> list = null;

            @Override
            public Void doInBackground() {
                list = manager.getAllBranchs();
                return null;
            }

            @Override
            public void onPostExecute(Void aVoid) {

                final List<Branch> orgList = list;
                branchesAdapter = new ArrayAdapter<Branch>(getContext(),R.layout.frag_branch_item,list)
                {


                    @Nullable
                    @Override
                    public Branch getItem(int position) {
                        return list.get(position);
                    }

                    @Override
                    public int getPosition(@Nullable Branch item) {
                        return list.indexOf(item);
                    }

                    @Override
                    public int getCount() {
                        return list.size();
                    }


                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        if(convertView == null)
                        {
                            convertView = View.inflate(getContext(),R.layout.frag_branch_item,null);
                        }

                        TextView address = (TextView) convertView.findViewById(R.id.location);
                        TextView link = (TextView) convertView.findViewById(R.id.map_link);

                        final Address branchAdd = list.get(position).getAddress();
                        address.setText(branchAdd.getCity()+" "+branchAdd.getStreet()+" "+branchAdd.getNumber());

                        link.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uri = String.format("geo:0,0?q="+branchAdd.getCity()+"+"+
                                        branchAdd.getStreet()+"+"+
                                        branchAdd.getNumber());
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                            }
                        });

                        return convertView;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        if(convertView == null)
                        {
                            convertView = View.inflate(getContext(),R.layout.frag_branch_item,null);
                        }

                        TextView address = (TextView) convertView.findViewById(R.id.location);
                        TextView link = (TextView) convertView.findViewById(R.id.map_link);

                        final Address branchAdd = list.get(position).getAddress();
                        address.setText(branchAdd.getCity()+" "+branchAdd.getStreet()+" "+branchAdd.getNumber());

                        link.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uri = String.format("geo:0,0?q="+branchAdd.getCity()+"+"+
                                        branchAdd.getStreet()+"+"+
                                        branchAdd.getNumber());
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                            }
                        });

                        return convertView;
                    }

                    @NonNull
                    @Override
                    public Filter getFilter() {
                        return new Filter() {

                            @Override
                            protected FilterResults performFiltering(CharSequence constraint) {

                                FilterResults res = new FilterResults();
                                if(constraint == null || constraint.toString().isEmpty())
                                {
                                    res.values = orgList;
                                    res.count = orgList.size();
                                }
                                else
                                {
                                    List<Branch> temp = new ArrayList<>();

                                    for(Branch branch : orgList)
                                    {
                                        if(branch.getAddress().getCity().startsWith(constraint.toString()))
                                        {
                                            temp.add(branch);
                                        }
                                    }

                                    res.values = temp;
                                    res.count = temp.size();
                                }
                                return res;
                            }

                            @Override
                            protected void publishResults(CharSequence constraint, FilterResults results) {

                                if(results.count == 0)
                                {
                                    notifyDataSetInvalidated();
                                }
                                else {
                                    list = ((List<Branch>) results.values);
                                    notifyDataSetChanged();
                                }

                            }
                        };
                    }
                };

                listFrag.setAdapter(branchesAdapter);

            }
        };
        getBranchesBgp = new backgroundProcess<>(getBranchesAction);
        getBranchesBgp.execute();

        getCarsAction = new backgroundProcess.backgroundProcessActions<Void, Void, Void>() {

            List<Car> cars;
            List<CarModel> models;

            @Override
            public Void doInBackground() {

                cars = null;
                models = null;
                ContentValues id = new ContentValues();
                id.put(TakeAndGoConsts.BranchConst.ID,""+((Branch)listFrag.getSelectedItem()).getId());

                cars = manager.getAvailableCarsForBranch(id);
                models = manager.getAllCarModels();
                return null;
            }

            @Override
            public void onPostExecute(Void aVoid) {
                carsAdapter = null;
                if(cars != null && !cars.isEmpty())
                {
                    carsAdapter = new ArrayAdapter(getContext(),R.layout.branch_frag_cars,cars)
                    {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                            if(convertView == null)
                            {
                                convertView = View.inflate(getContext(),R.layout.branch_frag_cars,null);
                            }

                            TextView carName = (TextView) convertView.findViewById(R.id.car_name);

                            String modelId = cars.get(position).getCarModel();

                            CarModel carModel=null;

                            for(CarModel model : models)
                            {
                                if(model.getId().compareTo(modelId) == 0) {
                                    carModel = model;
                                    break;
                                }
                            }

                            if(carModel != null) {
                                carName.setText(carModel.getBrand()+" "+carModel.getModelName());
                            }

                            return convertView;
                        }
                    };
                }

                carsFrag.setAdapter(carsAdapter);

            }
        };

        listFrag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(getBranchesCars != null && getBranchesCars.getStatus() == AsyncTask.Status.RUNNING)
                {
                    getBranchesCars.cancel(true);
                    Toast.makeText(getContext(),"נסה שוב",Toast.LENGTH_LONG).show();
                }
                else {
                    getBranchesCars = new backgroundProcess<Void, Void, Void>(getCarsAction);
                    getBranchesCars.execute();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
