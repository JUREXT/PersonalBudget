package com.example.jure_lokovsek.personalbudget.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jure_lokovsek.personalbudget.Adapters.BudgetAdapter;
import com.example.jure_lokovsek.personalbudget.Database.Budget;
import com.example.jure_lokovsek.personalbudget.Database.DatabaseManager;
import com.example.jure_lokovsek.personalbudget.R;
import com.example.jure_lokovsek.personalbudget.Room.BudgetR;
import com.example.jure_lokovsek.personalbudget.Room.BudgetRViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BudgetListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BudgetListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetListFragment extends Fragment {
    private final static String TAG = BudgetListFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
  //  private OnFragmentInteractionListener mListener;

    private Context mContext;
    private RecyclerView mRecyclerView;
    private FragmentManager mFragmentManager;
    private DatabaseManager mDatabaseManager;
    private ListView listView;
    private List<BudgetR> budgetList;
    private BudgetRViewModel mBudgetRViewModel;

    public BudgetListFragment() {
        // Required empty public constructor
    }

//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BudgetListFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static BudgetListFragment newInstance(String param1, String param2) {
//        BudgetListFragment fragment = new BudgetListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mDatabaseManager = new DatabaseManager(mContext);
        mFragmentManager = getFragmentManager();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget_list, container, false);
        getActivity().setTitle("Today Budget List");
        listView = view.findViewById(R.id.budget_list_view);

        mBudgetRViewModel = ViewModelProviders.of(this).get(BudgetRViewModel.class);
        mBudgetRViewModel.getLiveData().observe(this, new Observer<List<BudgetR>>() {
            @Override
            public void onChanged(@Nullable List<BudgetR> budgetRS) {
                // update listview
                Log.d(TAG, "onChange");
               // budgetList = budgetRS;
                BudgetAdapter budgetAdapter = new BudgetAdapter(budgetRS, mContext);
                listView.setAdapter(budgetAdapter);
                Toast.makeText(mContext, "onChange", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }


}
