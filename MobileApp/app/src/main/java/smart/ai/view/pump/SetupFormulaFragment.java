package smart.ai.view.pump;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;


import smart.ai.R;
import smart.ai.model.NutritionalFormula;
import smart.ai.util.AppUtil;
import smart.ai.util.Constant;
import smart.ai.util.MyDatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

public class SetupFormulaFragment extends Fragment {
    private static final String TAG = SetupFormulaFragment.class.getSimpleName();

    @BindView(R.id.spFormula)
    Spinner spFormula;

    @BindView(R.id.edtFormulaName)
    EditText edtFormulaName;

    @BindView(R.id.edtRateWater)
    EditText edtRateWater;

    @BindView(R.id.edtLiquidOne)
    EditText edtLiquidOne;

    @BindView(R.id.edtLiquidTwo)
    EditText edtLiquidTwo;

    @BindView(R.id.edtLiquidThree)
    EditText edtLiquidThree;

    @BindView(R.id.edtLiquidFour)
    EditText edtLiquidFour;

    private List<NutritionalFormula> nutritionalFormulas;
    private ArrayAdapter<NutritionalFormula> nutritionalFormulaAdapter;
    private NutritionalFormula nutritionalFormulaCurrent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_formula, container, false);

        Log.d(TAG, "onCreateView: ");
        addControls(view);
        addEvents();
        settingDatabase();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated: ");
    }

    public void settingDatabase() {
        MyDatabaseHelper db = new MyDatabaseHelper(getContext());
        db.createDefaultFormula();

        nutritionalFormulas.clear();
        nutritionalFormulas.addAll(db.getAllFormulas());
        nutritionalFormulaAdapter.notifyDataSetChanged();
        if (nutritionalFormulas.size() > 0){
            spFormula.setSelection(0);
        }
    }

    private void addControls(View view) {
        ButterKnife.bind(this, view);
        setupSpinner();
    }

    private void addEvents() {
        spFormula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + position);
                if (position >= 0 && position <= nutritionalFormulas.size()) {
                    nutritionalFormulaCurrent = nutritionalFormulas.get(position);
                }else {
                    nutritionalFormulaCurrent = null;
                }
                setInfoFormula(nutritionalFormulaCurrent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setInfoFormula(NutritionalFormula nutritionalFormula) {
        if (nutritionalFormula != null) {
            edtFormulaName.setText(nutritionalFormula.getFormulaName());
            edtRateWater.setText(nutritionalFormula.getPercentWater() + "");
            edtLiquidOne.setText(nutritionalFormula.getPercentNutritionalOne() + "");
            edtLiquidTwo.setText(nutritionalFormula.getPercentNutritionalTwo() + "");
            edtLiquidThree.setText(nutritionalFormula.getPercentNutritionalThree() + "");
            edtLiquidFour.setText(nutritionalFormula.getPercentNutritionalFour() + "");
        }else {
            edtFormulaName.setText("");
            edtRateWater.setText("0");
            edtLiquidOne.setText("0");
            edtLiquidTwo.setText("0");
            edtLiquidThree.setText("0");
            edtLiquidFour.setText("0");
        }
    }

    private void setupSpinner() {
        nutritionalFormulas = new ArrayList<>();
        nutritionalFormulaAdapter = new ArrayAdapter<NutritionalFormula>(getActivity(), android.R.layout.simple_spinner_item, nutritionalFormulas);
        nutritionalFormulaAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spFormula.setAdapter(nutritionalFormulaAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean isEditTextEmpty(EditText edtText) {
        if (edtText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    @OnClick(R.id.btAdd)
    void addFormula() {
        if (!isEditTextEmpty(edtFormulaName)
                && !isEditTextEmpty(edtRateWater)
                && !isEditTextEmpty(edtLiquidOne)
                && !isEditTextEmpty(edtLiquidTwo)
                && !isEditTextEmpty(edtLiquidThree)
                && !isEditTextEmpty(edtLiquidFour)
        ) {
            MyDatabaseHelper db = new MyDatabaseHelper(getContext());

            String name = edtFormulaName.getText().toString().trim();

            if (db.checkExist(name)) {
                Toast.makeText(getContext(), R.string.msg_the_formula_name_already_exists, Toast.LENGTH_SHORT).show();
                return;
            }

            double water = Double.parseDouble(edtRateWater.getText().toString());
            double one = Double.parseDouble(edtLiquidOne.getText().toString());
            double two = Double.parseDouble(edtLiquidTwo.getText().toString());
            double three = Double.parseDouble(edtLiquidThree.getText().toString());
            double four = Double.parseDouble(edtLiquidFour.getText().toString());

            if (water + one + two + three + four == 100) {
                NutritionalFormula formulaNew = new NutritionalFormula();
                formulaNew.setId(UUID.randomUUID().toString());
                formulaNew.setFormulaName(name);
                formulaNew.setPercentWater(water);
                formulaNew.setPercentNutritionalOne(one);
                formulaNew.setPercentNutritionalTwo(two);
                formulaNew.setPercentNutritionalThree(three);
                formulaNew.setPercentNutritionalFour(four);

                db.addFormula(formulaNew);
                nutritionalFormulas.add(formulaNew);
                spFormula.setAdapter(nutritionalFormulaAdapter);
                spFormula.setSelection(nutritionalFormulas.size() - 1);
                Toast.makeText(getContext(), R.string.msg_save_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.msg_the_total_radio_should_be_100, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(), R.string.msg_valid_input, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btSave)
    void editFormula() {
        if (nutritionalFormulaCurrent != null) {

            MyDatabaseHelper db = new MyDatabaseHelper(getContext());

            double water = Double.parseDouble(edtRateWater.getText().toString());
            double one = Double.parseDouble(edtLiquidOne.getText().toString());
            double two = Double.parseDouble(edtLiquidTwo.getText().toString());
            double three = Double.parseDouble(edtLiquidThree.getText().toString());
            double four = Double.parseDouble(edtLiquidFour.getText().toString());

            if (water + one + two + three + four == 100) {
                nutritionalFormulaCurrent.setFormulaName(edtFormulaName.getText().toString().trim());
                nutritionalFormulaCurrent.setPercentWater(water);
                nutritionalFormulaCurrent.setPercentNutritionalOne(one);
                nutritionalFormulaCurrent.setPercentNutritionalTwo(two);
                nutritionalFormulaCurrent.setPercentNutritionalThree(three);
                nutritionalFormulaCurrent.setPercentNutritionalFour(four);

                db.updateFormula(nutritionalFormulaCurrent);

                Toast.makeText(getContext(), R.string.msg_save_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.msg_the_total_radio_should_be_100, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(), R.string.msg_formula_not_exist, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btCancel)
    void cancelFormula() {
        if (nutritionalFormulaCurrent != null) {

            MyDatabaseHelper db = new MyDatabaseHelper(getContext());

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.msg_you_can_remove_this_formula);
            builder.setPositiveButton(R.string.title_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    db.deleteFormula(nutritionalFormulaCurrent);
                    nutritionalFormulas.remove(nutritionalFormulaCurrent);
                    spFormula.setAdapter(nutritionalFormulaAdapter);

                    if (db.getFormulaCount() == 0){
                        nutritionalFormulaCurrent = null;
                        setInfoFormula(nutritionalFormulaCurrent);
                    }
                }
            });
            builder.setNegativeButton(R.string.title_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            Toast.makeText(getContext(), R.string.msg_formula_not_exist, Toast.LENGTH_SHORT).show();
        }
    }
}
